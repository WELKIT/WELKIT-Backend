package welkit_server.user.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;

@Service
@Profile("test")
@RequiredArgsConstructor
@Getter
public class UserServiceTest {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LockSettingRepository lockSettingRepository;

    public void signupDirectly(String email, String password, JobRole jobRole) {
        User user = userRepository.save(User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .jobRole(jobRole)
                .isCompanyVerified(false) // 테스트에서 바로 인증 완료
                .emailType(EmailType.PERSONAL_EMAIL)
                .build());

        List<LockSetting> lockSettings = Arrays.stream(FeatureName.values())
                .map(feature -> LockSetting.builder()
                        .user(user)
                        .featureName(feature)
                        .isLocked(false)
                        .build())
                .toList();

        lockSettingRepository.saveAll(lockSettings);
    }

}
