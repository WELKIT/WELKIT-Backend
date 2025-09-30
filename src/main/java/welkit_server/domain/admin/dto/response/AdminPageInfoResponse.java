package welkit_server.domain.admin.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPageInfoResponse {

    private Integer totalPages; // 전체 페이지 수
    private Long totalElements; // 등록된 공지사항 수

}
