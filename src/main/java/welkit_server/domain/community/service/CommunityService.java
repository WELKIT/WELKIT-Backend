package welkit_server.domain.community.service;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import welkit_server.domain.community.dto.request.*;
import welkit_server.domain.community.dto.response.*;
import welkit_server.domain.community.entity.CommunityComments;
import welkit_server.domain.community.entity.CommunityFeedBack;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.community.model.TargetType;
import welkit_server.domain.community.repository.CommunityCommentRepository;
import welkit_server.domain.community.repository.CommunityFeedBackRepository;
import welkit_server.domain.community.repository.CommunityPostRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityPostRepository postRepository;
    private final CommunityFeedBackRepository feedbackRepository;
    private final CommunityCommentRepository commentRepository;
    private final UserService userService;

    public PostPageResponse getAllCommunityPosts(JobRole jobRole, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommunityPosts> posts;

        if (jobRole != null) {
            posts = postRepository.findAllByJobRole(jobRole, pageable);
        } else {
            posts = postRepository.findAll(pageable);
        }

        List<PostSummaryResponse> postSummaries = posts.getContent().stream()
                .map(PostSummaryResponse::fromEntity)
                .toList();

        PostPageInfoResponse pageInfo = getPostsInfo(posts);

        return PostPageResponse.builder()
                .postInfo(pageInfo)
                .posts(postSummaries)
                .build();
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long postId, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);
        CommunityPosts post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        if (post.getFeedbacks() == null) post.setFeedbacks(new ArrayList<>());
        if (post.getComments() == null) post.setComments(new ArrayList<>());

        return PostDetailResponse.fromEntity(post, user);
    }

    @Transactional(readOnly = true)
    public PostPageResponse getMyPosts(int page, int size, JobRole jobRole, Authentication authentication) {
        User currentUser = userService.getAuthenticatedUser(authentication);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CommunityPosts> posts = postRepository.findByUserAndOptionalJobRole(currentUser, jobRole, pageable);

        List<PostSummaryResponse> myPostSummaries = posts.stream()
                .map(PostSummaryResponse::fromEntity)
                .toList();

        PostPageInfoResponse pageInfo = getPostsInfo(posts);

        return PostPageResponse.builder()
                .postInfo(pageInfo)
                .posts(myPostSummaries)
                .build();
    }

    @Transactional(readOnly = true)
    public PostPageResponse getMyCommentedPosts(int page, int size, JobRole jobRole, Authentication authentication) {
        User currentUser = userService.getAuthenticatedUser(authentication);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<CommunityPosts> posts = postRepository.findDistinctByComments_UserAndOptionalJobRole(currentUser, jobRole, pageable);

        List<PostSummaryResponse> myCommentedPosts = posts.stream()
                .map(PostSummaryResponse::fromEntity)
                .toList();

        PostPageInfoResponse pageInfo = getPostsInfo(posts);

        return PostPageResponse.builder()
                .postInfo(pageInfo)
                .posts(myCommentedPosts)
                .build();
    }

    public PostPageResponse searchPosts(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<CommunityPosts> posts;

        if (keyword == null || keyword.isBlank()) {
            posts = postRepository.findAll(pageable);
        } else {
            posts = postRepository.searchPostsOptionalKeyword(keyword, pageable);
        }

        List<PostSummaryResponse> searchPosts = posts.stream()
                .map(PostSummaryResponse::fromEntity)
                .toList();

        PostPageInfoResponse pageInfo = getPostsInfo(posts);

        return  PostPageResponse.builder()
                .postInfo(pageInfo)
                .posts(searchPosts)
                .build();
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        checkIsCompanyVerified(user);

        CommunityPosts post = CommunityPosts.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .jobRole(request.getJobRole())
                .user(user)
                .status(CommunityPostStatus.NORMAL)
                .build();

        CommunityPosts saved = postRepository.save(post);

        return PostResponse.fromEntity(saved);
    }

    @Transactional
    public PostUpdateResponse updatePost(Long postId, PostUpdateRequest request, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

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
        User user = userService.getAuthenticatedUser(authentication);

        CommunityPosts post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorMessage.WK_NO_PERMISSION);
        }

        postRepository.delete(post);
    }

    @Transactional
    public CommentResponse createComment(CommentCreateRequest request, Long postId, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        checkIsCompanyVerified(user);

        CommunityPosts post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        CommunityComments parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_COMMENT_NOT_FOUND));
        }

        CommunityComments comment = CommunityComments.builder()
                .comment(request.getContent())
                .user(user)
                .post(post)
                .parent(parent)
                .feedbacks(new ArrayList<>())
                .build();

        CommunityComments saved = commentRepository.save(comment);
        return CommentResponse.fromEntity(saved, user);
    }

    @Transactional
    public CommentUpdateResponse updateComment(Long commentId, CommentUpdateRequest request, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        CommunityComments comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorMessage.WK_NO_PERMISSION);
        }

        comment.editComment(request.getContent());

        return CommentUpdateResponse.fromEntity(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        CommunityComments comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_COMMENT_NOT_FOUND));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException(ErrorMessage.WK_NO_PERMISSION);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public FeedbackResponse toggleHelpful(FeedbackRequest request, Authentication authentication) {
        User user = userService.getAuthenticatedUser(authentication);

        checkIsCompanyVerified(user);

        Long targetId = request.getTargetId();
        TargetType targetType = request.getTargetType();
        Boolean isHelpful = request.getIsHelpful();

        if (targetType == TargetType.POSTS && !postRepository.existsById(targetId)) {
            throw new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND);
        }
        if (targetType == TargetType.COMMENTS && !commentRepository.existsById(targetId)) {
            throw new NotFoundException(ErrorMessage.COMMUNITY_COMMENT_NOT_FOUND);
        }

        CommunityFeedBack feedback = feedbackRepository
                .findByTargetTypeAndTargetIdAndUser(targetType, targetId, user)
                .orElseGet(() -> feedbackRepository.save(CommunityFeedBack.create(targetType, targetId, user)));

        feedback.toggleHelpful(isHelpful);

        int helpfulCount = feedbackRepository.countByTargetTypeAndTargetIdAndIsHelpful(targetType, targetId, true);
        int notHelpfulCount = feedbackRepository.countByTargetTypeAndTargetIdAndIsHelpful(targetType, targetId, false);

        return FeedbackResponse.fromEntity(targetType, targetId, helpfulCount, notHelpfulCount);
    }

    public PostPageInfoResponse getPostsInfo(Page<CommunityPosts> posts){

        PostPageInfoResponse pageInfo = PostPageInfoResponse.builder()
                .totalPages(posts.getTotalPages())
                .totalElements(posts.getTotalElements())
                .build();

        return pageInfo;
    }

    private static void checkIsCompanyVerified(User user) {
        if (!user.isCompanyVerified()) {
            throw new UnauthorizedException(ErrorMessage.COMMUNITY_COMPANY_NOT_VERIFIED);
        }
    }

}
