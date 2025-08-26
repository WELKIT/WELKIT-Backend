package welkit_server.domain.insightcard.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightCardRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private String note;
    @NotNull
    private CardType type;
    private Boolean isFavorite;

    public InsightCard toEntity() {
        return InsightCard.builder()
                .title(title != null ? title.trim() : null)
                .description(description != null ? description.trim() : null)
                .note(note != null ? note.trim() : null)
                .type(type)
                .isFavorite(isFavorite)
                .build();
    }

}
