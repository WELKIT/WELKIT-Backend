package welkit_server.domain.community.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.community.model.CommunityPostStatus;
import welkit_server.domain.user.entity.User;

@Entity
@Getter
@Builder
@Table(name="community_posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommunityPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CommunityPostStatus status;

}
