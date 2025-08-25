package welkit_server.domain.auth.signup.google.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleSignupRequest {

    @NotBlank
    private String password;

    @NotNull
    private JobRole jobRole;

}