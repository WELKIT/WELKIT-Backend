package welkit_server.domain.terms.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetCategoryTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;

}
