package welkit_server.domain.auth.logout.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequest {

    @NotBlank
    private String token;
}