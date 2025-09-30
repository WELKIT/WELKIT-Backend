package welkit_server.domain.retrospectives.dto.response;

import lombok.*;
import welkit_server.domain.retrospectives.model.Type;
import java.time.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllRetrospectiveResponse {

    private Long retrospectiveId;
    private String title;
    private String content;
    private Type type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

}
