package welkit_server.domain.community.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateRequest {
    @NotNull
    private JobRole jobRole;
    @NotBlank
    @Size(min = 1, max = 30)
    private String title;
    @NotBlank
    @Size(min = 1, max = 2000)
    private String content;
}