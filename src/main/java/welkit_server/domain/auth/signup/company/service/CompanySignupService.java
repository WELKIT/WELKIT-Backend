package welkit_server.domain.auth.signup.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import welkit_server.domain.auth.signup.company.dto.SignupRequest;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.config.BlockedDomainsConfig;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.redis.RedisUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanySignupService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final BlockedDomainsConfig blockedDomainsConfig;

    public void signup(SignupRequest request) {
        String email = request.getEmail();

        if (!redisUtil.isVerifiedEmail(email)) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        if (isBlockedDomain(email)) {
            throw new BadRequestException(ErrorMessage.USR_EMAIL_COMPANY_DOMAIN_INVALID);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .jobRole(request.getJobRole())
                .isCompanyVerified(true)
                .emailType(EmailType.COMPANY_EMAIL)
                .build();

        userRepository.save(user);
        redisUtil.deleteEmailCode(email);
    }

    private boolean isBlockedDomain(String email) {
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        List<String> blockedDomains = blockedDomainsConfig.getEmailDomains();
        return blockedDomains.stream()
                .map(String::toLowerCase)
                .anyMatch(d -> d.equals(domain));
    }

}