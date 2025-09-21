package welkit_server.domain.mypage.dto.response;

import lombok.*;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UpdateJobRoleResponse {

    private JobRole jobRole;

}
