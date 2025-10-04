package welkit_server.domain.mypage.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LockSettingRequest {

    @NotBlank
    private String lockPin;

}
