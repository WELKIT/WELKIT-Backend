package welkit_server.domain.terms.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAllTermResponse {

    private Long termId;
    private String name;
    private String definition;
    private Long categoryId;
    private LocalDateTime updatedAt;

}
