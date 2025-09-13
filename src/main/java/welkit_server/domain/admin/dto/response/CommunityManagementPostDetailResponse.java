package welkit_server.domain.admin.dto.response;

import lombok.*;
import welkit_server.domain.community.entity.CommunityPosts;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommunityManagementPostDetailResponse {

    private Long id;
    private String title;
    private String content;

    public static CommunityManagementPostDetailResponse fromEntity(CommunityPosts post) {
        return CommunityManagementPostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}

