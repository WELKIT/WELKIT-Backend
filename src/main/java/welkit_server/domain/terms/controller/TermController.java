package welkit_server.domain.terms.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.response.TermResponse;
import welkit_server.domain.terms.service.TermService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermController {

    private final TermService termService;

    @Operation(summary = "용어 사전 용어 등록", description = "새로운 용어를 등록합니다")
    @PostMapping
    public ResponseEntity<TermResponse> createTerm(@Valid @RequestBody CreateTermRequest createTerm){
        TermResponse termResponse = termService.createTerm(createTerm);
        return ResponseEntity.ok(termResponse);
    }
}
