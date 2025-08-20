package welkit_server.domain.terms.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.user.entity.User;

@Entity
@Getter
@Builder
@Table(name="terms",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"name", "category_id"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Term extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private TermCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
