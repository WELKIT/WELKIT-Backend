package welkit_server.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import welkit_server.domain.auth.signup.personal.dto.SignupRequest;
import welkit_server.domain.auth.signup.personal.service.PersonalSignupService;
import welkit_server.domain.mail.model.EmailCodePurpose;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.fixture.UserFixture;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.redis.RedisUtil;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    LockSettingRepository lockSettingRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RedisUtil redisUtil;

    @InjectMocks
    PersonalSignupService personalSignupService;

    @DisplayName("사용자 / 관리자는  정상적으로 개인 이메일로 회원가입 할 수 있습니다 ")
    @Test
    void signUpTest() {
        //given
        SignupRequest request = new SignupRequest(
                "abc@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );

        String encodePassword = "encode_password";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodePassword);

        when(redisUtil.isVerifiedEmail(anyString(),any())).thenReturn(true);

        User saveUser = UserFixture.createUser(
                request.getEmail(),
                encodePassword,
                request.getJobRole()
        );

        when(userRepository.save(Mockito.any(User.class))).thenReturn(saveUser);
        when(lockSettingRepository.saveAll(Mockito.anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        personalSignupService.signup(request);
        //then
        ArgumentCaptor<User> signUpUser = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(signUpUser.capture());

        User user = signUpUser.getValue();
        assertThat(user.getEmail()).isEqualTo(request.getEmail());
        assertThat(user.getPassword()).isEqualTo(encodePassword);
        assertThat(user.getJobRole()).isEqualTo(request.getJobRole());

        verify(lockSettingRepository, atLeastOnce()).saveAll(Mockito.anyList());

    }

    @DisplayName("이메일 인증이 되지 않는 이메일로 회원가입 시 예외 처리")
    @Test
    void signUpFailsIfNotVerifiedEmail() {
        //given
        SignupRequest request = new SignupRequest(
                "abc1@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );
        when(redisUtil.isVerifiedEmail(request.getEmail(), EmailCodePurpose.SIGN_UP)).thenReturn(false); //이메일 인증 미처리
        //when + then
        assertThatThrownBy(() -> personalSignupService.signup(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorMessage.INVALID_EMAIL_VERIFICATION.getMessage());
    }

    @DisplayName("이미 회원가입한 이메일로 회원가입 시 예외 처리")
    @Test
    void signUpFailsIfEmailAlreadyExists() {
        //given
        SignupRequest request = new SignupRequest(
                "abc@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );
        when(redisUtil.isVerifiedEmail((request.getEmail()) , EmailCodePurpose.SIGN_UP)).thenReturn(true); //이메일 인증 처리
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true); //이메일 중복 처리
        //when + then
        assertThatThrownBy(() -> personalSignupService.signup(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorMessage.DUPLICATE_EMAIL.getMessage());
    }
}
