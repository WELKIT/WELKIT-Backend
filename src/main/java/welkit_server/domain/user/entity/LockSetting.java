package welkit_server.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.user.model.FeatureName;


@Entity
@Getter
@Builder
@Table(name="lock_settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockSetting extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private FeatureName featureName;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked;

}
