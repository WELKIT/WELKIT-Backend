package welkit_server.domain.community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import welkit_server.domain.community.dto.request.PostCreateRequest;
import welkit_server.domain.community.dto.response.PostResponse;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.community.repository.CommunityPostRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityPostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(PostCreateRequest request, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        CommunityPosts post = CommunityPosts.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .status(CommunityPostStatus.NORMAL)
                .build();

        CommunityPosts saved = postRepository.save(post);

        return PostResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .build();
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        // 토큰 검증 + payload에서 userId 추출
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
