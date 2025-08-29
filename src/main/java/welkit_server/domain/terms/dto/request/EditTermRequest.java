package welkit_server.domain.terms.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EditTermRequest {

    private String name;

    private String definition;

    @NotNull
    private Long categoryId;

    private String categoryName;

}
