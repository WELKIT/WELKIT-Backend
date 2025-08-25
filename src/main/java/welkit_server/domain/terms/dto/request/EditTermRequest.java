package welkit_server.domain.terms.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EditTermRequest {

    private String name;
    private String definition;
    private Long categoryId;
    private String categoryName;
}
