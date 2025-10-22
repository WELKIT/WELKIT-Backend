package welkit_server.domain.community.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPagePlainResponse {

    private PostPageInfoResponse postInfo;
    private List<PostSummaryPlainResponse> posts;

}

