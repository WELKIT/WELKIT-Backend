package welkit_server.domain.terms.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@Table(name="term_categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id") //nullable = false
    private User user;

    public TermCategory(String categoryName, User user) {
        this.name = categoryName;
        this.user = user;
    }
}
