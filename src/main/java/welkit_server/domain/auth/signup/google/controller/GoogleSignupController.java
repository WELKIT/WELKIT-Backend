package welkit_server.domain.auth.signup.google.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    // 1. 구글 인증 콜백 → 세션에 이메일 저장
    @GetMapping("/callback")
    public ResponseEntity<Void> googleCallback(@RequestParam("code") String code, HttpSession session) {
        googleSignupService.saveGoogleEmailToSession(code, session);
        return ResponseEntity.status(302)
                .header("Location", "http://localhost:3000/users/signup/google")
                .build();
    }

    // 2. 구글 인증 후 회원가입
    @PostMapping
    public ResponseEntity<SuccessResponse> signupWithGoogle(
            @RequestBody @Valid GoogleSignupRequest request,
            HttpSession session) {

        googleSignupService.signupWithSessionEmail(session, request);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.GOOGLE_COMPANY_REGISTER_SUCCESS, null));
    }

}