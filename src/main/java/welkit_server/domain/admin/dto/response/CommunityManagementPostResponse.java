package welkit_server.domain.admin.dto.response;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CommunityManagementPostResponse {

    private AdminPageInfoResponse communityManagementPostInfo;
    private List<GetAllCommunityManagementPostResponse> communityManagementPostResponses; // 수정됨

}
