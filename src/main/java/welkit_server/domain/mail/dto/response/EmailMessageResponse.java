package welkit_server.domain.mail.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class EmailMessageResponse {

    private String to;
    private String subject;
    private String message;
}
