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

    public List<GetAllInsightCardResponse> getAllInsightPersonCards() {
        List<InsightCard> insightCards = insightCardRepository.findAllInsightPersonCards();
        return insightCards.stream()
                .map(insightCard -> GetAllInsightCardResponse.builder()
                        .cardId(insightCard.getId())
                        .title(insightCard.getTitle())
                        .description(insightCard.getDescription())
                        .note(insightCard.getNote())
                        .type(insightCard.getType())
                        .favorite(insightCard.isFavorite())
                        .lastViewedAt(insightCard.getLastViewedAt())
                        .updatedAt(insightCard.getLastModifiedDate())
                        .build())
                .toList();
    }

    public List<GetAllInsightCardResponse> getAllInsightWorkCards(){
        List<InsightCard> insightCards = insightCardRepository.findAllInsightWorkCards();
        return insightCards.stream()
                .map(insightCard -> GetAllInsightCardResponse.builder()
                        .cardId(insightCard.getId())
                        .title(insightCard.getTitle())
                        .description(insightCard.getDescription())
                        .note(insightCard.getNote())
                        .type(insightCard.getType())
                        .favorite(insightCard.isFavorite())
                        .lastViewedAt(insightCard.getLastViewedAt())
                        .updatedAt(insightCard.getLastModifiedDate())
                        .build())
                .toList();
    }

    @Transactional
    public InsightCardResponse getInsightCardById(Long cardId) {
        InsightCard insightCard = findInsightCardById(cardId);
        insightCard.updateLastViewedAt();
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse createInsightCard(InsightCardRequest createInsightCardRequest) {
        InsightCard insightCard = insightCardRepository.save(createInsightCardRequest.toEntity());
        insightCard.updateUpdatedAt();
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse editInsightCard(Long cardId, InsightCardRequest editInsightCardRequest) {
        InsightCard insightCard = findInsightCardById(cardId);
        insightCard.editInsightCard(editInsightCardRequest);
        insightCard.updateUpdatedAt();
        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public void deleteInsightCard(Long cardId) {
        InsightCard insightCard = findInsightCardById(cardId);
        insightCardRepository.delete(insightCard);
    }

    public InsightCard findInsightCardById(Long cardId) {
        return insightCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.INSIGHT_CARD_NOT_FOUND));
    }

}
