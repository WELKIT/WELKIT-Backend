package welkit_server.domain.retrospectives.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RetrospectiveRequest {

    @NotBlank
    private String title;
    @NotNull
    private Type type;
    @NotBlank
    private String content;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate endDate;

    public Retrospective toEntity() {
        return Retrospective.builder()
                .title(title != null ? title.trim() : null)
                .type(type)
                .content(content != null ? content.trim() : null)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

}
