package welkit_server.domain.terms.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.EditTermResponse;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.dto.response.GetAllTermResponse;
import welkit_server.domain.terms.dto.response.TermsResponse;
import welkit_server.domain.terms.service.TermService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermController {

    private final TermService termService;

    @Operation(summary = "용어 조회", description = "등록된 용어를 조회합니다")
    @GetMapping
    public ResponseEntity<SuccessResponse<TermsResponse>> getTerms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        TermsResponse termsFindAll = termService.getTerms(page,size,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, termsFindAll));
    }

    @Operation(summary = "카테고리별 용어 조회", description = "사용자가 선택한 카테고리에 맞게 용어를 조회합니다")
    @GetMapping("/category")
    public ResponseEntity<SuccessResponse<TermsResponse>> getCategoryTerms(
            @RequestParam(value = "categoryId", required = false) List<Long> categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        TermsResponse termsFindByCategory = termService.getTermsByCategory(page,size,categoryId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, termsFindByCategory));
    }

    @Operation(summary = "용어 등록", description = "새로운 용어를 등록합니다")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateTermResponse>> createTerm(
            @Valid @RequestBody CreateTermRequest createTerm,
            Authentication authentication
    ) {
        CreateTermResponse createTermResponse = termService.createTerm(createTerm, authentication);
        SuccessResponse<CreateTermResponse> body = SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, createTermResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "용어 수정", description = "등록되어 있는 용어를 수정합니다")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<EditTermResponse>> updateTerm(
            @PathVariable("id") Long termId,
            @Valid @RequestBody EditTermRequest editTermRequest,
            Authentication authentication
    ) {
        EditTermResponse editTermResponse = termService.editTerm(termId, editTermRequest,authentication);
        SuccessResponse<EditTermResponse> body = SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editTermResponse);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(summary = "용어 삭제", description = "등록되어 있는 용어를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteTerm(
            @PathVariable("id") Long termId,
            Authentication authentication
    ) {
        termService.deleteTerm(termId,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
