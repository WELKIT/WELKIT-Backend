package welkit_server.domain.insightcard.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.dto.response.GetAllInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.InsightCardResponse;
import welkit_server.domain.insightcard.service.InsightCardService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class InsightCardController {

    private final InsightCardService insightCardService;

    @Operation(summary = "인물 카드 전체 조회", description = "등록된 인물 카드를 전체 조회합니다")
    @GetMapping("/person")
    public ResponseEntity<SuccessResponse<GetAllInsightCardResponse>> getAllInsightPersonCard() {
        List<GetAllInsightCardResponse> insightPersonCards = insightCardService.getAllInsightCards();
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insightPersonCards));
    }

    @Operation(summary = "인물 카드 상세 조회", description = "등록된 인물 카드 중 선택한 인물 카드를 조회합니다")
    @GetMapping("/person/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> getInsightPersonCardByCardNumber(@PathVariable("id") Long insightPersonCardId) {
        InsightCardResponse insighPersonCard = insightCardService.getInsightCardById(insightPersonCardId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insighPersonCard));
    }

    @Operation(summary = "인물 카드 생성", description = "새로운 인물 카드를 생성합니다")
    @PostMapping("/person")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> createInsightPersonCard(@Valid @RequestBody InsightCardRequest createInsightPersonCardRequest) {
        InsightCardResponse  insightPersonCardResponse = insightCardService.createInsightCard(createInsightPersonCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, insightPersonCardResponse));
    }

    @Operation(summary = "인물 카드 수정", description = "등록되어 있는 인물 카드를 수정합니다")
    @PatchMapping("/person/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> updatInsightPersonCard(
            @PathVariable("id") Long insightCardId,
            @Valid @RequestBody InsightCardRequest editInsightPersonCardRequest
    ) {
        InsightCardResponse editInsightPersonCardResponse = insightCardService.editInsightCard(insightCardId, editInsightPersonCardRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editInsightPersonCardResponse));
    }

    @Operation(summary = "인물 카드 삭제", description = "등록되어 있는 인물 카드를 삭제합니다")
    @DeleteMapping("/person/{id}")
    public ResponseEntity<SuccessResponse> deleteInsightPersonCard(@PathVariable("id") Long insightPersonCardId) {
        insightCardService.deleteInsightCard(insightPersonCardId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

    @Operation(summary = "업무 카드 전체 조회", description = "등록된 업무 카드를 전체 조회합니다")
    @GetMapping("/work")
    public ResponseEntity<SuccessResponse<GetAllInsightCardResponse>> getAllInsightWorkCard() {
        List<GetAllInsightCardResponse> insightWorkCards = insightCardService.getAllInsightCards();
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insightWorkCards));
    }

    @Operation(summary = "업무 카드 상세 조회", description = "등록된 업무 카드 중 선택한 업무 카드를 조회합니다")
    @GetMapping("/work/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> getInsightWorkCardByCardNumber(@PathVariable("id") Long insightWorkCardId) {
        InsightCardResponse insighWorkCard = insightCardService.getInsightCardById(insightWorkCardId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insighWorkCard));
    }

    @Operation(summary = "업무 카드 생성", description = "새로운 업무 카드를 생성합니다")
    @PostMapping("/work")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> createInsightWorkCard(@Valid @RequestBody InsightCardRequest createInsightWorkCardRequest) {
        InsightCardResponse  insightWorkCardResponse = insightCardService.createInsightCard(createInsightWorkCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, insightWorkCardResponse));
    }

    @Operation(summary = "업무 카드 수정", description = "등록되어 있는 업무 카드를 수정합니다")
    @PatchMapping("/work/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> updateInsightWorkCard(
            @PathVariable("id") Long insightCardId,
            @Valid @RequestBody InsightCardRequest editInsightWorkCardRequest
    ) {
        InsightCardResponse editInsightWorkCardResponse = insightCardService.editInsightCard(insightCardId, editInsightWorkCardRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editInsightWorkCardResponse));
    }

    @Operation(summary = "업무 카드 삭제", description = "등록되어 있는 업무 카드를 삭제합니다")
    @DeleteMapping("/work/{id}")
    public ResponseEntity<SuccessResponse> deleteInsightWorkCard(@PathVariable("id") Long insightWorkCardId) {
        insightCardService.deleteInsightCard(insightWorkCardId);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
