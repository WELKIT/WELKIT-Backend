package welkit_server.domain.mypage.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.mypage.model.FeatureName;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class LockSettingRequest {

    @NotNull
    private FeatureName featureName;

    @NotBlank
    private String lockPin;

}
