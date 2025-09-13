package welkit_server.domain.admin.dto.response;

import lombok.*;
import welkit_server.domain.community.entity.CommunityPosts;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommunityManagementPostResponse {

    private Long id;
    private String title;
    private int notHelpfulCount;
    private LocalDateTime createdAt;

    public static CommunityManagementPostResponse fromEntity(CommunityPosts post){

        int notHelpfulCount = (int) post.getFeedbacks().stream()
                .filter(f -> Boolean.FALSE.equals(f.getIsHelpful()))
                .count();

        return CommunityManagementPostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .notHelpfulCount(notHelpfulCount)
                .createdAt(post.getCreatedDate())
                .build();
    }

}
