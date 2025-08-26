package welkit_server.domain.insightcard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import welkit_server.domain.insightcard.entity.InsightCard;

import java.util.List;

@Repository
public interface InsightCardRepository extends JpaRepository<InsightCard, Long> {

    @Query("SELECT i FROM InsightCard i ORDER BY i.lastModifiedDate DESC")
    List<InsightCard> findAllInsightCards();

}
