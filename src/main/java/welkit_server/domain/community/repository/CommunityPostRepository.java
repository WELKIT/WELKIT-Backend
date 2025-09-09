package welkit_server.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.user.model.JobRole;

public interface CommunityPostRepository extends JpaRepository<CommunityPosts, Long> {
    Page<CommunityPosts> findAllByJobRole(JobRole jobRole, Pageable pageable);
}