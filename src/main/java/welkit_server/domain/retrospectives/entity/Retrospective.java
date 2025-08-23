package welkit_server.domain.retrospectives.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.retrospectives.model.Type;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;
import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name="retrospectives")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Retrospective extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Type type;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
