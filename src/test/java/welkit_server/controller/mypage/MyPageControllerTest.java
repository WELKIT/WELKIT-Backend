package welkit_server.controller.mypage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import welkit_server.controller.BaseControllerTest;
import welkit_server.domain.admin.dto.response.AdminPageInfoResponse;
import welkit_server.domain.admin.dto.response.GetAllNoticeResponse;
import welkit_server.domain.admin.dto.response.NoticeResponse;
import welkit_server.domain.admin.model.NoticePriority;
import welkit_server.domain.admin.service.NoticeService;
import welkit_server.domain.mypage.controller.MyPageController;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.SolveLockRequest;
import welkit_server.domain.mypage.dto.request.UpdateJobRoleRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.dto.response.MyPageResponse;
import welkit_server.domain.mypage.dto.response.UpdateJobRoleResponse;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.mypage.service.MyPageService;
import welkit_server.domain.user.dto.response.UserInfoResponse;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("마이페이지 컨트롤러 테스트")
@WebMvcTest(controllers = MyPageController.class)
public class MyPageControllerTest extends BaseControllerTest {

    private static final String DEFAULT_URL = "/mypage";
    private static final String GET_NOTICES_URL = DEFAULT_URL + "/notices"; //공지사항 조회
    private static final String LOCK_URL = DEFAULT_URL + "/lock"; // 암호 설정
    private static final String LOCK_PIN_URL = LOCK_URL + "/pin"; //암호 생성
    private static final String LOCK_VERIFY_URL = LOCK_URL + "/verify"; // 기능별 암호 검증
    private static final String CHANGE_JOB_ROLE = DEFAULT_URL + "/job"; //사용자 직무 변경


    @MockitoBean
    private MyPageService myPageService;

    @MockitoBean
    private NoticeService noticeService;

    @MockitoBean
    private RedisTemplate<String, String> redisTemplate;

    @MockitoBean
    private LockSettingRepository lockSettingRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@test.com")
                .emailType(EmailType.COMPANY_EMAIL)
                .password("qwer1234!")
                .isCompanyVerified(true)
                .jobRole(JobRole.AI_DEVELOP_DATA)
                .lockPin("1234")
                .userType(UserType.USER)
                .build();
    }

    Authentication authentication = new UsernamePasswordAuthenticationToken(testUser, null, new ArrayList<>());

    @Test
    @DisplayName("전체 마이페이지 조회 성공 데스트")
    void getAllMyPages_success() throws Exception {
        //given
        MyPageResponse myPageResponse = MyPageResponse.builder()
                .user(UserInfoResponse.fromEntity(testUser))
                .lockSettings(List.of())
                .build();

        given(myPageService.getMyPage(authentication)).willReturn(myPageResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(DEFAULT_URL)
                .principal(authentication));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.user.email").value("test@test.com"));

    }

    @Test
    @DisplayName("마이페이지 공지사항 조회 성공 테스트")
    void getAllNotices_success() throws Exception {
        //given
        List<GetAllNoticeResponse> notices = List.of(
                GetAllNoticeResponse.builder()
                        .noticeId(1L)
                        .title("공지사항테스트데이터")
                        .content("테스트")
                        .priority(NoticePriority.MEDIUM)
                        .lastModified(now())
                        .build()
        );
        NoticeResponse noticeResponse = NoticeResponse.builder()
                .notices(notices)
                .noticePageInfo(
                        AdminPageInfoResponse.builder()
                                .totalPages(1)
                                .totalElements((long) notices.size())
                                .build()
                )
                .build();

        given(noticeService.getAllNotices(0,3, authentication)).willReturn(noticeResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(GET_NOTICES_URL)
                .principal(authentication));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.notices.length()").value(1));
        resultActions.andExpect(jsonPath("$.data.notices[0].title").value("공지사항테스트데이터"));
        resultActions.andExpect(jsonPath("$.data.notices[0].content").value("테스트"));
        resultActions.andExpect(jsonPath("$.data.notices[0].priority").value("MEDIUM"));
        resultActions.andExpect(jsonPath("$.data.notices[0].lastModified").value("2025-10-11"));
        resultActions.andExpect(jsonPath("$.data.noticePageInfo.totalPages").value(1));
        resultActions.andExpect(jsonPath("$.data.noticePageInfo.totalElements").value(1));

    }

    @Test
    @DisplayName("마이페이지 암호 생성 성공 테스트")
    void setLock_success() throws Exception {
        //given
        LockSettingRequest lockSettingRequest = LockSettingRequest.builder()
                .lockPin("1234")
                .build();

        doNothing().when(myPageService).setLock(any(LockSettingRequest.class), any(Authentication.class));

        //when
        ResultActions resultActions = mockMvc.perform(put(LOCK_PIN_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lockSettingRequest)));

        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("마이페이지 암호 생성 실패 테스트- 암호를 입력하지않은 경우 ")
    void setLock_fail_input_null() throws Exception {
        //given
        LockSettingRequest lockSettingRequest = null;

        doThrow(new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST))
                .when(myPageService).setLock(any(LockSettingRequest.class), any(Authentication.class));
        //when
        ResultActions resultActions = mockMvc.perform(put(LOCK_PIN_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lockSettingRequest)));

        //then
        resultActions.andExpect(jsonPath("$.message")
                .value(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST.getMessage()));

    }

    @Test
    @DisplayName("마이페이지 암호 생성 실패 테스트 - 암호 길이가 4자리 이상인경우 ")
    void setLock_fail_input_wrong_length() throws Exception {
        //given
        LockSettingRequest lockSettingRequest = LockSettingRequest.builder()
                .lockPin("1234675")
                .build();

        doThrow(new BadRequestException(ErrorMessage.MYP_LOCK_PIN_INVALID))
                .when(myPageService).setLock(any(LockSettingRequest.class), any(Authentication.class));
        //when
        ResultActions resultActions = mockMvc.perform(put(LOCK_PIN_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lockSettingRequest)));

        //then
        resultActions.andExpect(jsonPath("$.message")
                .value(ErrorMessage.MYP_LOCK_PIN_INVALID.getMessage()));

    }

    @Test
    @DisplayName("마이페이지 기능 암호 설정 성공 테스트")
    void setFeatureLock_success() throws Exception {
        //given
        LockSetting lockSetting = LockSetting.builder()
                .id(1L)
                .user(testUser)
                .featureName(FeatureName.INSIGHT_CARDS)
                .isLocked(true)
                .build();


        FeatureLockSettingRequest featureLockSettingRequest = FeatureLockSettingRequest.builder()
                .featureName(FeatureName.INSIGHT_CARDS)
                .isLocked(true)
                .build();

        FeatureLockSettingResponse featureLockSettingResponse = FeatureLockSettingResponse.fromEntity(lockSetting);

        given(myPageService.setFeatureLock(any(FeatureLockSettingRequest.class), any(Authentication.class)))
                        .willReturn(featureLockSettingResponse);

        //when
        ResultActions resultActions = mockMvc.perform(patch(LOCK_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(featureLockSettingRequest)));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.featureName").value("INSIGHT_CARDS"));
        resultActions.andExpect(jsonPath("$.data.locked").value(true));

    }

    @Test
    @DisplayName("마이페이지 기능 암호 검증 성공 테스트")
    void solveFeatureLock_success() throws Exception {
        //given
        SolveLockRequest solveLockRequest = SolveLockRequest.builder()
                .lockPin("1234")
                .featureName(FeatureName.INSIGHT_CARDS)
                .build();
        doNothing().when(myPageService).solveFeatureLock(any(SolveLockRequest.class), any(Authentication.class));

        //when
        ResultActions resultActions = mockMvc.perform(post(LOCK_VERIFY_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solveLockRequest)));

        resultActions.andExpect(status().isOk());

    }

    @Test
    @DisplayName("마이페이지 기능 암호 검증 실패 테스트 - 암호가 틀린 경우" )
    void solveFeatureLock_fail_input_wrong() throws Exception {
        //given
        SolveLockRequest solveLockRequest = SolveLockRequest.builder()
                .lockPin("2345")
                .featureName(FeatureName.INSIGHT_CARDS)
                .build();

        doThrow(new BadRequestException(ErrorMessage.MYP_INVALID_LOCK_PIN))
                .when(myPageService).solveFeatureLock(any(SolveLockRequest.class), any(Authentication.class));

        //when
        ResultActions resultActions = mockMvc.perform(post(LOCK_VERIFY_URL)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(solveLockRequest)));

        //then
        resultActions.andExpect(jsonPath("$.message")
                .value(ErrorMessage.MYP_INVALID_LOCK_PIN.getMessage()));

    }

    @Test
    @DisplayName("마이페이지 사용자 직무 변경 성공 테스트")
    void changeJobRole_success() throws Exception {
        //given
        UpdateJobRoleRequest updateJobRoleRequest = UpdateJobRoleRequest.builder()
                .jobRole(JobRole.DESIGN)
                .build();

        UpdateJobRoleResponse updateJobRoleResponse = UpdateJobRoleResponse.builder()
                .jobRole(JobRole.DESIGN)
                .build();
        given(myPageService.updateJobRole(any(UpdateJobRoleRequest.class), any(Authentication.class)))
                .willReturn(updateJobRoleResponse);

        //when
        ResultActions resultActions = mockMvc.perform(patch(CHANGE_JOB_ROLE)
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateJobRoleRequest)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.jobRole").value(JobRole.DESIGN.toString()));

    }

}

