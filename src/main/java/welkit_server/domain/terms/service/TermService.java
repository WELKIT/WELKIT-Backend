package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.EditTermResponse;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.dto.response.GetAllTermResponse;
import welkit_server.domain.terms.dto.response.TermsResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.terms.repository.TermRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.ForbiddenException;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final TermCategoryRepository termCategoryRepository;
    private final TermCategoryService termCategoryService;

    public TermsResponse getTerms(int page, int size, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page,size);

        Page<Term> termList = termRepository.findByUserOrderByCreatedDateDesc(user,pageable);

        long totalCount = termRepository.countByUser(user);

        List<GetAllTermResponse> terms =  termList.getContent().stream()
                .map(term -> GetAllTermResponse.builder()
                        .termId(term.getId())
                        .name(term.getName())
                        .definition(term.getDefinition())
                        .categoryId(term.getCategory().getId())
                        .updatedAt(term.getLastModifiedDate())
                        .build())
                .toList();

        return TermsResponse.builder()
                .totalAmount(totalCount)
                .terms(terms)
                .build();
    }

    public TermsResponse getCategoryTerms( int page, int size, List<Long> categoryIds, Authentication authentication){
        User user = getAuthenticatedUser(authentication);

        List<Long> validCategoryIds = categoryIds.stream()
                .filter(id -> termCategoryRepository.findByIdAndUser(id, user).isPresent())
                .toList();

        if (validCategoryIds.isEmpty()) {
            throw new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<Term> sortedCategoryTerms =
                termRepository.findAllByUserAndCategoryIds(user, validCategoryIds, pageable);

        long categoryCount = termRepository.countByUserAndCategoryIds(user, validCategoryIds);

        List<GetAllTermResponse> categorySortedTerms = sortedCategoryTerms.stream()
                .map(term -> GetAllTermResponse.builder()
                        .termId(term.getId())
                        .name(term.getName())
                        .definition(term.getDefinition())
                        .categoryId(term.getCategory().getId())
                        .build())
                .toList();

        return TermsResponse.builder()
                .totalAmount(categoryCount)
                .terms(categorySortedTerms)
                .build();

    }

    @Transactional
    public CreateTermResponse createTerm(CreateTermRequest createTermRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        TermCategory category = termCategoryService.findOrCreate(
                createTermRequest.getCategoryId(),
                createTermRequest.getCategoryName(),
                authentication
        );

        Term term = createTermRequest.toEntity(category);
        term.setUser(user);

        termRepository.save(term);

        return CreateTermResponse.fromEntity(term);
    }

    @Transactional
    public EditTermResponse editTerm(Long termId, EditTermRequest editTermRequest, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        Term term = findOwnedTerm(currentUser.getId(), termId);
        TermCategory category = termCategoryService.findOrCreate(
                editTermRequest.getCategoryId(),
                editTermRequest.getCategoryName(),
                authentication
        );

        term.editTerm(editTermRequest, category);

        return EditTermResponse.    fromEntity(term);
    }

    @Transactional
    public void deleteTerm(Long termId, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        Term term = findOwnedTerm(currentUser.getId(), termId);

        termRepository.delete(term);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

    public Term getTermById(Long termId) {
        return termRepository.findById(termId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TERM_NOT_FOUND));
    }

    private void validateOwnership(Long currentUserId, Term term) {
        if (!term.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException(ErrorMessage.WK_NO_PERMISSION);
        }
    }

    private Term findOwnedTerm(Long userId, Long termId) {
        Term term = getTermById(termId);
        validateOwnership(userId,term);
        return term;
    }

}
