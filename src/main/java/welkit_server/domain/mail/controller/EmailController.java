package welkit_server.domain.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mail.service.EmailService;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/signup")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Operation(summary = "회사 이메일 인증 코드 전송", description = "5분간 유효한 이메일 인증 코드를 전송합니다. ")
    @PostMapping("/company/email")
    public ResponseEntity sendCompanyMail(@Valid @RequestBody EmailPostRequest emailPostRequest) {
        emailService.sendVerificationEmail(emailPostRequest.getEmail(), "회사");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.COMPANY_EMAIL_SEND_SUCCESS, null));
    }

    @Operation(summary = "회사 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/company/email")
    public ResponseEntity verifyCompanyEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getCode(), "회사");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.COMPANY_EMAIL_VERIFICATION_SUCCESS));
    }

    @Operation(summary = "개인 이메일 인증 코드 전송", description = "5분간 유효한 이메일 인증 코드를 전송합니다.")
    @PostMapping("/personal/email")
    public ResponseEntity sendPersonalMail(@Valid @RequestBody EmailPostRequest emailPostRequest) {
        emailService.sendVerificationEmail(emailPostRequest.getEmail(), "개인");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.PERSONAL_EMAIL_SEND_SUCCESS, null));
    }

    @Operation(summary = "개인 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/personal/email")
    public ResponseEntity verifyPersonalEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getCode(), "개인");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.PERSONAL_EMAIL_VERIFICATION_SUCCESS));
    }

}
