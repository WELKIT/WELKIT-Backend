package welkit_server.domain.admin.dto.response;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeResponse {

    private AdminPageInfoResponse noticePageInfo;
    private List<GetAllNoticeResponse> notices;

}
