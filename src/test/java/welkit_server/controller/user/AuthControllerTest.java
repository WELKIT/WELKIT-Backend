package welkit_server.controller.user;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import welkit_server.controller.BaseControllerTest;
import welkit_server.domain.auth.login.controller.LoginController;
import welkit_server.domain.auth.login.dto.LoginRequest;
import welkit_server.domain.auth.login.service.LoginService;
import welkit_server.domain.auth.logout.controller.LogoutController;
import welkit_server.domain.auth.signup.company.controller.CompanySignupController;
import welkit_server.domain.auth.signup.company.dto.SignupRequest;
import welkit_server.domain.auth.signup.company.service.CompanySignupService;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.controller.UserController;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.message.SuccessMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.security.dto.CustomUserDetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("회원가입 컨트롤러 테스트")
@WebMvcTest(controllers = { CompanySignupController.class, UserController.class,
        LoginController.class })
public class AuthControllerTest extends BaseControllerTest {

    private static final String SIGNUP_URL = "/users/signup/company";
    private static final String DELETE_USER_URL = "/users/delete";
    private static final String LOGIN_URL = "/users/login";

    private final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjIyMjU0NjY4LCJleHAiO";

    @MockitoBean
    private CompanySignupService companySignupService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private LoginService loginService;

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
        // when
        ResultActions resultActions = mockMvc.perform(post(SIGNUP_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        // then
        resultActions.andExpect(jsonPath("$.message")
                .value(ErrorMessage.INVALID_EMAIL_VERIFICATION.getMessage()));
    }

    @Test
    @DisplayName("회원가입 실패 - 중복 이메일인 경우 ")
    void fail_SignUp_ExistsByEmail() throws Exception {
        //given
        SignupRequest signupRequest = new SignupRequest(
                "user@test.com",
                "qwer1234!",
                JobRole.AI_DEVELOP_DATA
        );
        doThrow(new BadRequestException(ErrorMessage.DUPLICATE_EMAIL))
                .when(companySignupService).signup(any(SignupRequest.class));
        //when
        mockMvc.perform(post(SIGNUP_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                //then
                .andExpect(jsonPath("$.message").value(ErrorMessage.DUPLICATE_EMAIL.getMessage()));

    }

    @Test
    @DisplayName("회원 삭제 성공 - 로그인 된 상태")
    void success_deleteUser() throws Exception {
       //given
        User testUser = User.builder()
                .id(1L)
                .email("test@test.com")
                .emailType(EmailType.COMPANY_EMAIL)
                .jobRole(JobRole.AI_DEVELOP_DATA)
                .isCompanyVerified(true)
                .build();
        CustomUserDetails userDetails = new CustomUserDetails(testUser);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
        );

        doNothing().when(userService).deleteUser(any());
        //when
        ResultActions resultActions = mockMvc.perform(delete(DELETE_USER_URL)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("회원 로그인 테스트 ")
    void login_success() throws Exception {
        //given
        LoginRequest loginRequest = new LoginRequest().builder()
                .email("test@test.com")
                .password("qwer1234!")
                .build();

        when(loginService.login(any(LoginRequest.class), any(HttpServletResponse.class)))
                .thenReturn(TOKEN);

        ResultActions resultActions = mockMvc.perform(post(LOGIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.message").value(SuccessMessage.LOGIN_SUCCESS.getMessage()));
        resultActions.andExpect(jsonPath("$.data").value(TOKEN));
    }

}
