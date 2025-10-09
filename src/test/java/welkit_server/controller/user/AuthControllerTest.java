package welkit_server.controller.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import welkit_server.controller.BaseControllerTest;
import welkit_server.domain.auth.signup.company.controller.CompanySignupController;
import welkit_server.domain.auth.signup.company.dto.SignupRequest;
import welkit_server.domain.auth.signup.company.service.CompanySignupService;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.model.JobRole;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 컨트롤러 테스트")
@WebMvcTest(controllers = CompanySignupController.class)
public class AuthControllerTest extends BaseControllerTest {

    private static final String SIGNUP_URL = "/users/signup/company";
    private final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO";

    @MockitoBean
    private CompanySignupService companySignupService;

    @MockitoBean
    private RedisTemplate<String, String> redisTemplate;

    @MockitoBean
    private LockSettingRepository lockSettingRepository;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void success_signUp() throws Exception {
        //given
        SignupRequest signupRequest = new SignupRequest(
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );

        doNothing().when(companySignupService).signup(any(SignupRequest.class));
        //when
        ResultActions resultActions = mockMvc.perform(
                post(SIGNUP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", TOKEN)
                        .content(objectMapper.writeValueAsString(signupRequest))
        );
        // then
        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 인증이 안된 경우 ")
    void fail_SignUp_EmailNotVerified() throws Exception {
        //given
        SignupRequest signupRequest = new SignupRequest(
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );

        doThrow(new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION))
                .when(companySignupService).signup(any(SignupRequest.class));

        mockMvc.perform(post(SIGNUP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(jsonPath("$.message").value(ErrorMessage.INVALID_EMAIL_VERIFICATION.getMessage()));

    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일인 경우 ")
    void fail_SignUp_ExistsByEmail() throws Exception {
        SignupRequest signupRequest = new SignupRequest(
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );
        doThrow(new BadRequestException(ErrorMessage.DUPLICATE_EMAIL))
                .when(companySignupService).signup(any(SignupRequest.class));

        mockMvc.perform(post(SIGNUP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(jsonPath("$.message").value(ErrorMessage.DUPLICATE_EMAIL.getMessage()));

    }

}
