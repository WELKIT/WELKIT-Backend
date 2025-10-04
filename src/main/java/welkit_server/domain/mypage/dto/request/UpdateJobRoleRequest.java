package welkit_server.domain.mypage.dto.request;

import lombok.*;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateJobRoleRequest {

    private JobRole jobRole;

}
