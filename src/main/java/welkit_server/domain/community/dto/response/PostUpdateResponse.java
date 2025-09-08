package welkit_server.domain.community.dto.response;

import jakarta.persistence.PostUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.user.model.JobRole;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostUpdateResponse {
    private Long id;
    private String title;
    private String content;
    private JobRole jobRole;
    private LocalDateTime updatedAt; // 게시글 수정일

    public static PostUpdateResponse fromEntity(CommunityPosts post) {
        return PostUpdateResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .jobRole(post.getUser().getJobRole())
                .updatedAt(post.getLastModifiedDate())
                .build();
    }
}