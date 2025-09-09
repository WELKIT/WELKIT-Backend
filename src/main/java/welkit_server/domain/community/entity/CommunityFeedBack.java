package welkit_server.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.community.model.TargetType;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommunityFeedBack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_helpful")
    private Boolean isHelpful;

    public static CommunityFeedBack create(TargetType targetType, Long targetId, User user) {
        CommunityFeedBack feedback = new CommunityFeedBack();
        feedback.targetType = targetType;
        feedback.targetId = targetId;
        feedback.user = user;
        feedback.isHelpful = null;
        return feedback;
    }

    public void toggleHelpful(Boolean newValue) {
        if (this.isHelpful != null && this.isHelpful.equals(newValue)) {
            this.isHelpful = null; 
        } else {
            this.isHelpful = newValue;
        }
    }
    
}
