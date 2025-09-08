package welkit_server.domain.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private JobRole jobRole;
    private String title;
    private String content;
}
