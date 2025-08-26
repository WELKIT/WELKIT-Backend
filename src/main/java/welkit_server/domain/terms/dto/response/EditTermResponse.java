package welkit_server.domain.terms.dto.response;

import lombok.*;
import welkit_server.domain.terms.entity.Term;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EditTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;

    public static EditTermResponse fromEntity(Term term) {
        return EditTermResponse.builder()
                .termId(term.getId())
                .name(term.getName())
                .definition(term.getDefinition())
                .categoryId(term.getCategory().getId())
                .build();
    }

}
