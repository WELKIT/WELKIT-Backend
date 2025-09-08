package welkit_server.domain.community.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import welkit_server.domain.community.dto.request.PostCreateRequest;
import welkit_server.domain.community.dto.request.PostUpdateRequest;
import welkit_server.domain.community.dto.response.PostResponse;
import welkit_server.domain.community.dto.response.PostUpdateResponse;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.community.repository.CommunityPostRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
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

        return PostResponse.fromEntity(saved);
    }

    @Transactional
    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest request, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        CommunityPosts post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorMessage.WK_NO_PERMISSION);
        }

        post.editPost(request);

        return PostUpdateResponse.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long postId, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        CommunityPosts post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorMessage.WK_NO_PERMISSION);
        }

        postRepository.delete(post);
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
