package welkit_server.domain.community.dto.response;

import lombok.Builder;
import lombok.Getter;
import welkit_server.domain.community.model.TargetType;

@Getter
@Builder
public class FeedbackResponse {
    private TargetType targetType;
    private Long targetId;
    private Integer helpfulCount;
    private Integer notHelpfulCount;

    public static FeedbackResponse fromEntity(TargetType targetType, Long targetId, int helpfulCount, int notHelpfulCount) {
        return FeedbackResponse.builder()
                .targetType(targetType)
                .targetId(targetId)
                .helpfulCount(helpfulCount)
                .notHelpfulCount(notHelpfulCount)
                .build();
    }
}