package welkit_server.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.community.entity.CommunityFeedBack;

public interface CommunityFeedBackRepository extends JpaRepository<CommunityFeedBack, Long> {
}
