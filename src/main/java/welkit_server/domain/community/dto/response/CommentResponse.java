package welkit_server.domain.community.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.community.entity.CommunityComments;
import welkit_server.domain.community.entity.CommunityFeedBack;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String content;
    private Long userId;
    private JobRole jobRole;
    private LocalDateTime createdAt;
    private Boolean isHelpful;
    private int helpfulCount;
    private int notHelpfulCount;
    private List<CommentResponse> replies; // 대댓글 재귀 구조

    public static CommentResponse fromEntity(CommunityComments comment, User currentUser) {
        int helpfulCount = (int) comment.getFeedbacks().stream()
                .filter(f -> Boolean.TRUE.equals(f.getIsHelpful()))
                .count();
        int notHelpfulCount = (int) comment.getFeedbacks().stream()
                .filter(f -> Boolean.FALSE.equals(f.getIsHelpful()))
                .count();
        Boolean isHelpful = comment.getFeedbacks().stream()
                .filter(f -> f.getUser().equals(currentUser))
                .map(CommunityFeedBack::getIsHelpful)
                .findFirst()
                .orElse(null);

        List<CommentResponse> replies = comment.getChildren().stream()
                .map(child -> fromEntity(child, currentUser))
                .toList();

        if (replies.isEmpty()) {
            replies = null;
        }

        return CommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getComment())
                .userId(comment.getUser().getId())
                .jobRole(comment.getUser().getJobRole())
                .createdAt(comment.getCreatedAt())
                .isHelpful(isHelpful)
                .helpfulCount(helpfulCount)
                .notHelpfulCount(notHelpfulCount)
                .replies(replies)
                .build();
    }
}