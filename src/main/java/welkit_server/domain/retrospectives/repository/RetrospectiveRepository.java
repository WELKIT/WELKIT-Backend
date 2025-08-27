package welkit_server.domain.retrospectives.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import welkit_server.domain.retrospectives.entity.Retrospective;

@Repository
public interface RetrospectiveRepository extends JpaRepository<Retrospective, Long> {
}
