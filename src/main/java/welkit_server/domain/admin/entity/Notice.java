package welkit_server.domain.admin.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private NoticePriority priority;

}
