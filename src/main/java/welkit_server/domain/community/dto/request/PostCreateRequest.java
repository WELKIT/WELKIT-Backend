package welkit_server.domain.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    @NotNull
    private JobRole jobRole;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
