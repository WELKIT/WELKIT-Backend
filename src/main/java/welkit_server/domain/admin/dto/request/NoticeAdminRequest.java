package welkit_server.domain.admin.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import welkit_server.domain.admin.entity.Notice;
import welkit_server.domain.admin.model.NoticePriority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeAdminRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private NoticePriority priority;

    public Notice toEntity() {
        return Notice.builder()
                .title(title != null ? title.trim() : null)
                .content(content != null ? content.trim() : null)
                .priority(priority)
                .build();
    }

}
