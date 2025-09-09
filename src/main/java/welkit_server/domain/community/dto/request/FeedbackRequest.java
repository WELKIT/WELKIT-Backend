package welkit_server.domain.community.dto.request;

import lombok.Getter;
import welkit_server.domain.community.model.TargetType;

import jakarta.validation.constraints.NotNull;

@Getter
public class FeedbackRequest {
    @NotNull
    private TargetType targetType;
    @NotNull
    private Long targetId;
    @NotNull
    private Boolean isHelpful;
}