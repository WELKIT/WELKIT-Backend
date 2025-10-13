package welkit_server.domain.retrospectives.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;
import welkit_server.domain.user.entity.User;

@Repository
public interface RetrospectiveRepository extends JpaRepository<Retrospective, Long> {

    @Query("SELECT r FROM Retrospective r " +
            "WHERE r.user = :user AND r.type = :type " +
            "ORDER BY r.createdDate DESC")
    Page<Retrospective> findAllRetrospectives(@Param("user") User user, @Param("type") Type type, Pageable pageable);

    @Query("""
           SELECT r FROM Retrospective r
           WHERE r.user = :user
           AND (:type IS NULL OR r.type = :type)
           AND (:keyword IS NULL OR r.title LIKE %:keyword% OR r.content LIKE %:keyword%)
           ORDER BY r.createdDate DESC
    """)
    Page<Retrospective> searchRetrospectives(
            @Param("user") User user,
            @Param("type") Type type,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}

