package welkit_server.domain.auth.signup.personal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.auth.signup.personal.dto.SignupRequest;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.redis.RedisUtil;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonalSignupService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final LockSettingRepository lockSettingRepository;

    @Transactional
    public void signup(SignupRequest request) {
        String email = request.getEmail();

        if (!redisUtil.isVerifiedEmail(email)) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .jobRole(request.getJobRole())
                .isCompanyVerified(false)
                .emailType(EmailType.PERSONAL_EMAIL)
                .build();

        userRepository.save(user);

        List<LockSetting> lockSettings = Arrays.stream(FeatureName.values())
                .map(feature -> LockSetting.builder()
                        .user(user)
                        .featureName(feature)
                        .isLocked(false)
                        .build())
                .toList();

        lockSettingRepository.saveAll(lockSettings);

        redisUtil.deleteEmailCode(email);
    }
}
