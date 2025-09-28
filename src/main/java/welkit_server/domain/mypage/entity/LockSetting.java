package welkit_server.domain.mypage.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.global.domain.BaseEntity;


@Entity
@Getter
@Builder
@Table(name="lock_settings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LockSetting extends BaseEntity {

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
    private boolean isLocked = false;

    public void settingLock(boolean isLocked) {
        this.isLocked = isLocked;
    }

}
