package welkit_server.domain.terms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import welkit_server.domain.terms.entity.Term;
import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t FROM Term t ORDER BY t.lastModifiedDate DESC")
    List<Term> findAllTerms();

    @Query("SELECT t FROM Term t WHERE t.category.id = :categoryId ORDER BY t.lastModifiedDate DESC")
    List<Term> findAllTermsByCategoryId(Long categoryId);

}
