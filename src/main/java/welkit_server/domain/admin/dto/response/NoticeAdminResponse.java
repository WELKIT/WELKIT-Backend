package welkit_server.domain.admin.dto.response;

import lombok.*;
import welkit_server.domain.admin.entity.Notice;
import welkit_server.domain.admin.model.NoticePriority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NoticeAdminResponse {

    private Long id;
    private String title;
    private String content;
    private NoticePriority priority;

    public static NoticeAdminResponse fromEntity(Notice notice) {
        return NoticeAdminResponse.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .priority(notice.getPriority())
                .build();
    }

}
