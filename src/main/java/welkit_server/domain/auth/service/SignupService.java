package welkit_server.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import welkit_server.domain.auth.dto.SignupRequest;
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
public class SignupService {

    private final UserRepository userRepository;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;
    private final BlockedDomainsConfig blockedDomainsConfig;

    @Value("${blocked.email-domains}")
    private List<String> blockedDomains;

    public void signupPersonal(SignupRequest request) {
        signupCommon(request, false);
    }

    public void signupCompany(SignupRequest request) {
        signupCommon(request, true);
    }

    private void signupCommon(SignupRequest request, boolean isCompany) {
        String email = request.getEmail();

        if (!redisUtil.isVerifiedEmail(email)) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        // 회사 메일 인증 후 회원가입 시 범용 이메일 도메인 유무 체크
        if (isCompany && isBlockedDomain(email)) {
            throw new BadRequestException(ErrorMessage.USR_EMAIL_COMPANY_DOMAIN_INVALID);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        EmailType emailType = isCompany ? EmailType.COMPANY_EMAIL : EmailType.PERSONAL_EMAIL;

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .jobRole(request.getJobRole())
                .isCompanyVerified(isCompany)
                .emailType(emailType)
                .build();

        userRepository.save(user);

        redisUtil.deleteEmailCode(email);
    }

    private boolean isBlockedDomain(String email) {
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        List<String> blockedDomains = blockedDomainsConfig.getEmailDomains();
        System.out.println("Blocked domains: " + blockedDomains);
        System.out.println("Checking email domain: " + domain);
        return blockedDomains.stream()
                .map(String::toLowerCase)
                .anyMatch(d -> d.equals(domain));
    }

}