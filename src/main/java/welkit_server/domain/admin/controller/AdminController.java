package welkit_server.domain.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.admin.dto.request.NoticeAdminRequest;
import welkit_server.domain.admin.dto.response.*;
import welkit_server.domain.admin.service.CommunityManagementService;
import welkit_server.domain.admin.service.NoticeService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final NoticeService noticeService;
    private final CommunityManagementService communityManagementService;

    @Operation(summary = "공지사항 전체 조회", description = "등록되어 있는 공지사항을 전체 조회합니다")
    @GetMapping("/notices")
    public ResponseEntity<SuccessResponse<NoticeResponse>> getAllNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            Authentication authentication) {
        NoticeResponse getAllNotices = noticeService.getAllNotices(page,size,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, getAllNotices));
    }

    @Operation(summary = "공지사항 상세 조회", description = "등록되어 있는 공지사항을 상세 조회합니다")
    @GetMapping("/notices/{id}")
    public ResponseEntity<SuccessResponse<NoticeAdminResponse>> getNotice(@PathVariable("id") Long noticeId, Authentication authentication) {
        NoticeAdminResponse notice= noticeService.getNotice(noticeId,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS,notice));
    }

    @Operation(summary = "공지사항 등록", description = "새로운 공지사항을 등록합니다")
    @PostMapping("/notices")
    public ResponseEntity<SuccessResponse<NoticeAdminResponse>> createNotices(
            @Valid @RequestBody NoticeAdminRequest createNoticeRequest,
            Authentication authentication
    ) {
        NoticeAdminResponse createNoticeResponse = noticeService.createNotice(createNoticeRequest, authentication);
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
        NoticeAdminResponse editNoticeResponse = noticeService.updateNotice(noticeId, editNoticeRequest, authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editNoticeResponse));
    }

    @Operation(summary = "공지사항 삭제", description = "등록되어 있는 공지사항을 삭제합니다")
    @DeleteMapping("/notices/{id}")
    public ResponseEntity<SuccessResponse> deleteNotice(@PathVariable("id") Long noticeId, Authentication authentication) {
        noticeService.deleteNotice(noticeId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

    @Operation(summary = "제재 커뮤니티 글 전체 조회", description = "제재할 커뮤니티 글을 전체 조회합니다")
    @GetMapping("/sanction-posts")
    public ResponseEntity<SuccessResponse<CommunityManagementPostResponse>> getAllSanctionPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ){
        CommunityManagementPostResponse communityManagementPostResponse = communityManagementService.getAllSanctionPosts(page,size,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, communityManagementPostResponse));
    }


    @Operation(summary = "제재 커뮤니티 글 상세 조회", description = "제재할 커뮤니티 글을 상세 조회합니다")
    @GetMapping("/sanction-posts/{id}")
    public ResponseEntity<SuccessResponse<CommunityManagementPostDetailResponse>> getSectionPostById(@PathVariable("id") Long postId, Authentication authentication) {
        CommunityManagementPostDetailResponse communityPosts = communityManagementService.getSanctionPostById(postId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS,communityPosts));
    }

    @Operation(summary = "제재 커뮤니티 글 삭제",description = "제재할 커뮤니티 글을 삭제합니다")
    @DeleteMapping("/sanction-posts/{id}")
    public ResponseEntity<SuccessResponse> deleteSectionPostById(@PathVariable("id") Long postId,Authentication authentication) {
        communityManagementService.deleteSanctionPostById(postId,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
