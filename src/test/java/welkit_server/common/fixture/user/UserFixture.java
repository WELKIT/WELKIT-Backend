package welkit_server.common.fixture.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFixture {

    public static User user(Long id, String email, String password, JobRole jobRole) {
        return User.builder()
                .id(id)
                .email(email)
                .emailType(EmailType.COMPANY_EMAIL)
                .password(password)
                .jobRole(jobRole)
                .isCompanyVerified(true)
                .userType(UserType.USER)
                .build();
    }



    public static User createUser(String email, String password, JobRole jobRole) {
        return User.builder()
                .email(email)
                .password(password)
                .jobRole(jobRole)
                .build();
    }

}
