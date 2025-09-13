package welkit_server.domain.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import welkit_server.domain.admin.entity.Notice;
import welkit_server.domain.admin.model.NoticePriority;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GetAllNoticeResponse {

    private Long noticeId;
    private String title;
    private String content;
    private NoticePriority priority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime lastModified;

    public static GetAllNoticeResponse fromEntity(Notice notice) {
        return GetAllNoticeResponse.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .priority(notice.getPriority())
                .lastModified(notice.getLastModifiedDate())
                .build();

    }

}
