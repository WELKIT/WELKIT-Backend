package welkit_server.domain.mypage.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import welkit_server.domain.mypage.model.FeatureName;

@Getter
@Builder
public class FeatureLockSettingRequest {

    @NotNull
    private final FeatureName featureName;

    @NotNull
    private final Boolean isLocked;

    @JsonCreator
    public FeatureLockSettingRequest(
            @JsonProperty("featureName") FeatureName featureName,
            @JsonProperty("isLocked") Boolean isLocked) {
        this.featureName = featureName;
        this.isLocked = isLocked;
    }
}
