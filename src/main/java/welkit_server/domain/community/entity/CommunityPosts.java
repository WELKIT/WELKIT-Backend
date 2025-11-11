package welkit_server.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;
import welkit_server.domain.community.dto.request.PostUpdateRequest;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.global.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name="community_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityPosts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 30)
    private String title;

    @Column(nullable=false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    private JobRole jobRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CommunityPostStatus status;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 10)
    private List<CommunityComments> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Where(clause = "target_type = 'POSTS'")
    @JoinColumn(name = "target_id", insertable = false, updatable = false)
    @Builder.Default
    @BatchSize(size = 10)
    private List<CommunityFeedBack> feedbacks = new ArrayList<>();

    public void editPost(PostUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.jobRole = request.getJobRole(); // 게시글의 jobRole만 업데이트
    }

    public void setFeedbacks(List<CommunityFeedBack> feedbacks) { this.feedbacks = feedbacks; }
    public void setComments(List<CommunityComments> comments) { this.comments = comments; }

}
