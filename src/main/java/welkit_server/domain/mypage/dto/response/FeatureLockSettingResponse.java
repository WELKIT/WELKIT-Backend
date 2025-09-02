package welkit_server.domain.mypage.dto.response;

import lombok.*;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FeatureLockSettingResponse {

    private FeatureName featureName;
    private boolean isLocked;

    public static FeatureLockSettingResponse fromEntity(LockSetting lockSetting) {
        return FeatureLockSettingResponse.builder()
                .featureName(lockSetting.getFeatureName())
                .isLocked(lockSetting.isLocked())
                .build();
    }
}
