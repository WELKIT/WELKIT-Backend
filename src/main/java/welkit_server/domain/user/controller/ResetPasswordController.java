package welkit_server.domain.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.user.dto.request.ResetPasswordRequest;
import welkit_server.domain.user.service.ResetPasswordService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/password")
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @Operation(summary = "비밀번호 재설정을 위한 인증 이메일 요청",description = "비밀번호 재설정을 하기위해 이메일 인증 과정을 거칩니다")
    @PostMapping("/email")
    public ResponseEntity sendEmail(@Valid @RequestBody EmailPostRequest emailPostRequest) {
        resetPasswordService.sendEmail(emailPostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(SuccessMessage.EMAIL_SEND_SUCCESS));
    }

    @Operation(summary = "비밀번호 재설정을 위한 인증 이메일 코드 검증",description = "비밀번호 재설정을 하기위해 이메일 검증 과정을 거칩니다")
    @PutMapping("/verify")
    public ResponseEntity verifyEmail(@Valid @RequestBody EmailVerifyRequest emailVerifyRequest) {
        resetPasswordService.verifyEmail(emailVerifyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(SuccessMessage.EMAIL_VERIFICATION_SUCCESS));
    }

    @Operation(summary = "비밀번호 재설정", description = "로그인 페이지에서 비밀번호 재설정을 합니다")
    @PostMapping
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        resetPasswordService.resetPassword(resetPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(SuccessMessage.RESET_PASSWORD_SUCCESS));
    }

}
