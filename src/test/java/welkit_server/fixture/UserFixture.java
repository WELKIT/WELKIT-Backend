package welkit_server.fixture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFixture {

    public static User createUser(String email, String password, JobRole jobRole) {
        return User.builder()
                .email(email)
                .password(password)
                .jobRole(jobRole)
                .build();
    }

}
