package welkit_server.domain.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mail.dto.request.EmailPostRequest;
import welkit_server.domain.mail.dto.request.EmailVerifyRequest;
import welkit_server.domain.mail.dto.response.EmailResponse;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.SolveLockRequest;
import welkit_server.domain.mypage.dto.request.UpdateJobRoleRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.dto.response.MyPageResponse;
import welkit_server.domain.mypage.dto.response.UpdateJobRoleResponse;
import welkit_server.domain.mypage.service.MyPageService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService mypageService;

    @Operation(summary = "전체 마이페이지 조회", description = "사용자의 정보, 공지사항, 기능별 암호 설정 상태를 포함한 마이페이지 전체 데이터를 조회합니다 ")
    @GetMapping
    public ResponseEntity<SuccessResponse<MyPageResponse>> getAllMyPages(Authentication authentication) {
        MyPageResponse mypage = mypageService.getMyPage(authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS,mypage));
    }

    @Operation(summary = "암호 생성", description = "기능별 암호 설정을 위한 암호를 생성합니다")
    @PutMapping("/lock/pin")
    public ResponseEntity<SuccessResponse<?>> lockPin(
            @Valid @RequestBody LockSettingRequest lockSettingRequest,
            Authentication authentication
    ) {
        mypageService.setLock(lockSettingRequest,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS));
    }

    @Operation(summary = "기능별 암호 설정", description = "특정 기능을 잠그거나 잠금 해제합니다")
    @PatchMapping("/lock")
    public ResponseEntity<SuccessResponse<FeatureLockSettingResponse>> settingFeatureLock(
            @Valid @RequestBody FeatureLockSettingRequest featureLockSettingRequest,
            Authentication authentication
    ) {
        FeatureLockSettingResponse featureLockSettingResponse = mypageService.setFeatureLock(featureLockSettingRequest, authentication);
        String msg = SuccessMessage.LOCK_FEATURE_UPDATED.formatMessage(featureLockSettingRequest.getFeatureName());
        return ResponseEntity.ok(
                SuccessResponse.of(SuccessMessage.LOCK_FEATURE_UPDATED.getStatus(), msg, featureLockSettingResponse )
        );
    }

    @Operation(summary = "기능별 암호 검증", description = "사용자가 해당 기능을 이용하기위해 입력한 암호를 검증합니다")
    @PostMapping("/lock/verify")
    public ResponseEntity<SuccessResponse<Void>> verifyFeatureLock(
            @Valid @RequestBody SolveLockRequest solveLockRequest,
            Authentication authentication
    ) {
        mypageService.solveFeatureLock(solveLockRequest, authentication);

        String formattedMsg = SuccessMessage.LOCK_SOLVE_SUCCESS.formatMessage(solveLockRequest.getFeatureName());

        return ResponseEntity.ok(
                SuccessResponse.of(SuccessMessage.LOCK_SOLVE_SUCCESS.getStatus(), formattedMsg, null)
        );
    }

    @Operation( summary = "회사 이메일 인증번호 요청", description = "개인 이메일로 인증한 사용자가 회사 이메일로 재인증을 하기 위해 인증번호를 요청합니다")
    @PostMapping("/email/verify/company")
    public ResponseEntity<SuccessResponse<EmailResponse>> sendCompanyMail(
            @Valid @RequestBody EmailPostRequest emailPostRequest,
            Authentication authentication
    ) {
        EmailResponse response = mypageService. sendCompanyVerificationEmail(emailPostRequest, authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.COMPANY_EMAIL_SEND_SUCCESS, response));
    }

    @Operation(summary = "회사 이메일 인증번호 검증", description = "개인 이메일 인증 사용자가 회사 이메일로 재인증하기 위해 인증번호를 검증합니다")
    @PatchMapping("/email/verify/company")
    public ResponseEntity<SuccessResponse<Void>> verifyCompanyMail(
            @Valid @RequestBody EmailVerifyRequest emailVerifyRequest,
            Authentication authentication
    ) {
        mypageService.verifyEmail(emailVerifyRequest, authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.COMPANY_EMAIL_VERIFICATION_SUCCESS, null));
    }

    @Operation(summary = "사용자 직무 변경", description = "사용자가 마이페이지에서 자신의 직무(Job Role)를 변경합니다")
    @PatchMapping("/job")
    public ResponseEntity<SuccessResponse<UpdateJobRoleResponse>> updateJobRole(
            @Valid @RequestBody UpdateJobRoleRequest updateJobRoleRequest,
            Authentication authentication
    ) {
        UpdateJobRoleResponse response = mypageService.updateJobRole(updateJobRoleRequest, authentication);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, response));
    }

}
