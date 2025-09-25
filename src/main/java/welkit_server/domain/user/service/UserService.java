package welkit_server.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.security.jwt.JWTUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;
    private final JWTUtil jwtUtil;

    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());

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

}