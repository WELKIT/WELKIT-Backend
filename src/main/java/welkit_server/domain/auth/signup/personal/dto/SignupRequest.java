package welkit_server.domain.auth.signup.personal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import welkit_server.domain.user.model.JobRole;

@Getter
public class SignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private JobRole jobRole;
}
