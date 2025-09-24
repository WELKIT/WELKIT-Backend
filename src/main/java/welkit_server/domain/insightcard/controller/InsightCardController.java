package welkit_server.domain.insightcard.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.dto.response.GetAllInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.GetFavoriteInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.GetInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.InsightCardResponse;
import welkit_server.domain.insightcard.model.CardType;
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
    public ResponseEntity<SuccessResponse<GetInsightCardResponse>> getAllInsightPersonCard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        GetInsightCardResponse insightPersonCards = insightCardService.getAllInsightPersonCards(page, size, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insightPersonCards));
    }

    @Operation(summary = "인물 카드 상세 조회", description = "등록된 인물 카드 중 선택한 인물 카드를 조회합니다")
    @GetMapping("/person/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> getInsightPersonCardByCardNumber(
            @PathVariable("id") Long insightPersonCardId,
            Authentication authentication
    ) {
        InsightCardResponse insighPersonCard = insightCardService.getInsightCardById(insightPersonCardId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insighPersonCard));
    }

    @Operation(summary = "인물 카드 최근본 카드 조회", description = "등록된 인물 카드 중 최근 본 인물 카드를 조회합니다")
    @GetMapping("/person/recent")
    public ResponseEntity<SuccessResponse<GetAllInsightCardResponse>> getRecentInsightPersonCard(Authentication authentication) {
        List<GetAllInsightCardResponse> recentInsightCard = insightCardService.getTop4LastViewedAtInsightCards(CardType.PERSON, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, recentInsightCard));
    }

    @Operation(summary = "인물 카드 즐겨찾기 등록된 카드 조회", description = "사용자가 즐겨찾기에 등록한 인물 카드를 조회합니다 ")
    @GetMapping("/person/favorites")
    public ResponseEntity<SuccessResponse<GetFavoriteInsightCardResponse>> getFavoriteInsightPersonCard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        GetFavoriteInsightCardResponse favoritePersonInsightCards  = insightCardService.getFavoritePersonInsightCards(page, size, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, favoritePersonInsightCards));
    }


    @Operation(summary = "인물 카드 생성", description = "새로운 인물 카드를 생성합니다")
    @PostMapping("/person")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> createInsightPersonCard(
            @Valid @RequestBody InsightCardRequest createInsightPersonCardRequest,
            Authentication authentication
    ) {
        InsightCardResponse  insightPersonCardResponse = insightCardService.createInsightCard(createInsightPersonCardRequest,authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, insightPersonCardResponse));
    }

    @Operation(summary = "인물 카드 수정", description = "등록되어 있는 인물 카드를 수정합니다")
    @PatchMapping("/person/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> updatInsightPersonCard(
            @PathVariable("id") Long insightPersonCardId,
            @Valid @RequestBody InsightCardRequest editInsightPersonCardRequest,
            Authentication authentication
    ) {
        InsightCardResponse editInsightPersonCardResponse = insightCardService.editInsightCard(insightPersonCardId, editInsightPersonCardRequest,authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editInsightPersonCardResponse));
    }

    @Operation(summary = "인물 카드 삭제", description = "등록되어 있는 인물 카드를 삭제합니다")
    @DeleteMapping("/person/{id}")
    public ResponseEntity<SuccessResponse> deleteInsightPersonCard(
            @PathVariable("id") Long insightPersonCardId,
            Authentication authentication
    ) {
        insightCardService.deleteInsightCard(insightPersonCardId,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

    @Operation(summary = "업무 카드 전체 조회", description = "등록된 업무 카드를 전체 조회합니다")
    @GetMapping("/work")
    public ResponseEntity<SuccessResponse<GetInsightCardResponse>> getAllInsightWorkCard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        GetInsightCardResponse insightWorkCards = insightCardService.getAllInsightWorkCards(page, size, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insightWorkCards));
    }

    @Operation(summary = "업무 카드 상세 조회", description = "등록된 업무 카드 중 선택한 업무 카드를 조회합니다")
    @GetMapping("/work/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> getInsightWorkCardByCardNumber(
            @PathVariable("id") Long insightWorkCardId,
            Authentication authentication
    ) {
        InsightCardResponse insighWorkCard = insightCardService.getInsightCardById(insightWorkCardId, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, insighWorkCard));
    }

    @Operation(summary = "업무 카드 최근본 카드 조회", description = "등록된 업무 카드 중 최근 본 업무 카드를 조회합니다")
    @GetMapping("/work/recent")
    public ResponseEntity<SuccessResponse<GetAllInsightCardResponse>> getRecentInsightWorkCard(Authentication authentication) {
        List<GetAllInsightCardResponse> recentInsightCard = insightCardService.getTop4LastViewedAtInsightCards(CardType.WORK, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, recentInsightCard));
    }

    @Operation(summary = "인물 카드 즐겨찾기 등록된 카드 조회", description = "사용자가 즐겨찾기에 등록한 인물 카드를 조회합니다 ")
    @GetMapping("/work/favorites")
    public ResponseEntity<SuccessResponse<GetFavoriteInsightCardResponse>> getFavoriteInsightWorkCard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        GetFavoriteInsightCardResponse favoriteWorkInsightCards  = insightCardService.getFavoriteWorkInsightCards(page, size, authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.LOAD_SUCCESS, favoriteWorkInsightCards));
    }

    @Operation(summary = "업무 카드 생성", description = "새로운 업무 카드를 생성합니다")
    @PostMapping("/work")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> createInsightWorkCard(
            @Valid @RequestBody InsightCardRequest createInsightWorkCardRequest,
            Authentication authentication
    ) {
        InsightCardResponse  insightWorkCardResponse = insightCardService.createInsightCard(createInsightWorkCardRequest,authentication);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of(SuccessMessage.CREATED_SUCCESS, insightWorkCardResponse));
    }

    @Operation(summary = "업무 카드 수정", description = "등록되어 있는 업무 카드를 수정합니다")
    @PatchMapping("/work/{id}")
    public ResponseEntity<SuccessResponse<InsightCardResponse>> updateInsightWorkCard(
            @PathVariable("id") Long insightWorkCardId,
            @Valid @RequestBody InsightCardRequest editInsightWorkCardRequest,
            Authentication authentication
    ) {
        InsightCardResponse editInsightWorkCardResponse = insightCardService.editInsightCard(insightWorkCardId, editInsightWorkCardRequest,authentication);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.of(SuccessMessage.UPDATED_SUCCESS, editInsightWorkCardResponse));
    }

    @Operation(summary = "업무 카드 삭제", description = "등록되어 있는 업무 카드를 삭제합니다")
    @DeleteMapping("/work/{id}")
    public ResponseEntity<SuccessResponse> deleteInsightWorkCard(
            @PathVariable("id") Long insightWorkCardId,
            Authentication authentication
    ) {
        insightCardService.deleteInsightCard(insightWorkCardId,authentication);
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETED_SUCCESS));
    }

}
