package welkit_server.domain.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.service.EmailService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/resend")
public class EmailResendController {

    private final EmailService emailService;

    @Operation(summary = "이메일 인증 코드 재전송", description = "5분간 유효한 이메일 인증 코드를 재전송합니다")
    @PostMapping("/email")
    public ResponseEntity resendEmail(@Valid @RequestBody EmailPostRequest emailPostRequest) {
        emailService.resendEmail(emailPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(SuccessMessage.EMAIL_SEND_SUCCESS));
    }

    @Operation(summary = "이메일 인증 재전송 코드 검증", description = "재전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/email")
    public ResponseEntity resendVerifyEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyResendVerificationEmail(emailVerifyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(SuccessMessage.EMAIL_VERIFICATION_SUCCESS));
    }

}
