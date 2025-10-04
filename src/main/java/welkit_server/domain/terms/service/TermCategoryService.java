package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermCategoryService {

    private final TermCategoryRepository termCategoryRepository;
    private final UserService userService;

    @Transactional
    public TermCategory findOrCreate(Long categoryId, String categoryName, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        return termCategoryRepository.findByIdAndUser(categoryId,user)
                .orElseGet(() -> termCategoryRepository.save(
                        TermCategory.builder()
                                .name(categoryName)
                                .user(user)
                                .build()
                ));
    }

}
