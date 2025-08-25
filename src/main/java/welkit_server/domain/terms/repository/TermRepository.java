package welkit_server.domain.terms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.terms.entity.Term;

import java.util.Optional;

public interface TermRepository extends JpaRepository<Term, Long> {
    Optional<Term> findById(Long id);
}
