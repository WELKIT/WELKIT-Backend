package welkit_server.domain.terms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import welkit_server.domain.terms.entity.TermCategory;
import java.util.Optional;

@Repository
public interface TermCategoryRepository extends JpaRepository<TermCategory, Long> {

    Optional<TermCategory> findTermCategoryById(Long id);

}
