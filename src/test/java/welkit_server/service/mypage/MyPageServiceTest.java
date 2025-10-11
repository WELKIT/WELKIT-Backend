package welkit_server.service.mypage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import welkit_server.common.fixture.UserFixture;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.UpdateJobRoleRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.dto.response.UpdateJobRoleResponse;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.mypage.service.MyPageService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @InjectMocks
    private MyPageService myPageService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LockSettingRepository lockSettingRepository;

    @Mock
    private Authentication authentication;

    @Mock
    PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setUp() {
        user = UserFixture.user(
                1L,
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA,
                UserType.USER
        );
    }
    @Test
    @DisplayName("마이페이지에서 직무를 변경한다")
    void updateJobRoleSuccess() {
        //given
        JobRole newJobRole = JobRole.DESIGN;
        UpdateJobRoleRequest updateJobRoleRequest = new UpdateJobRoleRequest(newJobRole);
        //Authentication Mock
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        //when
        UpdateJobRoleResponse response = myPageService.updateJobRole(updateJobRoleRequest, authentication);

        //then
        assertThat(user.getJobRole()).isEqualTo(newJobRole);
        assertThat(response.getJobRole()).isEqualTo(newJobRole);
        verify(userService).getAuthenticatedUser(authentication);
    }

    @Test
    @DisplayName("마이페이지에서 기능 잠금을 위한 암호를 설정을 한다")
    void setLockTestSuccess() {
        //given
        String lockPin = "1234";
        LockSettingRequest lockSettingRequest = new LockSettingRequest(lockPin);
        //Authentication Mock
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(passwordEncoder.encode(lockPin)).thenReturn("encodedPin");
        //when
        myPageService.setLock(lockSettingRequest, authentication);

        //then
        verify(userService).getAuthenticatedUser(authentication);
        verify(passwordEncoder).encode(lockPin);
        assertThat(user.getLockPin()).isEqualTo("encodedPin");
    }

    @Test
    @DisplayName("마이페이지에서 4자리 이상 비밀번호 설정 시 예외 발생")
    void setLockTestFail() {
        // given
        String lockPin = "123456";
        LockSettingRequest lockSettingRequest = new LockSettingRequest(lockPin);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        // when & then
        assertThatThrownBy(() -> myPageService.setLock(lockSettingRequest, authentication))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorMessage.MYP_LOCK_PIN_INVALID.getMessage());
    }
    @Test
    @DisplayName("마이페이지에서 기능별 잠금을 설정을 한다")
    void setFeatureLockSuccess() {
        //given
        String lockPin = "1234";
        FeatureName featureName = FeatureName.INSIGHT_CARDS;

        LockSettingRequest lockSettingRequest = new LockSettingRequest(lockPin);
        FeatureLockSettingRequest featureLockSettingRequest = new FeatureLockSettingRequest(featureName,true);

        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        when(passwordEncoder.encode(lockPin)).thenReturn("encodedPin");

        LockSetting lockSetting = new LockSetting().builder()
                .id(user.getId())
                .user(user)
                .featureName(featureName)
                .isLocked(true)
                .build();
        when(lockSettingRepository.findByUserIdAndFeatureName(1L, featureName))
                .thenReturn(lockSetting);
        //when
        myPageService.setLock(lockSettingRequest, authentication);
        FeatureLockSettingResponse featureLockSettingResponse = myPageService.setFeatureLock(featureLockSettingRequest, authentication);
        //then
        assertThat(user.getLockPin()).isEqualTo("encodedPin");
        assertThat(lockSetting.isLocked()).isTrue();
        assertThat(featureLockSettingResponse.isLocked()).isTrue();

        verify(userService, times(2)).getAuthenticatedUser(authentication);
        verify(passwordEncoder).encode(lockPin);
    }

    @DisplayName("마이페이지에서 비밀번호 설정 안하고 기능 잠금 설정 시 예외 발생")
    @Test
    void setFeatureLockFail() {
        //given
        FeatureName featureName = FeatureName.INSIGHT_CARDS;
        FeatureLockSettingRequest featureLockSettingRequest = new FeatureLockSettingRequest(featureName,true);
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);
        // when & then
        assertThatThrownBy(() -> myPageService.setFeatureLock(featureLockSettingRequest, authentication))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorMessage.MYP_LOCK_PIN_NOT_FOUND.getMessage());
    }

}
