package welkit_server.service.mypage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import welkit_server.common.fixture.user.UserFixture;
import welkit_server.domain.mypage.dto.request.UpdateJobRoleRequest;
import welkit_server.domain.mypage.dto.response.UpdateJobRoleResponse;
import welkit_server.domain.mypage.service.MyPageService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.domain.user.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Profile("test")
@ExtendWith(MockitoExtension.class)
class MyPageServiceTest {

    @InjectMocks
    private MyPageService myPageService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication authentication;

    @Test
    @DisplayName("마이페이지에서 직무를 변경한다")
    void updateJobRoleSuccess() {
        //given
        JobRole newJobRole = JobRole.DESIGN;
        UpdateJobRoleRequest updateJobRoleRequest = new UpdateJobRoleRequest(newJobRole);
        User user = UserFixture.createUser(
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );

        //Authentication Mock
        when(userService.getAuthenticatedUser(authentication)).thenReturn(user);

        //when
        UpdateJobRoleResponse response = myPageService.updateJobRole(updateJobRoleRequest, authentication);

        //then
        assertThat(user.getJobRole()).isEqualTo(newJobRole);
        assertThat(response.getJobRole()).isEqualTo(newJobRole);
        verify(userService).getAuthenticatedUser(authentication);
    }

}


