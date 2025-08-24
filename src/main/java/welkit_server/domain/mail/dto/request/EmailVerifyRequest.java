package welkit_server.domain.mail.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class EmailVerifyRequest {

    @NotBlank(message = "이메일은 필수입니다")
    private String email;

    @NotBlank(message = "인증 코드는 필수 입니다")
    private String code;

}
