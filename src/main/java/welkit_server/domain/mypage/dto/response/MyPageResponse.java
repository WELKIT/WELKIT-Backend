package welkit_server.domain.mypage.dto.response;

import lombok.*;
import welkit_server.domain.admin.dto.response.GetAllNoticeResponse;
import welkit_server.domain.user.dto.response.UserInfoResponse;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MyPageResponse {
    private UserInfoResponse user;
    private List<GetAllNoticeResponse> noticeList;
    private List<FeatureLockSettingResponse> lockSettings;
}
