package welkit_server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.user.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findByGoogleEmail(String googleEmail);

    boolean existsByGoogleEmail(String googleEmail);

}
