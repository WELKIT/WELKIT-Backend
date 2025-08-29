package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermCategoryService {

    private final TermCategoryRepository termCategoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public TermCategory findOrCreate(Long categoryId, String categoryName, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        return termCategoryRepository.findByIdAndUser(categoryId,user)
                .orElseGet(() -> termCategoryRepository.save(
                        TermCategory.builder()
                                .name(categoryName)
                                .user(user)
                                .build()
                ));
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
