package welkit_server.domain.terms.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllCategoryName {
    private long categoryId;
    private String categoryName;
}
