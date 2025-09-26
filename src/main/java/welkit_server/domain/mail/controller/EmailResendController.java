package welkit_server.domain.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.model.EmailCodePurpose;
import welkit_server.domain.mail.service.EmailService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/email")
public class EmailResendController {
    private final EmailService emailService;

    @Operation(summary = "인증 코드 재발송", description = "5분간 유효한 이메일 인증 코드를 재발송합니다")
    @PostMapping("/resend")
    public ResponseEntity<SuccessResponse<?>> resendEmail(
            @RequestParam EmailCodePurpose purpose,
            @Valid @RequestBody EmailPostRequest emailPostRequest
    ) {
        emailService.resendEmail(emailPostRequest, purpose);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.EMAIL_SEND_SUCCESS));
    }

    @Operation(summary = "재발송된 인증 코드 검증", description = "재전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/verify")
    public ResponseEntity<SuccessResponse<?>> verifyEmail(
            @RequestParam EmailCodePurpose purpose,
            @Valid @RequestBody EmailVerifyRequest emailVerifyRequest
    ) {
        String email = emailVerifyRequest.getEmail();
        String inputCode = emailVerifyRequest.getCode();
        emailService.verifyEmail(email, inputCode, purpose);

        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.EMAIL_VERIFICATION_SUCCESS));
    }
}
