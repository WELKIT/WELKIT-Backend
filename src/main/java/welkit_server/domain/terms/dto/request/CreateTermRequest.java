package welkit_server.domain.terms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateTermRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String definition;

    private Long categoryId;

    @NotBlank
    private String categoryName;

    public Term toEntity(TermCategory category) {
        return Term.builder()
                .name(name)
                .definition(definition)
                .category(category)
                .build();
    }
}
