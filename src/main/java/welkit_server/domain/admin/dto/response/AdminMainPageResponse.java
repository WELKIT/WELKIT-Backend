package welkit_server.domain.admin.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminMainPageResponse {

    private List<GetAllNoticeResponse> noticeList;
    private List<CommunityManagementPostResponse> communityManagementPosts;

}
