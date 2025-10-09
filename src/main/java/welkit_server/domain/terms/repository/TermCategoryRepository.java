package welkit_server.domain.terms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TermCategoryRepository extends JpaRepository<TermCategory, Long> {

    Optional<TermCategory> findByIdAndUser(Long categoryId, User user);

    @Query("SELECT c FROM TermCategory c WHERE c.user = :user")
    List<TermCategory> findAlLCategory(User user);

    Optional<TermCategory> findByNameAndUser(String name, User user);
}
