package welkit_server.domain.insightcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.dto.response.GetAllInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.InsightCardResponse;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.repository.InsightCardRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class InsightCardService {

    private final InsightCardRepository insightCardRepository;

    public List<GetAllInsightCardResponse> getAllInsightCards() {
        List<InsightCard> insightCards = insightCardRepository.findAll();
        return insightCards.stream()
                .map(insightCard -> GetAllInsightCardResponse.builder()
                        .insightCardId(insightCard.getId())
                        .title(insightCard.getTitle())
                        .description(insightCard.getDescription())
                        .note(insightCard.getNote())
                        .type(insightCard.getType())
                        .lastViewedAt(insightCard.getLastViewedAt())
                        .updatedAt(insightCard.getLastModifiedDate())
                        .build())
                .toList();
    }

    @Transactional
    public InsightCardResponse getInsightCardById(Long insightCardId) {
        InsightCard insightCard = findInsightCardById(insightCardId);
        insightCard.updateLastViewedAt();
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse createInsightCard(InsightCardRequest createInsightCardRequest) {
        InsightCard insightCard = insightCardRepository.save(createInsightCardRequest.toEntity());
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse editInsightCard(Long insightCardId, InsightCardRequest editInsightCardRequest) {
        InsightCard insightCard = findInsightCardById(insightCardId);
        insightCard.editInsightCard(editInsightCardRequest);
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public void deleteInsightCard(Long insightCardId) {
        InsightCard insightCard = findInsightCardById(insightCardId);
        insightCardRepository.delete(insightCard);
    }

    public InsightCard findInsightCardById(Long insightCardId) {
        return insightCardRepository.findById(insightCardId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.INSIGHT_CARD_NOT_FOUND));
    }

}
