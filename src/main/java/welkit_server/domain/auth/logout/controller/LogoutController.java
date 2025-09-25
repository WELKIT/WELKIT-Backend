package welkit_server.domain.auth.logout.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import welkit_server.domain.auth.logout.dto.LogoutRequest;
import welkit_server.domain.auth.logout.service.LogoutService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/logout")
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping
    public ResponseEntity<SuccessResponse> logout(@Valid @RequestBody LogoutRequest request) {
        logoutService.logout(request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOGOUT_SUCCESS));
    }
}