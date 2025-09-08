package welkit_server.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.community.entity.CommunityComments;

public interface CommunityCommentRepository extends JpaRepository<CommunityComments, Long> {
}
