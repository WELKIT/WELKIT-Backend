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
    @Size(min = 1, max = 30)
    private String title;

    @NotBlank
    @Size(min = 1, max = 2000)
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
