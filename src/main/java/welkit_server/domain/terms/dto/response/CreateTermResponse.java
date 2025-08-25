package welkit_server.domain.terms.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;
    private String categoryName;

    public static CreateTermResponse of(Long termId, String name, String definition, Long categoryId, String categoryName) {
        return new CreateTermResponse(termId, name, definition, categoryId, categoryName);
    }

}
