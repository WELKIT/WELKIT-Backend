package welkit_server.domain.insightcard.dto.response;

import lombok.*;
import welkit_server.domain.insightcard.model.CardType;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllInsightCardResponse {

    private Long insightCardId;
    private String title;
    private String description;
    private String note;
    private CardType type;
    private boolean isFavorite;
    private LocalDateTime lastViewedAt;
    private LocalDateTime updatedAt;

}
