package welkit_server.domain.mypage.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.mypage.model.FeatureName;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolveLockRequest {

    @NotNull
    private FeatureName featureName;

    @NotBlank
    private String lockPin;

}
