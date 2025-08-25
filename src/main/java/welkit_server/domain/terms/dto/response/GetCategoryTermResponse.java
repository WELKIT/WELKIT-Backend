package welkit_server.domain.terms.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCategoryTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;

}
