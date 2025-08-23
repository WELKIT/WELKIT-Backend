package welkit_server.domain.user.mail.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.user.mail.dto.request.EmailPostRequest;
import welkit_server.domain.user.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.user.mail.dto.response.EmailMessageResponse;
import welkit_server.domain.user.mail.dto.response.EmailResponse;
import welkit_server.domain.user.mail.service.EmailService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.redis.RedisUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/signup")
@Slf4j
public class EmailController {

    private final EmailService emailService;
    private final RedisUtil redisUtil;

    @Operation(summary = "회사 이메일 인증 코드 전송", description = "5분간 유효한 이메일 인증 코드를 전송합니다. ")
    @PostMapping("/company/email")
    public ResponseEntity<EmailResponse> sendCompanyMail(@RequestBody EmailPostRequest emailPostRequest) {
        return ResponseEntity.ok(emailService.sendVerificationEmail(emailPostRequest.getEmail(), "회사"));
    }

    @Operation(summary = "회사 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/company/email")
    public ResponseEntity<String> verifyCompanyEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getCode(), "회사");
        return ResponseEntity.ok("회사 이메일 인증이 완료되었습니다.");
    }

    @Operation(summary = "개인 이메일 인증 코드 전송", description = "5분간 유효한 이메일 인증 코드를 전송합니다. ")
    @PostMapping("/personal/email")
    public ResponseEntity<EmailResponse> sendPersonalMail(@RequestBody EmailPostRequest emailPostRequest) {
        return ResponseEntity.ok(emailService.sendVerificationEmail(emailPostRequest.getEmail(), "개인"));
    }

    @Operation(summary = "개인 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/personal/email")
    public ResponseEntity<String> verifyPersonalEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        emailService.verifyEmail(emailVerifyRequest.getEmail(), emailVerifyRequest.getCode(), "개인");
        return ResponseEntity.ok("개인 이메일 인증이 완료되었습니다.");
    }

}
