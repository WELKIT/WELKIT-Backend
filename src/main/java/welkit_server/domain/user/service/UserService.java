package welkit_server.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import welkit_server.domain.user.dto.response.UserInfoResponse;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import welkit_server.global.security.jwt.JWTUtil;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final JWTUtil jwtUtil;

    public void deleteUser(User user) {
        userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
        userRepository.delete(user);

        String token = getTokenFromRequest();
        if (token != null) {
            long expiration = jwtUtil.getExpiration(token);
            redisTemplate.opsForValue().set(
                    "blacklist:" + token,
                    "deleted",
                    expiration,
                    TimeUnit.MILLISECONDS
            );
        }
    }

    public String getTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }

    public UserInfoResponse getUserInfoResponse(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserInfoResponse.fromEntity(user);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
