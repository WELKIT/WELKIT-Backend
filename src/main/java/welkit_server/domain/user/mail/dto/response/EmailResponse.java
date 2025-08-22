package welkit_server.domain.user.mail.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class EmailResponse {

    private String code;
    private String message;
}
