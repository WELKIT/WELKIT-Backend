package welkit_server.domain.insightcard.dto.response;
import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetFavoriteInsightCardResponse {

    private Long favoriteTotal;
    private List<GetAllInsightCardResponse> cards;

}



