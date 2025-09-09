package welkit_server.domain.admin.dto.response;

import lombok.*;
import welkit_server.domain.admin.entity.Notice;
import welkit_server.domain.admin.model.NoticePriority;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllNoticeResponse {

    private Long noticeId;
    private String title;
    private String content;
    private NoticePriority priority;
    private String lastModified;

    public static GetAllNoticeResponse fromEntity(Notice notice) {
        return GetAllNoticeResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .priority(notice.getPriority())
                .lastModified(notice.getLastModifiedDate()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .build();
    }

}
