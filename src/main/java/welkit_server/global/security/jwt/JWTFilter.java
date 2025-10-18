package welkit_server.global.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final StringRedisTemplate redisTemplate;

    /**
     * permitAll 경로는 JWT 체크하지 않도록 필터 제외
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        String method = request.getMethod();

        // GET 요청 중 공개 API
        if (method.equalsIgnoreCase("GET")) {
            if (path.equals("/community/posts")
                    || path.equals("/community/posts/search")
                    || path.matches("^/community/posts/\\d+$")) { // 상세조회도 허용
                return true;
            }
        }

        // 기본 공개 경로
        return path.startsWith("/users/signup") ||
                path.startsWith("/auth") ||
                path.equals("/users/login") ||
                path.equals("/users/logout") ||
                path.startsWith("/privacy") ||
                path.startsWith("/agreement") ||
                path.equals("/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // Authorization 헤더가 없으면 비회원으로 간주하고 통과
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7); // "Bearer " 제거

        try {
            // 토큰이 비어 있는 경우
            if (token.isBlank()) {
                setErrorResponse(response, ErrorMessage.INVALID_TOKEN);
                return;
            }

            // 블랙리스트(로그아웃된 토큰) 확인
            if (Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token))) {
                setErrorResponse(response, ErrorMessage.LOGIN_REQUIRED);
                return;
            }

            // 토큰 만료 확인
            if (jwtUtil.isExpired(token)) {
                setErrorResponse(response, ErrorMessage.SESSION_EXPIRED);
                return;
            }

            // 토큰이 유효한 경우 사용자 정보 추출
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

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception e) {
            // JWT 파싱 에러 또는 잘못된 토큰
            setErrorResponse(response, ErrorMessage.INVALID_TOKEN);
            return;
        }

        // 인증 성공 또는 비회원이라도 계속 필터 체인 통과
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