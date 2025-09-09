package welkit_server.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.user.model.JobRole;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSummaryResponse {

    private Long postId;
    private String title;
    private String content;
    private Long userId;
    private JobRole jobRole;
    private CommunityPostStatus status;
    private int commentCount;
    private LocalDateTime createdAt;
    private int helpfulCount;
    private int notHelpfulCount;


    public static PostSummaryResponse fromEntity(CommunityPosts post) {
        int commentCount = post.getComments() != null ? post.getComments().size() : 0;
        int helpfulCount = (int) post.getFeedbacks().stream()
                .filter(f -> Boolean.TRUE.equals(f.getIsHelpful()))
                .count();
        int notHelpfulCount = (int) post.getFeedbacks().stream()
                .filter(f -> !Boolean.TRUE.equals(f.getIsHelpful()))
                .count();

        return PostSummaryResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .jobRole(post.getJobRole())
                .status(post.getStatus())
                .commentCount(commentCount)
                .createdAt(post.getCreatedDate())
                .helpfulCount(helpfulCount)
                .notHelpfulCount(notHelpfulCount)
                .build();
    }

}