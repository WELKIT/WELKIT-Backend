package welkit_server.domain.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
import welkit_server.domain.mypage.dto.request.SolveLockRequest;
import welkit_server.domain.mypage.dto.response.FeatureLockSettingResponse;
import welkit_server.domain.mypage.service.MyPageService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService mypageService;


    @Operation(summary = "암호 생성", description = "기능별 암호 설정을 위한 암호를 생성합니다")
    @PutMapping("/lock/pin")
    public ResponseEntity<SuccessResponse<?>> lockPin(
            @Valid @RequestBody LockSettingRequest lockSettingRequest,
            Authentication authentication
    ) {
        mypageService.setLock(lockSettingRequest,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS));
    }

    @Operation(summary = "기능별 암호 설정", description = "기능별 암호 설정을 합니다")
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

}
