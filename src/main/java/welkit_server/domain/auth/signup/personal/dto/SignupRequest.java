package welkit_server.domain.auth.signup.personal.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
public class SignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?]).+$",
            message = "비밀번호는 최소 1개의 특수문자를 포함해야 합니다.")
    private String password;

    @NotNull
    private JobRole jobRole;
}
