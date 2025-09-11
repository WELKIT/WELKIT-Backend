package welkit_server.domain.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.community.dto.request.*;
import welkit_server.domain.community.dto.response.*;
import welkit_server.domain.community.service.CommunityService;
import welkit_server.domain.user.model.JobRole;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 글 전체 조회", description = "커뮤니티 글을 전체 조회합니다. 카테고리(직무)별로 필터링할 수 있습니다.")
    @GetMapping("/posts")
    public ResponseEntity<SuccessResponse<Map<String, List<PostSummaryResponse>>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) JobRole category
    ) {
        List<PostSummaryResponse> posts = communityService.getAllCommunityPosts(category, page, size);

        Map<String, List<PostSummaryResponse>> response = Map.of("posts", posts);

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "커뮤니티 글 상세 조회", description = "커뮤니티 글의 상세 내용을 조회합니다. 글과 함께 해당 글의 댓글도 함께 조회합니다.")
    @GetMapping("posts/{postId}")
    public ResponseEntity<SuccessResponse<PostDetailResponse>> getPostDetail(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        PostDetailResponse response = communityService.getPostDetail(postId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "내가 작성한 글 조회", description = "내가 작성한 커뮤니티 글을 조회합니다.")
    @GetMapping("/myposts")
    public ResponseEntity<SuccessResponse<Page<PostSummaryResponse>>> getMyPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) JobRole category,
            Authentication authentication
    ) {
        List<PostSummaryResponse> response = communityService.getMyPosts(page, size, category, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "내가 댓글 단 글 조회", description = "내가 댓글을 단 커뮤니티 글을 조회합니다.")
    @GetMapping("/mycomments")
    public ResponseEntity<SuccessResponse<Page<PostSummaryResponse>>> getMyCommentedPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) JobRole category,
            Authentication authentication
    ) {
        List<PostSummaryResponse> response = communityService.getMyCommentedPosts(page, size, category, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "게시글 검색", description = "키워드로 게시글을 검색합니다.")
    @GetMapping("/posts/search")
    public ResponseEntity<SuccessResponse<Page<PostSummaryResponse>>> searchPosts(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String keyword
    ) {
        List<PostSummaryResponse> response = communityService.searchPosts(page, size, keyword);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "커뮤니티 글 작성", description = "새로운 커뮤니티 글을 작성합니다.")
    @PostMapping("/posts")
    public ResponseEntity<SuccessResponse<PostResponse>> createPost(
            @RequestBody PostCreateRequest request,
            Authentication authentication
    ) {
        PostResponse response = communityService.createPost(request, authentication);

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, response));
    }

    @Operation(summary = "커뮤니티 글 수정", description = "기존 커뮤니티 글을 수정합니다.")
    @PatchMapping("posts/{postId}")
    public ResponseEntity<SuccessResponse<PostUpdateResponse>> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequest request,
            Authentication authentication) {

        PostUpdateResponse response = communityService.updatePost(postId, request, authentication);

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, response));
    }

    @Operation(summary = "커뮤니티 글 삭제", description = "기존 커뮤니티 글을 삭제합니다.")
    @DeleteMapping("posts/{postId}")
    public ResponseEntity<SuccessResponse<Void>> deletePost(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        communityService.deletePost(postId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS, null));
    }

    @Operation(summary = "댓글 등록", description = "게시글에 댓글을 등록합니다. parentId가 있으면 대댓글로 등록됩니다.")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<SuccessResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequest request,
            Authentication authentication
    ) {
        CommentResponse response = communityService.createComment(request, postId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, response));
    }

    @Operation(summary = "댓글 수정", description = "본인의 댓글을 수정합니다. 대댓글도 동일하게 수정할 수 있습니다.")
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SuccessResponse<CommentUpdateResponse>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequest request,
            Authentication authentication
    ) {
        CommentUpdateResponse response = communityService.updateComment(commentId, request, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, response));
    }

    @Operation(summary = "커뮤니티 글/댓글 공감 버튼 토글", description = "유익했어요/유익하지 않았어요 버튼을 토글합니다.")
    @PostMapping("/feedback")
    public ResponseEntity<SuccessResponse<FeedbackResponse>> toggleHelpful(
            @Valid @RequestBody FeedbackRequest request,
            Authentication authentication
    ) {
        FeedbackResponse response = communityService.toggleHelpful(request, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, response));
    }

}
