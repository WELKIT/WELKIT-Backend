package welkit_server.domain.auth.refresh.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.jwt.JWTUtil;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    private static final String REFRESH_TOKEN_PREFIX = "refresh:";

    // 리프레시 토큰 저장
    public void saveRefreshToken(Long userId, String refreshToken, long expirationMs) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.opsForValue().set(key, refreshToken, expirationMs, TimeUnit.MILLISECONDS);
    }

    // 리프레시 토큰 검증
    public boolean validateRefreshToken(Long userId, String refreshToken) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        String storedToken = redisTemplate.opsForValue().get(key);
        return refreshToken.equals(storedToken);
    }

    // 리프레시 토큰 삭제
    public void deleteRefreshToken(Long userId) {
        String key = REFRESH_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }

    public String refreshAccessToken(String refreshToken, HttpServletResponse response) {
        // 리프레시 토큰 검증
        if (refreshToken == null || refreshToken.isBlank() || !jwtUtil.validateToken(refreshToken)) {
            throw new UnauthorizedException(ErrorMessage.INVALID_TOKEN);
        }

        // 토큰에서 사용자 정보 추출
        Long userId = jwtUtil.getUserId(refreshToken);

        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        // 새로운 액세스 토큰 및 리프레시 토큰 발급
        String newAccessToken = jwtUtil.createJwt(
                user.getLoginEmail(),
                user.getId(),
                user.getUserType().name(),
                user.getJobRole().name(),
                60 * 60 * 1000L
        );
        String newRefreshToken = jwtUtil.createJwt(
                user.getLoginEmail(),
                user.getId(),
                user.getUserType().name(),
                user.getJobRole().name(),
                7 * 24 * 60 * 60 * 1000L
        );

        // 리프레시 토큰 저장 (서버 측)
        saveRefreshToken(userId, newRefreshToken, 7 * 24 * 60 * 60 * 1000L);

        // 리프레시 토큰을 쿠키에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);

        return newAccessToken;
    }
}