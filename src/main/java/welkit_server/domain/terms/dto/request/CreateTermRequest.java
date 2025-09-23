package welkit_server.domain.terms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import welkit_server.domain.terms.entity.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateTermRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank
    @Size(min = 1, max = 500)
    private String definition;

    private Long categoryId;

    @Size(min = 1, max = 20)
    private String categoryName;

    public Term toEntity(TermCategory category) {
        return Term.builder()
                .name(name.trim())
                .definition(definition.trim())
                .category(category)
                .build();
    }

}
