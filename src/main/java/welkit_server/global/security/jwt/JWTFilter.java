package welkit_server.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.global.dto.ErrorResponse;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.security.dto.CustomUserDetails;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    /**
     * permitAll 경로는 JWT 체크하지 않도록 필터 제외
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.startsWith("/users/signup") || path.equals("/users/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // 1. 헤더 없음 → 로그인 필요
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            setErrorResponse(response, ErrorMessage.LOGIN_REQUIRED); // AUTH1002
            return;
        }

        String token = authorization.substring(7); // "Bearer " 제거

        try {
            // 2. 토큰이 잘못된 경우
            if (token.isBlank()) {
                setErrorResponse(response, ErrorMessage.INVALID_TOKEN); // AUTH1004
                return;
            }

            // 3. 토큰 만료 확인
            if (jwtUtil.isExpired(token)) {
                setErrorResponse(response, ErrorMessage.SESSION_EXPIRED); // AUTH1001
                return;
            }

            // 4. 정상 토큰 → claim 가져오기
            Long userId = jwtUtil.getUserId(token);
            String email = jwtUtil.getEmail(token);
            String userType = jwtUtil.getUserType(token);
            String jobRole = jwtUtil.getJobRole(token);

            User user = User.builder()
                    .id(userId)
                    .email(email)
                    .userType(Enum.valueOf(UserType.class, userType))
                    .jobRole(Enum.valueOf(JobRole.class, jobRole))
                    .build();

            CustomUserDetails userDetails = new CustomUserDetails(user);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            // 2. JWT 파싱 오류, 잘못된 토큰 등
            setErrorResponse(response, ErrorMessage.INVALID_TOKEN); // AUTH1004
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorMessage errorMessage) throws IOException {
        response.setStatus(errorMessage.getStatus());
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponse errorResponse = ErrorResponse.of(errorMessage);
        response.getWriter().write(
                "{\"status\":" + errorResponse.status() +
                        ",\"code\":\"" + errorResponse.code() +
                        "\",\"message\":\"" + errorResponse.message() + "\"}"
        );
    }

}
