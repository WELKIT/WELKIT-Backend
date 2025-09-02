package welkit_server.domain.mypage.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.mypage.dto.request.FeatureLockSettingRequest;
import welkit_server.domain.mypage.dto.request.LockSettingRequest;
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
        return ResponseEntity.ok(SuccessResponse.of(featureLockSettingResponse, msg, SuccessMessage.LOCK_FEATURE_UPDATED.getStatus()));
    }
}
