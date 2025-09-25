package welkit_server.domain.auth.logout.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import welkit_server.domain.auth.logout.dto.LogoutRequest;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.security.jwt.JWTUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final JWTUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;

    public void logout(LogoutRequest request) {
        String token = request.getToken();

        if (!jwtUtil.validateToken(token)) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIAL);
        }

        // 토큰 만료시간(ms)
        long expiration = jwtUtil.getExpiration(token);

        // Redis 블랙리스트에 저장
        redisTemplate.opsForValue().set(
                "blacklist:" + token,
                "logout",
                expiration,
                TimeUnit.MILLISECONDS
        );
    }
}