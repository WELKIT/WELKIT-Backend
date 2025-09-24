package welkit_server.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.admin.dto.response.GetAllNoticeResponse;
import welkit_server.domain.admin.service.NoticeService;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.service.EmailService;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.SolveLockRequest;
import welkit_server.domain.mypage.dto.request.UpdateJobRoleRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.dto.response.MyPageResponse;
import welkit_server.domain.mypage.dto.response.UpdateJobRoleResponse;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.dto.response.UserInfoResponse;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.config.BlockedDomainsConfig;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.redis.RedisUtil;
import welkit_server.global.security.dto.CustomUserDetails;
import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final NoticeService noticeService;
    private final LockSettingRepository lockSettingRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisUtil redisUtil;
    private final EmailService emailService;
    private final BlockedDomainsConfig blockedDomainsConfig;

    public MyPageResponse getMyPage(Authentication authentication) {

        UserInfoResponse user = getUserInfo(authentication);
        List<GetAllNoticeResponse> notices = noticeService.getAllNotices(authentication);
        List<FeatureLockSettingResponse> lockSettings =
                lockSettingRepository.findByUserId(user.getId()).stream()
                        .map(FeatureLockSettingResponse::fromEntity)
                        .toList();

        return MyPageResponse.builder()
                .user(user)
                .noticeList(notices)
                .lockSettings(lockSettings)
                .build();
    }

    public UserInfoResponse getUserInfo(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        return UserInfoResponse.fromEntity(user);
    }

    @Transactional
    public void setLock(LockSettingRequest lockSettingRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        String lockPin = lockSettingRequest.getLockPin();

        if (lockPin == null || lockPin.isEmpty()) {
            throw new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST);
        }

        if (lockPin.length() != 4) {
            throw new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST);
        }
        user.settingLockPin(encoder.encode(lockPin));
    }

    @Transactional
    public FeatureLockSettingResponse setFeatureLock(FeatureLockSettingRequest featureLockSettingRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Long userId = user.getId();
        FeatureName feature = featureLockSettingRequest.getFeatureName();

        LockSetting lockSetting = lockSettingRepository.
                findByUserIdAndFeatureName(user.getId(), featureLockSettingRequest.getFeatureName());

        if (user.getLockPin() == null || user.getLockPin().isEmpty() || lockSetting == null) {
            throw new BadRequestException(ErrorMessage.MYP_LOCK_PIN_NOT_FOUND);
        }

        Boolean requestedLock = featureLockSettingRequest.getIsLocked();

        if (requestedLock != null){
            lockSetting.settingLock(requestedLock);
            if (!requestedLock) {
                String key = "feature-unlocked:" + userId + ":" + feature.name();
                redisTemplate.delete(key);
            }
        }
        return FeatureLockSettingResponse.fromEntity(lockSetting);
    }

    public void solveFeatureLock(SolveLockRequest solveLockRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Long userId = user.getId();
        FeatureName feature = solveLockRequest.getFeatureName();

        if (!encoder.matches(solveLockRequest.getLockPin(), user.getLockPin())) {
            throw new BadRequestException(ErrorMessage.MYP_INVALID_LOCK_PIN);
        }

        String key = "feature-unlocked:" + userId + ":" + feature.name();
        redisTemplate.opsForValue().set(key, "true", Duration.ofHours(3));
    }

    public void sendCompanyVerificationEmail(EmailPostRequest emailPostRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        if (user.getEmailType() == EmailType.COMPANY_EMAIL) {
            throw new BadRequestException(ErrorMessage.MYP_ALREADY_COMPANY_EMAIL_USER);
        }
        emailService.sendVerificationEmail(emailPostRequest.getEmail(), "회사");
    }

    @Transactional
    public void verifyEmail(EmailVerifyRequest emailVerifyRequest, Authentication authentication) {

        User user = getAuthenticatedUser(authentication);

        String email = emailVerifyRequest.getEmail();
        String code = emailVerifyRequest.getCode();

        emailService.verifyEmail(email, code, "회사" );

        if (!redisUtil.isVerifiedEmail(email)) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        if (isBlockedDomain(email)) {
            throw new BadRequestException(ErrorMessage.USR_EMAIL_COMPANY_DOMAIN_INVALID);
        }
        user.setEmailType(EmailType.COMPANY_EMAIL);
    }

    @Transactional
    public UpdateJobRoleResponse updateJobRole(UpdateJobRoleRequest updateJobRoleRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        JobRole jobRole = updateJobRoleRequest.getJobRole();
        user.updateJobRole(jobRole);

        return UpdateJobRoleResponse.builder()
                .jobRole(jobRole)
                .build();
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

    private boolean isBlockedDomain(String email) {
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        List<String> blockedDomains = blockedDomainsConfig.getEmailDomains();
        return blockedDomains.stream()
                .map(String::toLowerCase)
                .anyMatch(d -> d.equals(domain));
    }

}
