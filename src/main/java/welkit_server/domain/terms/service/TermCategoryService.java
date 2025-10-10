package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermCategoryService {

    private final TermCategoryRepository termCategoryRepository;
    private final UserService userService;

    @Transactional
    public TermCategory findOrCreate(Long categoryId, String categoryName, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        if (categoryId != null) {
            return termCategoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.CATEGORY_NOT_FOUND));
        }

        if (categoryName != null) {
            // 이미 존재하는 카테고리 확인
            return termCategoryRepository.findByNameAndUser((categoryName), user)
                    .orElseGet(() -> {
                        TermCategory newCategory = new TermCategory(categoryName, user);
                        return termCategoryRepository.save(newCategory);
                    });
        }

        throw new BadRequestException(ErrorMessage.WK_VALIDATION_NULL_OR_BLANK);
    }

}
