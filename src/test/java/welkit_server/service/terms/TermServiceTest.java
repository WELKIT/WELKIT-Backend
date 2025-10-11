package welkit_server.service.terms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import welkit_server.common.fixture.TermCategoryFixture;
import welkit_server.common.fixture.TermFixture;
import welkit_server.common.fixture.UserFixture;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.dto.response.TermsResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.terms.repository.TermRepository;
import welkit_server.domain.terms.service.TermCategoryService;
import welkit_server.domain.terms.service.TermService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.domain.user.service.UserService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TermServiceTest {

    @InjectMocks
    private TermService termService;

    @Mock
    TermCategoryService termCategoryService;

    @Mock
    UserService userService;

    @Mock
    TermCategoryRepository termCategoryRepository;

    @Mock
    TermRepository termRepository;

    @Mock
    private Authentication authentication;

    private User user;
    private Term term;
    private TermCategory termCategory;

    @BeforeEach
    void setUp() {
        user = UserFixture.user(
                1L,
                "test@test.com",
                "qwer1234",
                JobRole.AI_DEVELOP_DATA,
                UserType.USER
        );

        //given
        termCategory = TermCategoryFixture.termCategory(
                1L,
                "카테고리",
                user
        );
        term = TermFixture.term(
                1L,
                "용어1",
                "테스트",
                termCategory,
                user
        );

    }
    @Test
    @DisplayName("용어 사전에서 전체 용어를 조회한다")
    void getAllTermsSuccess() {


        Pageable pageable = PageRequest.of(0, 10);
        Page<Term> termPage = new PageImpl<>(List.of(term), pageable, 1);

        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termRepository.findByUserOrderByCreatedDateDesc(user, pageable)).willReturn(termPage);
        given(termRepository.countByUser(user)).willReturn(1L);
        given(termCategoryRepository.findAlLCategory(user)).willReturn(List.of(termCategory));

        //when
        TermsResponse response = termService.getTerms(0,10,authentication);

        //then
        assertThat(response.getTotalAmount()).isEqualTo(1);
        assertThat(response.getTerms()).hasSize(1);
        assertThat(response.getTerms().get(0).getName()).isEqualTo("용어1");
        assertThat(response.getCategoryNames()).hasSize(1);
        assertThat(response.getCategoryNames().get(0).getCategoryName()).isEqualTo("카테고리");

    }




}
