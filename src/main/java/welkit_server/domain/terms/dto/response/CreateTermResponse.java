package welkit_server.domain.terms.dto.response;

import lombok.*;
import welkit_server.domain.terms.entity.Term;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CreateTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;
    private String categoryName;

    public static CreateTermResponse fromEntity(Term term) {
        return CreateTermResponse.builder()
                .termId(term.getId())
                .name(term.getName())
                .definition(term.getDefinition())
                .categoryId(term.getCategory().getId())
                .categoryName(term.getCategory().getName())
                .build();
    }

}
