package welkit_server.user.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.config.TestConfig;
import welkit_server.domain.user.model.JobRole;
import welkit_server.user.service.UserServiceTest;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestConfig
@Transactional
public class UserControllerTest {

    @Autowired
    private UserServiceTest userServiceTest;

    @Test
    @DisplayName("테스트용 10명 회원 생성")
    @Rollback(false)
    void createTenUsersBDD() {
        // given
        int numberOfUsers = 10;
        // when
        for (int i = 1; i <= numberOfUsers; i++) {
            userServiceTest.signupDirectly(
                    "test" + i + "@gmail.com",
                    "12345678!",
                    JobRole.AI_DEVELOP_DATA
            );
        }
        // then
        long count = userServiceTest.getUserRepository().count();
        assertThat(count).isGreaterThanOrEqualTo(numberOfUsers);
    }

}
