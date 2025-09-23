package welkit_server.domain.terms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.user.entity.User;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t FROM Term t " +
            "WHERE t.user = :user " +
            "ORDER BY t.createdDate DESC")
    Page<Term> findAllByUser(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Term t " +
            "WHERE t.user = :user AND t.category.id = :categoryId  " +
            "ORDER BY t.createdDate DESC")
    Page<Term> findAllByUserAndCategoryId(@Param("user") User user, @Param("categoryId") Long categoryId, Pageable pageable);

}
