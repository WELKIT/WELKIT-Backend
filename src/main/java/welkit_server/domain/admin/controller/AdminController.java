package welkit_server.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.admin.dto.request.NoticeAdminRequest;
import welkit_server.domain.admin.dto.response.NoticeAdminResponse;
import welkit_server.domain.admin.service.NoticeAdminService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final NoticeAdminService noticeAdminService;

    @Operation(summary = "공지사항 상세 조회", description = "등록되어 있는 공지사항을 상세 조회합니다")
    @GetMapping("/notices/{id}")
    public ResponseEntity<SuccessResponse<NoticeAdminResponse>> getNotice(@PathVariable Long noticeId) {
        NoticeAdminResponse notice= noticeAdminService.getNotice(noticeId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS,notice));
    }

    @Operation(summary = "공지사항 등록", description = "새로운 공지사항을 등록합니다")
    @PostMapping("/notices")
    public ResponseEntity<SuccessResponse<NoticeAdminResponse>> createNotices(
            @Valid @RequestBody NoticeAdminRequest createNoticeRequest,
            Authentication authentication
    ) {
        NoticeAdminResponse createNoticeResponse = noticeAdminService.createNotice(createNoticeRequest, authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, createNoticeResponse));
    }

    @Operation(summary = "공지사항 수정", description = "등록되어 있는 공지사항을 수정합니다")
    @PatchMapping("/notices/{id}")
    public ResponseEntity<SuccessResponse<NoticeAdminResponse>> updateNotice(
            @Valid @RequestBody NoticeAdminRequest editNoticeRequest,
            @PathVariable("id") Long noticeId,
            Authentication authentication
    ){
        NoticeAdminResponse editNoticeResponse = noticeAdminService.updateNotice(noticeId, editNoticeRequest, authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editNoticeResponse));
    }

    @Operation(summary = "공지사항 삭제", description = "등록되어 있는 공지사항을 삭제합니다")
    @DeleteMapping("/notices/{id}")
    public ResponseEntity<SuccessResponse> deleteNotice(@PathVariable("id") Long noticeId, Authentication authentication) {
        noticeAdminService.deleteNotice(noticeId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
