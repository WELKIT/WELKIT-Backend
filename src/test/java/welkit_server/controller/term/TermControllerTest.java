package welkit_server.controller.term;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.ResultActions;
import welkit_server.common.fixture.TermCategoryFixture;
import welkit_server.common.fixture.TermFixture;
import welkit_server.controller.BaseControllerTest;
import welkit_server.domain.mypage.intercepter.FeatureLockInterceptor;
import welkit_server.domain.terms.controller.TermController;
import welkit_server.domain.terms.dto.response.GetAllCategoryName;
import welkit_server.domain.terms.dto.response.GetAllTermResponse;
import welkit_server.domain.terms.dto.response.TermsResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.service.TermService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import java.util.ArrayList;
import java.util.List;
import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("용어사전 컨트롤러 테스트")
@WebMvcTest(controllers = TermController.class)
public class TermControllerTest extends BaseControllerTest {

    private static final String DEFAULT_URL = "/terms";
    private static final String CATEGORY_SEARCH_URL = DEFAULT_URL + "/category";
    private static final String EDIT_DELETE_URL = DEFAULT_URL + "/{id}";

    @MockitoBean
    private TermService termService;

    @MockitoBean
    private FeatureLockInterceptor featureLockInterceptor;

    @BeforeEach
    void setupInterceptor() {
        given(featureLockInterceptor.preHandle(any(), any(), any())).willReturn(true);
    }

    private User testUser;
    private Term term;
    private TermCategory termCategory;

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
        //given
        termCategory = TermCategoryFixture.termCategory(
                1L,
                "카테고리",
                testUser
        );
        term = TermFixture.term(
                1L,
                "용어1",
                "테스트",
                termCategory,
                testUser
        );
    }



    Authentication authentication = new UsernamePasswordAuthenticationToken(testUser,null,new ArrayList<>());

    @Test
    @DisplayName("용어 조회 성공 테스트")
    void getAllTerms() throws Exception {
        //given
        List<GetAllCategoryName> categoryNames = List.of(
                GetAllCategoryName.builder()
                        .categoryId(1L)
                        .categoryName("카테고리")
                        .build()
        );

        List<GetAllTermResponse> terms = List.of(
                GetAllTermResponse.builder()
                        .termId(1L)
                        .name("용어사전테스트")
                        .definition("용어사전테스트용 단어")
                        .categoryId(1L)
                        .updatedAt(now())
                        .build()
        );

        TermsResponse termsResponse = TermsResponse.builder()
                .totalAmount((long) terms.size())
                .categoryNames(categoryNames)
                .terms(terms)
                .build();

        given(termService.getTerms(0, 10, authentication)).willReturn(termsResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(DEFAULT_URL)
                .principal(authentication));

        //then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.totalAmount").value(terms.size()));
        resultActions.andExpect(jsonPath("$.data.terms[0].name").value("용어사전테스트"));
        resultActions.andExpect(jsonPath("$.data.categoryNames[0].categoryName").value("카테고리"));
    }




}
