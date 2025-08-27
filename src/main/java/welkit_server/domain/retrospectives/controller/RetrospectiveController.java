package welkit_server.domain.retrospectives.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.retrospectives.dto.request.RetrospectiveRequest;
import welkit_server.domain.retrospectives.dto.response.GetAllRetrospectiveResponse;
import welkit_server.domain.retrospectives.dto.response.RetrospectiveResponse;
import welkit_server.domain.retrospectives.model.Type;
import welkit_server.domain.retrospectives.service.RetrospectiveService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/retrospectives")
public class RetrospectiveController {
    
    private final RetrospectiveService retrospectiveService;

    @Operation(summary = "회고 전체 조회", description = "등록되어 있는 회고를 전체 조회합니다")
    @GetMapping
    public ResponseEntity<SuccessResponse<GetAllRetrospectiveResponse>> getAllRetrospectives(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam Type type)  {
        List<GetAllRetrospectiveResponse> response = retrospectiveService.getAllRetrospectives(type, page, size);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, response));
    }

    @Operation(summary = "회고 상세 조회", description = "등록되어 있는 회고를 상세 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<RetrospectiveResponse>> getRetrospectiveById(@PathVariable("id") Long retrospectiveId) {
        RetrospectiveResponse retrospectiveResponse = retrospectiveService.getRetrospectiveById(retrospectiveId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, retrospectiveResponse));
    }
    
    @Operation(summary = "회고 등록", description = "새로운 회고를 등록합니다")
    @PostMapping
    public ResponseEntity<SuccessResponse<RetrospectiveResponse>> createRetrospective(@Valid @RequestBody RetrospectiveRequest createRetrospectiveRequest) {
        RetrospectiveResponse createRetrospectiveResponse = retrospectiveService.createRetrospective(createRetrospectiveRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, createRetrospectiveResponse));
    }
    
    @Operation(summary = "회고 수정", description = "등록되어 있는 회고를 수정합니다")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<RetrospectiveResponse>> editRetrospective(
            @PathVariable("id") Long retrospectiveId,
            @Valid @RequestBody RetrospectiveRequest editRetrospectiveRequest) {
        RetrospectiveResponse editRetrospective = retrospectiveService.editRetrospective(retrospectiveId, editRetrospectiveRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editRetrospective));

    }

    @Operation(summary = "회고 삭제", description = "등록되어 있는 회고를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteRetrospective(@PathVariable("id") Long retrospectiveId) {
        retrospectiveService.deleteRetrospective(retrospectiveId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
