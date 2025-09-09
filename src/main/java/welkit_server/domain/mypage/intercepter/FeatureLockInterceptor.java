package welkit_server.domain.mypage.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;

@Component
@RequiredArgsConstructor
public class FeatureLockInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String,String> redisTemplate;
    private final LockSettingRepository lockSettingRepository;

    private static final String FEATURE_UNLOCK_KEY = "feature-unlocked:%d:%s";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException(ErrorMessage.SESSION_EXPIRED);
        }

        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();
        FeatureName feature = FeatureName.fromUrl(request.getRequestURI());

        if (feature == null) {
            return true;
        }

        LockSetting lockSetting = lockSettingRepository.findByUserIdAndFeatureName(userId, feature);
        if (lockSetting == null || !lockSetting.isLocked()) {
            return true;
        }

        String key = String.format(FEATURE_UNLOCK_KEY, userId, feature.name());
        String unlocked = redisTemplate.opsForValue().get(key);

        if ("true".equals(unlocked)) {
            return true;
        }

        throw new BadRequestException(ErrorMessage.MYP_INVALID_LOCK_PIN);
    }
}
