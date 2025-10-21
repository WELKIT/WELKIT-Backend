package welkit_server.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name="community_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityComments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPosts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CommunityComments parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CommunityComments> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "target_type = 'COMMENTS'")
    @JoinColumn(name = "target_id", insertable = false, updatable = false)
    private List<CommunityFeedBack> feedbacks;

    public void editComment(String comment) {
        this.comment = comment;
    }

}
