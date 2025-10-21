package welkit_server.domain.insightcard.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InsightCardRequest {

    @NotBlank
    @Size(min = 1, max = 30)
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String description;

    @Size(max = 200)
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
