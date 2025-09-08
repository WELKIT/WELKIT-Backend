package welkit_server.domain.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.community.dto.request.PostCreateRequest;
import welkit_server.domain.community.dto.response.PostResponse;
import welkit_server.domain.community.service.CommunityService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequestMapping("/community/posts")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @Operation(summary = "커뮤니티 글 작성", description = "새로운 커뮤니티 글을 작성합니다.")
    @PostMapping
    public ResponseEntity<SuccessResponse<PostResponse>> createPost(
            @RequestBody PostCreateRequest request,
            Authentication authentication
    ) {
        PostResponse response = communityService.createPost(request, authentication);

        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, response));
    }

}
