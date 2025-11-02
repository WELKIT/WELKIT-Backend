package welkit_server.domain.auth.signup.google.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import welkit_server.domain.user.model.JobRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleSignupRequest {

    private JobRole jobRole;

}
