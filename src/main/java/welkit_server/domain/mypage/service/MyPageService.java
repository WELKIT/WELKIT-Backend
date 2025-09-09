package welkit_server.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.SolveLockRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.ForbiddenException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final LockSettingRepository lockSettingRepository;
    private final RedisTemplate<String, String> redisTemplate;

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
            throw new ForbiddenException(ErrorMessage.MYP_INVALID_LOCK_PIN);
        }

        String key = "feature-unlocked:" + userId + ":" + feature.name();
        redisTemplate.opsForValue().set(key, "true", Duration.ofHours(3));

    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
