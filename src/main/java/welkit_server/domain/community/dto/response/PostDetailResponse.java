package welkit_server.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.community.entity.CommunityFeedBack;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {

    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private JobRole jobRole;
    private CommunityPostStatus status;
    private int commentCount;
    private LocalDateTime createdAt;
    private Boolean isHelpful;
    private int helpfulCount;
    private int notHelpfulCount;
    private List<CommentResponse> comments;

    public static PostDetailResponse fromEntity(CommunityPosts post, User currentUser) {
        int commentCount = post.getComments().size();

        int helpfulCount = (int) post.getFeedbacks().stream()
                .filter(f -> Boolean.TRUE.equals(f.getIsHelpful()))
                .count();
        int notHelpfulCount = (int) post.getFeedbacks().stream()
                .filter(f -> Boolean.FALSE.equals(f.getIsHelpful()))
                .count();

        Boolean isHelpful = (post.getFeedbacks() == null ? null :
                post.getFeedbacks().stream()
                        .filter(f -> f.getUser() != null && f.getUser().equals(currentUser))
                        .map(CommunityFeedBack::getIsHelpful)
                        .findFirst()
                        .orElse(null));

        List<CommentResponse> comments = post.getComments().stream()
                .filter(c -> c.getParent() == null) // 최상위 댓글만
                .map(c -> CommentResponse.fromEntity(c, currentUser))
                .toList();

        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .jobRole(post.getJobRole())
                .status(post.getStatus())
                .commentCount(commentCount)
                .createdAt(post.getCreatedDate())
                .isHelpful(isHelpful)
                .helpfulCount(helpfulCount)
                .notHelpfulCount(notHelpfulCount)
                .comments(comments)
                .build();
    }
}