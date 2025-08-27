package welkit_server.domain.insightcard.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import welkit_server.domain.insightcard.model.CardType;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllInsightCardResponse {

    private Long cardId;
    private String title;
    private String description;
    private String note;
    private CardType type;
    @JsonProperty("isFavorite")
    private boolean favorite;
    private LocalDateTime lastViewedAt;
    private LocalDateTime updatedAt;

}
