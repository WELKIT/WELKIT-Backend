package welkit_server.domain.auth.login.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import welkit_server.domain.auth.login.dto.LoginRequest;
import welkit_server.domain.auth.login.service.LoginService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        String token = loginService.login(request, response);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOGIN_SUCCESS, token));
    }
}