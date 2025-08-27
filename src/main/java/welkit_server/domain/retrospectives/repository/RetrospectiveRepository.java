package welkit_server.domain.retrospectives.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;

@Repository
public interface RetrospectiveRepository extends JpaRepository<Retrospective, Long> {

    @Query("SELECT r FROM Retrospective r WHERE r.type = :type ORDER BY r.createdDate DESC")
    Page<Retrospective> findAllRetrospectives(@Param("type") Type type, Pageable pageable);

}

