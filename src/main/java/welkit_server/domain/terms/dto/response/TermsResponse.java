package welkit_server.domain.terms.dto.response;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TermsResponse {
    private Long totalAmount;
    private List<GetAllTermResponse> terms;
}
