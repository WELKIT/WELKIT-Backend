package welkit_server.domain.retrospectives.dto.response;

import lombok.*;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RetrospectiveResponse {

    private Long retrospectiveId;
    private String title;
    private String content;
    private Type type;
    private LocalDate startDate;
    private LocalDate endDate;

    public static RetrospectiveResponse fromEntity(Retrospective retrospective) {
        return RetrospectiveResponse.builder()
                .retrospectiveId(retrospective.getId())
                .title(retrospective.getTitle())
                .content(retrospective.getContent())
                .type(retrospective.getType())
                .startDate(retrospective.getStartDate())
                .endDate(retrospective.getEndDate())
                .build();
    }

}
