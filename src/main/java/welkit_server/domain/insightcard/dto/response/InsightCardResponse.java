package welkit_server.domain.insightcard.dto.response;

import lombok.*;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InsightCardResponse {

    private Long insightCardId;
    private String title;
    private String description;
    private String note;
    private CardType type;
    private boolean isFavorite;

    public static InsightCardResponse fromEntity(InsightCard insightCard) {
        return InsightCardResponse.builder()
                .insightCardId(insightCard.getId())
                .title(insightCard.getTitle())
                .description(insightCard.getDescription())
                .note(insightCard.getNote())
                .type(insightCard.getType())
                .isFavorite(insightCard.isFavorite())
                .build();
    }

}
