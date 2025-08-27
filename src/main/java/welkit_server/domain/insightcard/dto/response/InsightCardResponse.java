package welkit_server.domain.insightcard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InsightCardResponse {

    private Long cardId;
    private String title;
    private String description;
    private String note;
    private CardType type;
    @JsonProperty("isFavorite")
    private boolean favorite;

    public static InsightCardResponse fromEntity(InsightCard insightCard) {
        return InsightCardResponse.builder()
                .cardId(insightCard.getId())
                .title(insightCard.getTitle())
                .description(insightCard.getDescription())
                .note(insightCard.getNote())
                .type(insightCard.getType())
                .favorite(insightCard.isFavorite())
                .build();
    }

}
