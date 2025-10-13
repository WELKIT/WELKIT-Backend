package welkit_server.domain.terms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.user.entity.User;

import java.util.List;

@Repository
public interface TermRepository extends JpaRepository<Term, Long> {

    @Query("SELECT t FROM Term t " +
            "WHERE t.user = :user " +
            "ORDER BY t.lastModifiedDate DESC")
    Page<Term> findByUserOrderByCreatedDateDesc(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Term t " +
            "WHERE t.user = :user " +
            "AND t.category.id IN :categoryId  " +
            "ORDER BY t.lastModifiedDate DESC")
    Page<Term> findAllByUserAndCategoryId(
            @Param("user") User user,
            @Param("categoryId") List<Long>categoryId,
            Pageable pageable);

    @Query("""
           SELECT t FROM Term t
           WHERE t.user = :user
           AND (:keyword IS NULL OR t.name LIKE %:keyword% OR t.definition LIKE %:keyword%)
           AND(:categoryId IS NULL OR t.category.id IN :categoryId)
           ORDER BY t.lastModifiedDate DESC
    """)
    Page<Term> searchTerms(
            @Param("user") User user,
            @Param("keyword") String keyword,
            @Param("categoryId") List<Long>categoryId,
            Pageable pageable);

    long countByUser(User user);

    @Query("SELECT COUNT(t) FROM Term t " +
            "WHERE t.user = :user " +
            "AND t.category.id IN :categoryId")
    long countByUserAndCategoryId(@Param("user") User user, @Param("categoryId") List<Long> categoryId);


}
