package welkit_server.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.community.entity.CommunityPosts;

public interface CommunityPostRepository extends JpaRepository<CommunityPosts, Long> {
}