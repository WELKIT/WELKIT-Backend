package welkit_server.domain.insightcard.dto.response;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetInsightCardResponse {
    private Long totalAmount;
    private List<GetAllInsightCardResponse> cards;
}
