package welkit_server.domain.auth.refresh.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.auth.refresh.service.RefreshTokenService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<SuccessResponse> refreshAccessToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        String newAccessToken = refreshTokenService.refreshAccessToken(refreshToken, response);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.ACCESS_TOKEN_REISSUE_SUCCESS, newAccessToken));
    }
}