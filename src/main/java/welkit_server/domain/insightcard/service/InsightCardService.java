package welkit_server.domain.insightcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.dto.response.GetAllInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.InsightCardResponse;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.repository.InsightCardRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.ForbiddenException;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InsightCardService {

    private final InsightCardRepository insightCardRepository;
    private final UserRepository userRepository;

    public List<GetAllInsightCardResponse> getAllInsightPersonCards(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        List<InsightCard> insightCards = insightCardRepository.findAllInsightPersonCards(user);

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

    public List<GetAllInsightCardResponse> getAllInsightWorkCards(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        List<InsightCard> insightCards = insightCardRepository.findAllInsightWorkCards(user);

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
    public InsightCardResponse getInsightCardById(Long cardId,Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        InsightCard insightCard = findOwnedInsightCard(userId, cardId);
        insightCard.updateLastViewedAt();

        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse createInsightCard(InsightCardRequest createInsightCardRequest,Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        InsightCard insightCard = createInsightCardRequest.toEntity();
        insightCard.setUser(user);
        insightCard.updateUpdatedAt();

        insightCardRepository.save(insightCard);

        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse editInsightCard(Long cardId, InsightCardRequest editInsightCardRequest, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        InsightCard insightCard = findOwnedInsightCard(userId, cardId);
        insightCard.editInsightCard(editInsightCardRequest);
        insightCard.updateUpdatedAt();

        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public void deleteInsightCard(Long cardId, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        InsightCard insightCard = findOwnedInsightCard(userId, cardId);

        insightCardRepository.delete(insightCard);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

    public InsightCard findInsightCardById(Long cardId) {
        return insightCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.INSIGHT_CARD_NOT_FOUND));
    }

    private void validateOwnership(Long currentUserId, InsightCard insightCard) {
        if (!insightCard.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException(ErrorMessage.WK_NO_PERMISSION);
        }
    }

    private InsightCard findOwnedInsightCard(Long userId, Long cardId) {
        InsightCard insightCard = findInsightCardById(cardId);
        validateOwnership(userId, insightCard);
        return insightCard;
    }


}
