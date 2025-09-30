package welkit_server.domain.auth.login.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import welkit_server.domain.auth.login.dto.LoginRequest;
import welkit_server.domain.auth.refresh.service.RefreshTokenService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.security.jwt.JWTUtil;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public String login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .or(() -> userRepository.findByGoogleEmail(request.getEmail()))
                .orElseThrow(() -> new BadRequestException(ErrorMessage.INVALID_CREDENTIAL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIAL);
        }

        // 액세스 토큰 발급
        String accessToken = jwtUtil.createJwt(
                user.getLoginEmail(),
                user.getId(),
                user.getUserType().name(),
                user.getJobRole().name(),
                60 * 60 * 1000L
        );

        // 리프레시 토큰 발급 및 저장
        String refreshToken = jwtUtil.createJwt(
                user.getLoginEmail(),
                user.getId(),
                user.getUserType().name(),
                user.getJobRole().name(),
                7 * 24 * 60 * 60 * 1000L
        );
        refreshTokenService.saveRefreshToken(user.getId(), refreshToken, 7 * 24 * 60 * 60 * 1000L);

        // 리프레시 토큰을 쿠키에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true); // JavaScript에서 접근 불가
        refreshTokenCookie.setSecure(true); // HTTPS에서만 전송 (개발 환경에서는 false)
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 만료 시간 설정 (7일)
        response.addCookie(refreshTokenCookie);

        return accessToken;
    }
}