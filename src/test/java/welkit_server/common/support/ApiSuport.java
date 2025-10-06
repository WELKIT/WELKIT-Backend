package welkit_server.common.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import welkit_server.common.fixture.user.UserFixture;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.security.jwt.JWTUtil;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiSuport {

    private static final String BEARER = "Bearer ";
    private User loginAdmin;
    private User loginUser;
    protected String accessTokenOfUser;
    protected String accessTokenOfAdmin;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Mock
    private JWTUtil jwtUtil;

    protected String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public void setUpUser() {
        String password = "qwer1234!";
        String encodePassword = passwordEncoder.encode(password);

        if(loginAdmin != null && loginUser != null) return;

        this.loginAdmin = userRepository.save(UserFixture.user(
                1L,"admin@test.com", encodePassword, JobRole.AI_DEVELOP_DATA, UserType.ADMIN));
        this.loginUser = userRepository.save(UserFixture.user(
                2L, "user@test.com", encodePassword, JobRole.AI_DEVELOP_DATA, UserType.USER));

        long expiredMs = 3600_000L; // 1시간

        // 테스트용이라면 jwtUtil Mock 사용
        this.accessTokenOfAdmin = BEARER + jwtUtil.createJwt(
                loginAdmin.getEmail(),
                loginAdmin.getId(),
                loginAdmin.getUserType().name(),
                loginAdmin.getJobRole().name(),
                expiredMs
        );

        this.accessTokenOfUser = BEARER + jwtUtil.createJwt(
                loginUser.getEmail(),
                loginUser.getId(),
                loginUser.getUserType().name(),
                loginUser.getJobRole().name(),
                expiredMs
        );
    }
}

