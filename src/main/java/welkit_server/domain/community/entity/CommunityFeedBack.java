package welkit_server.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.community.model.TargetType;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@Table(name="community_feedbacks",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"target_type", "target_id", "user_id"}
        )
    }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityFeedBack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TargetType targetType;

    @Column(nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_helpful")
    private Boolean isHelpful;

}

