package welkit_server.domain.community.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPageResponse {

    private PostPageInfoResponse postInfo;
    private List<PostSummaryResponse> posts;

}

