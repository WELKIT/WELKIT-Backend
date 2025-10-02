package welkit_server.domain.auth.signup.personal.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 64, message = "비밀번호는 8자 이상 64자 이하이어야 합니다.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?]).+$",
            message = "비밀번호는 최소 1개의 숫자와 1개의 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotNull
    private JobRole jobRole;
}
