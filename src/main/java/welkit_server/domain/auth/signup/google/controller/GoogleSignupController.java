package welkit_server.domain.auth.signup.google.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.auth.signup.google.dto.GoogleSignupRequest;
import welkit_server.domain.auth.signup.google.service.GoogleSignupService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequestMapping("/users/signup/google")
@RequiredArgsConstructor
public class GoogleSignupController {

    private final GoogleSignupService googleSignupService;

    @GetMapping("/callback")
    public ResponseEntity<Void> googleCallback(@RequestParam("code") String code) {
        String redirectUrl = googleSignupService.handleGoogleCallback(code);

        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, redirectUrl)
                .build();
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> signupWithGoogle(
            @RequestParam("code") String code,
            @RequestBody @Valid GoogleSignupRequest request) {

        String token = googleSignupService.signupWithGoogle(code, request);

        return ResponseEntity.ok(
                SuccessResponse.of(SuccessMessage.GOOGLE_COMPANY_REGISTER_SUCCESS, token)
        );
    }

}