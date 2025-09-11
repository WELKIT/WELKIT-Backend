package welkit_server.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import welkit_server.domain.community.entity.CommunityComments;

@Getter
@Builder
@AllArgsConstructor
public class CommentUpdateResponse {
    private Long commentId;
    private String content;
    private Long postId;
    private Long parentId;

    public static CommentUpdateResponse fromEntity(CommunityComments comment) {
        return CommentUpdateResponse.builder()
                .commentId(comment.getId())
                .content(comment.getComment())
                .postId(comment.getPost().getId())
                .parentId(comment.getParent() != null ? comment.getParent().getId() : null)
                .build();
    }
}
