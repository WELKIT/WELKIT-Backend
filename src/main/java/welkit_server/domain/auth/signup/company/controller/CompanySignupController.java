package welkit_server.domain.auth.signup.company.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.auth.signup.company.dto.SignupRequest;
import welkit_server.domain.auth.signup.company.service.CompanySignupService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/signup/company")
public class CompanySignupController {

    private final CompanySignupService CompanySignupService;

    @PostMapping
    public ResponseEntity<SuccessResponse> signup(@Valid @RequestBody SignupRequest request) {
        CompanySignupService.signup(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.PERSONAL_EMAIL_REGISTER_SUCCESS, null));
    }
}