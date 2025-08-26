package welkit_server.domain.insightcard.entity;

import jakarta.persistence.*;
import lombok.*;
import welkit_server.domain.insightcard.dto.request.InsightCardRequest;
import welkit_server.domain.insightcard.model.CardType;
import welkit_server.domain.user.entity.User;
import welkit_server.global.domain.BaseEntity;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(name="insight_cards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightCard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private CardType type;

    @Column(name = "is_favorite", nullable = false)
    @Builder.Default
    private boolean isFavorite = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") //nullable = false
    private User user;

    @Column(name = "last_viewed_at") //nullable = false
    private LocalDateTime lastViewedAt;

    public void updateLastViewedAt() {
        this.lastViewedAt = LocalDateTime.now();
    }

    public void editInsightCard(InsightCardRequest request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle().trim();
        }
        if (request.getDescription() != null) {
            this.description = request.getDescription().trim();
        }
        if (request.getNote() != null) {
            this.note = request.getNote().trim();
        }
        if (request.getType() != null) {
            this.type = request.getType();
        }
        if (request.getIsFavorite() != null) {
            this.isFavorite = request.getIsFavorite();
        }
    }

}
