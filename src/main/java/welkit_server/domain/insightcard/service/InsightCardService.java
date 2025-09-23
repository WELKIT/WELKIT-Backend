package welkit_server.domain.insightcard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.dto.response.GetAllInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.GetFavoriteInsightCardResponse;
import welkit_server.domain.insightcard.dto.response.InsightCardResponse;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;
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
        User currentUser = getAuthenticatedUser(authentication);

        List<InsightCard> insightCards = insightCardRepository.findAllInsightPersonCards(currentUser);

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
        User currentUser = getAuthenticatedUser(authentication);

        List<InsightCard> insightCards = insightCardRepository.findAllInsightWorkCards(currentUser);

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

    public List<GetAllInsightCardResponse> getTop4LastViewedAtInsightCards(CardType cardType, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        List<InsightCard> insightCards = insightCardRepository.findTop4ByUserIdAndTypeAndLastViewedAtIsNotNullOrderByLastViewedAtDesc(userId,cardType);

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
        User currentUser = getAuthenticatedUser(authentication);

        InsightCard insightCard = findOwnedInsightCard(currentUser.getId(), cardId);
        insightCard.updateLastViewedAt();

        return InsightCardResponse.fromEntity(insightCard);
    }

    public GetFavoriteInsightCardResponse getFavoritePersonInsightCards(int page, int size, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        Pageable pageable = PageRequest.of(page, size);
        Page<InsightCard> favoritePersonInsightCard = insightCardRepository.findFavoritePersonCards(currentUser, pageable);

        long totalFavoritePersonCardAmount = insightCardRepository.countByUserAndTypeAndIsFavoriteTrue(currentUser,CardType.PERSON);

        List<GetAllInsightCardResponse> favoritePersonInsightCards = favoritePersonInsightCard.stream()
                .map(insightPersonCards-> GetAllInsightCardResponse.builder()
                        .cardId(insightPersonCards.getId())
                        .title(insightPersonCards.getTitle())
                        .description(insightPersonCards.getDescription())
                        .note(insightPersonCards.getNote())
                        .type(insightPersonCards.getType())
                        .favorite(insightPersonCards.isFavorite())
                        .lastViewedAt(insightPersonCards.getLastViewedAt())
                        .updatedAt(insightPersonCards.getLastModifiedDate())
                        .build())
                .toList();

        return GetFavoriteInsightCardResponse.builder()
                .favoriteTotal(totalFavoritePersonCardAmount)
                .cards(favoritePersonInsightCards)
                .build();
    }

    public GetFavoriteInsightCardResponse getFavoriteWorkInsightCards(int page, int size, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        Pageable pageable = PageRequest.of(page, size);
        Page<InsightCard> favoriteWorkInsightCard = insightCardRepository.findFavoriteWorkCards(currentUser, pageable);

        long totalFavoriteWorkCardAmount = insightCardRepository.countByUserAndTypeAndIsFavoriteTrue(currentUser,CardType.WORK);

        List<GetAllInsightCardResponse> favoriteWorkInsightCards =  favoriteWorkInsightCard.stream()
                .map(insightWorkCards-> GetAllInsightCardResponse.builder()
                        .cardId(insightWorkCards.getId())
                        .title(insightWorkCards.getTitle())
                        .description(insightWorkCards.getDescription())
                        .note(insightWorkCards.getNote())
                        .type(insightWorkCards.getType())
                        .favorite(insightWorkCards.isFavorite())
                        .lastViewedAt(insightWorkCards.getLastViewedAt())
                        .updatedAt(insightWorkCards.getLastModifiedDate())
                        .build())
                .toList();

        return GetFavoriteInsightCardResponse.builder()
                .favoriteTotal(totalFavoriteWorkCardAmount)
                .cards(favoriteWorkInsightCards)
                .build();
    }

    @Transactional
    public InsightCardResponse createInsightCard(InsightCardRequest createInsightCardRequest,Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        InsightCard insightCard = createInsightCardRequest.toEntity();
        insightCard.setUser(currentUser);
        insightCard.updateUpdatedAt();

        insightCardRepository.save(insightCard);

        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public InsightCardResponse editInsightCard(Long cardId, InsightCardRequest editInsightCardRequest, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        InsightCard insightCard = findOwnedInsightCard(currentUser.getId(), cardId);
        insightCard.editInsightCard(editInsightCardRequest);
        insightCard.updateUpdatedAt();

        return InsightCardResponse.fromEntity(insightCard);
    }

    @Transactional
    public void deleteInsightCard(Long cardId, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        InsightCard insightCard = findOwnedInsightCard(currentUser.getId(), cardId);

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
