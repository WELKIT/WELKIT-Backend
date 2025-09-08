package welkit_server.domain.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.user.model.JobRole;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private JobRole jobRole;
    private LocalDateTime createdAt; // 게시글 생성일

    public static PostResponse fromEntity(CommunityPosts post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .jobRole(post.getUser().getJobRole())
                .createdAt(post.getCreatedDate())
                .build();
    }
}