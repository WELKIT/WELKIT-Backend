package welkit_server.domain.terms.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EditTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;

    public static EditTermResponse of(Long termId, String name, String definition, Long categoryId) {
        return new EditTermResponse(termId, name, definition, categoryId);
    }

}
