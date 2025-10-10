package welkit_server.domain.terms.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@Table(name="terms")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Term extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 500)
    private String definition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private TermCategory category;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void editTerm(EditTermRequest editTermRequest, TermCategory newCategory) {
        if (editTermRequest.getName() != null) {
            this.name = editTermRequest.getName();
        }
        if (editTermRequest.getDefinition() != null) {
            this.definition = editTermRequest.getDefinition();
        }
        if (newCategory != null) {
            this.category = newCategory;
        }
    }

}
