package welkit_server.domain.community.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPageInfoResponse {

    private Integer totalPages; // 전체 페이지 수
    private Long totalElements; // 전체 게시글 수

}
