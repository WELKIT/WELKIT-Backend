package welkit_server.domain.auth.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;

    private String googleEmail;

    @NotBlank
    private String password;

}
