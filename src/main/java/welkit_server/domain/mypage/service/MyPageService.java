package welkit_server.domain.mypage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final LockSettingRepository lockSettingRepository;

    //암호 설정
    @Transactional
    public void setLock(LockSettingRequest lockSettingRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        String lockPin = lockSettingRequest.getLockPin();

        if (lockPin == null || lockPin.isEmpty()) {
            throw new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST);
        }

        user.settingLockPin(encoder.encode(lockPin));
    }
    //기능별 암호 설정
    @Transactional
    public FeatureLockSettingResponse setFeatureLock(FeatureLockSettingRequest featureLockSettingRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        Long currentUserId = getAuthenticatedUserId(authentication);

        LockSetting lockSetting = lockSettingRepository.
                findIsLockedByUserIdAndFeatureName(currentUserId, featureLockSettingRequest.getFeatureName());

        if (user.getLockPin() == null || user.getLockPin().isEmpty()) {
            throw new BadRequestException(ErrorMessage.MYP_LOCK_PIN_NOT_FOUND);
        }

        Boolean requestedLock = featureLockSettingRequest.getIsLocked();
        if (!lockSetting.isLocked() && requestedLock != null && !requestedLock.equals(lockSetting.isLocked())) {
            lockSetting.settingLock(requestedLock);
        }

        return FeatureLockSettingResponse.fromEntity(lockSetting);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
