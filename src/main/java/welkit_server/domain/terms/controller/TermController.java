package welkit_server.domain.terms.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.EditTermResponse;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.service.TermService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermController {

    private final TermService termService;

    @Operation(summary = "용어 등록", description = "새로운 용어를 등록합니다")
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateTermResponse>> createTerm(@Valid @RequestBody CreateTermRequest createTerm) {
        CreateTermResponse createTermResponse = termService.createTerm(createTerm);
        SuccessResponse<CreateTermResponse> body = SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, createTermResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Operation(summary = "용어 수정", description = "기존에 등록되어 있는 용어를 수정합니다")
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<EditTermResponse>> updateTerm(
            @PathVariable("id") Long termId,
            @Valid @RequestBody EditTermRequest editTermRequest
    ) {
        EditTermResponse editTermResponse = termService.editTerm(termId, editTermRequest);
        SuccessResponse<EditTermResponse> body = SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editTermResponse);
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Operation(summary = "용어 삭제", description = "기존에 등록되어 있는 용어를 삭제합니다")
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteTerm(@PathVariable("id") Long termId){
        termService.deleteTerm(termId);
        return new ResponseEntity<>(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS), HttpStatus.OK);
    }

}
