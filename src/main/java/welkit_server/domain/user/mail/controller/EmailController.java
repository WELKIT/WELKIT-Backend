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
    public ResponseEntity sendCompanyMail(@RequestBody EmailPostRequest emailPostRequest) {
        EmailMessageResponse emailMessageResponse = EmailMessageResponse.builder()
                .to(emailPostRequest.getEmail())
                .subject("[welkit] 회사 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessageResponse, "email");
        redisUtil.saveEmailCode(emailPostRequest.getEmail(), code);

        EmailResponse emailResponse = EmailResponse.builder()
                .code(code)
                .message("인증코드 전송 완료")
                .build();
        return ResponseEntity.ok(emailResponse);
    }

    @Operation(summary = "개인 이메일 인증 코드 전송", description = "5분간 유효한 이메일 인증 코드를 전송합니다. ")
    @PostMapping("/personal/email")
    public ResponseEntity sendPersonalMail(@RequestBody EmailPostRequest emailPostRequest) {
        EmailMessageResponse emailMessageResponse = EmailMessageResponse.builder()
                .to(emailPostRequest.getEmail())
                .subject("[welkit] 개인 이메일 인증을 위한 인증 코드 발송")
                .build();

        String code = emailService.sendMail(emailMessageResponse, "email");
        redisUtil.saveEmailCode(emailPostRequest.getEmail(), code);

        EmailResponse emailResponse = EmailResponse.builder()
                .code(code)
                .message("인증코드 전송 완료")
                .build();
        return ResponseEntity.ok(emailResponse);
    }

    @Operation(summary = "회사 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/company/email")
    public ResponseEntity<?> verifyCompanyEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        String emailCode = redisUtil.getEmailCode(emailVerifyRequest.getEmail());
        
        if(emailCode == null) {
            log.warn("회사 이메일 인증 코드 없음 또는 만료됨 - email: {}", emailVerifyRequest.getEmail());
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR); //EMAIL CODE EXPIRED
        }
        if (!emailCode.equals(emailVerifyRequest.getCode().trim())) {
            log.warn("회사 이메일 인증 코드 불일치 - email: {}, 입력된 코드: {}", emailVerifyRequest.getEmail(), emailVerifyRequest.getCode());
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR); //ERROR CODE NOT MATCH
        }
        try {
            redisUtil.saveVerifiedEmail(emailVerifyRequest.getEmail());
            redisUtil.deleteEmailCode(emailVerifyRequest.getEmail());
        } catch (Exception e) {
            log.error("Redis 처리 중 오류 - email: {}", emailVerifyRequest.getEmail(), e);
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }

        log.info("회사 이메일 인증 성공 - email: {}", emailVerifyRequest.getEmail());
        return ResponseEntity.ok("개인 이메일 인증이 완료되었습니다.");
    }

    @Operation(summary = "개인 이메일 인증 코드 검증", description = "전송된 인증 코드와 입력값을 비교하여 인증합니다")
    @PutMapping("/personal/email")
    public ResponseEntity<?> verifyPersonalEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        String emailCode = redisUtil.getEmailCode(emailVerifyRequest.getEmail());

        if(emailCode == null) {
            log.warn("개인 이메일 인증 코드 없음 또는 만료됨 - email: {}", emailVerifyRequest.getEmail());
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR); //EMAIL CODE EXPIRED
        }
        if (!emailCode.equals(emailVerifyRequest.getCode().trim())) {
            log.warn("개인 이메일 인증 코드 불일치 - email: {}, 입력된 코드: {}", emailVerifyRequest.getEmail(), emailVerifyRequest.getCode());
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR); //ERROR CODE NOT MATCH
        }
        try {
            redisUtil.saveVerifiedEmail(emailVerifyRequest.getEmail());
            redisUtil.deleteEmailCode(emailVerifyRequest.getEmail());
        } catch (Exception e) {
            log.error("Redis 처리 중 오류 - email: {}", emailVerifyRequest.getEmail(), e);
            throw new BadRequestException(ErrorMessage.INTERNAL_SERVER_ERROR);
        }

        log.info("개인 이메일 인증 성공 - email: {}", emailVerifyRequest.getEmail());
        return ResponseEntity.ok("개인 이메일 인증이 완료되었습니다.");
    }
}
