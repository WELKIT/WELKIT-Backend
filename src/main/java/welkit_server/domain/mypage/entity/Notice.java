package welkit_server.domain.mypage.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.mypage.model.NoticePriority;

@Entity
@Getter
@Builder
@Table(name="notices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private NoticePriority priority;

}
