package welkit_server.service.terms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import welkit_server.common.fixture.TermCategoryFixture;
import welkit_server.common.fixture.TermFixture;
import welkit_server.common.fixture.UserFixture;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.dto.response.EditTermResponse;
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
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("용어 등록 성공 테스트")
    void createTermSuccess() {
        //given
        CreateTermRequest createTermRequest = CreateTermRequest.builder()
                .name("용어2")
                .definition("용어 등록 테스트")
                .categoryId(1L)
                .build();

        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termCategoryService.findOrCreate(1L, null,authentication)).willReturn(termCategory);
        given(termRepository.save(any(Term.class))).willAnswer(invocation -> invocation.getArgument(0));
        //when
        CreateTermResponse createTermResponse = termService.createTerm(createTermRequest, authentication);

        assertThat(createTermResponse).isNotNull();
        assertThat(createTermResponse.getName()).isEqualTo("용어2");
        assertThat(createTermResponse.getDefinition()).isEqualTo("용어 등록 테스트");
        assertThat(createTermResponse.getCategoryName()).isEqualTo("카테고리");
    }

    @Test
    @DisplayName("용어 수정 성공 테스트")
    void editTermSuccess() {
        // given
        EditTermRequest editTermRequest = EditTermRequest.builder()
                .name("용어3")
                .definition("용어 수정 테스트")
                .categoryName("카테고리1")
                .build();

        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termRepository.findById(1L)).willReturn(Optional.of(term));
        given(termCategoryService.findOrCreate(null, "카테고리1", authentication))
                .willReturn(termCategory);

        // when
        EditTermResponse editTermResponse = termService.editTerm(1L, editTermRequest, authentication);

        // then
        assertThat(editTermResponse).isNotNull();
        assertThat(editTermResponse.getName()).isEqualTo("용어3");
        assertThat(editTermResponse.getDefinition()).isEqualTo("용어 수정 테스트");
    }

    @Test
    @DisplayName("용어 수정 실패 테스트 - 존재하지 않는 용어를 수정할시 예외 발생 ")
    void editTermFail() {
        //given
        EditTermRequest editTermRequest = EditTermRequest.builder()
                .name("용어3")
                .definition("용어 수정 테스트")
                .categoryName("카테고리1")
                .build();
        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termRepository.findById(2L)).willReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> termService.editTerm(2L, editTermRequest, authentication))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(ErrorMessage.TERM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("용어 삭제 성공 테스트")
    void deleteTermSuccess() {
        //given
        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termRepository.findById(1L)).willReturn(Optional.of(term));

        //when
        termService.deleteTerm(1L, authentication);

        //then
        verify(termRepository).delete(term);

    }

    @Test
    @DisplayName("용어 삭제 실패 테스트 - 존재하지 않는 용어를 삭제시 예외발생")
    void deleteTermFail() {
        //given
        given(userService.getAuthenticatedUser(authentication)).willReturn(user);
        given(termRepository.findById(2L)).willReturn(Optional.empty());

        //when / then
        assertThatThrownBy(() -> termService.deleteTerm(2L, authentication))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(ErrorMessage.TERM_NOT_FOUND.getMessage());
    }

}
