package welkit_server.domain.insightcard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;
import welkit_server.domain.user.entity.User;
import java.util.List;

@Repository
public interface InsightCardRepository extends JpaRepository<InsightCard, Long> {

    @Query("SELECT i FROM InsightCard i " +
            "WHERE i.user = :user AND i.type = 'PERSON' " +
            "ORDER BY i.updatedAt DESC")
    Page<InsightCard> findAllInsightPersonCards(@Param("user") User user, Pageable pageable);

    @Query("SELECT i FROM InsightCard i " +
            "WHERE i.user = :user AND i.type = 'WORK' " +
            "ORDER BY i.updatedAt DESC")
    Page<InsightCard> findAllInsightWorkCards(@Param("user") User user,Pageable pageable);

    List<InsightCard> findTop4ByUserIdAndTypeAndLastViewedAtIsNotNullOrderByLastViewedAtDesc(Long userId, CardType type);

    @Query("SELECT i FROM InsightCard i " +
            "WHERE i.user = :user AND i.type ='PERSON' AND i.isFavorite = true " +
            "ORDER BY i.updatedAt DESC")
    Page<InsightCard> findFavoritePersonCards(@Param("user") User user, Pageable pageable);

    @Query("SELECT i FROM InsightCard i " +
            "WHERE i.user = :user AND i.type ='WORK' AND i.isFavorite = true " +
            "ORDER BY i.updatedAt DESC")
    Page<InsightCard> findFavoriteWorkCards(@Param("user") User user, Pageable pageable);

    long countByUserAndTypeAndIsFavoriteTrue(User user, CardType type);
    long countByUserAndType(User user, CardType type);
}
