package welkit_server.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.auth.dto.SignupRequest;
import welkit_server.domain.auth.service.SignupService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/signup")
public class AuthController {

    private final SignupService signupService;

    @PostMapping("/personal")
    public ResponseEntity<SuccessResponse> signupPersonal(@Valid @RequestBody SignupRequest request) {
        signupService.signupPersonal(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.PERSONAL_EMAIL_REGISTER_SUCCESS, null));
    }

    @PostMapping("/company")
    public ResponseEntity<SuccessResponse> signupCompany(@Valid @RequestBody SignupRequest request) {
        signupService.signupCompany(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.COMPANY_EMAIL_REGISTER_SUCCESS, null));
    }

}