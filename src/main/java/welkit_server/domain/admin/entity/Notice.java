package welkit_server.domain.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.admin.dto.request.NoticeAdminRequest;
import welkit_server.domain.admin.model.NoticePriority;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@Table(name="notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private NoticePriority priority;

    public void editNotice(NoticeAdminRequest request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle().trim();
        }
        if (request.getContent() != null) {
            this.content = request.getContent().trim();
        }
        if (request.getPriority() != null) {
            this.priority = request.getPriority();
        }
    }
}
