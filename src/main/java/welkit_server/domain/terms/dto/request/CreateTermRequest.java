package welkit_server.domain.terms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import welkit_server.domain.terms.entity.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateTermRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    @NotNull
    private Long categoryId;

    @NotBlank
    private String categoryName;

    public Term toEntity(TermCategory category) {
        return Term.builder()
                .name(name.trim())
                .definition(definition.trim())
                .category(category)
                .build();
    }

}
