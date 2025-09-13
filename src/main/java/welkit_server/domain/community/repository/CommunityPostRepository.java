package welkit_server.domain.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import java.util.List;
import java.util.Optional;

public interface CommunityPostRepository extends JpaRepository<CommunityPosts, Long> {
    Page<CommunityPosts> findAllByJobRole(JobRole jobRole, Pageable pageable);

    @Query("""
    SELECT p
    FROM CommunityPosts p
    WHERE p.user = :user
      AND (:jobRole IS NULL OR p.jobRole = :jobRole)
""")
    List<CommunityPosts> findByUserAndOptionalJobRole(
            @Param("user") User user,
            @Param("jobRole") JobRole jobRole,
            Pageable pageable
    );

    @Query("SELECT DISTINCT p FROM CommunityPosts p " +
            "JOIN p.comments c " +
            "WHERE c.user = :user " +
            "AND (:jobRole IS NULL OR p.jobRole = :jobRole)")
    List<CommunityPosts> findDistinctByComments_UserAndOptionalJobRole(
            @Param("user") User user,
            @Param("jobRole") JobRole jobRole,
            Pageable pageable
    );

    @Query("""
    SELECT p FROM CommunityPosts p
    WHERE (:keyword IS NULL OR :keyword = '' 
       OR LOWER(p.title) LIKE LOWER(CONCAT('%', COALESCE(:keyword, ''), '%'))
       OR LOWER(p.content) LIKE LOWER(CONCAT('%', COALESCE(:keyword, ''), '%')))
    """)
    Page<CommunityPosts> searchPostsOptionalKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("""
    SELECT p
    FROM CommunityPosts p
    JOIN p.feedbacks f
    WHERE f.isHelpful = false
    GROUP BY p
    HAVING COUNT(f) >= 10
    ORDER BY p.createdDate DESC
""")
    Page<CommunityPosts> findSanctionPosts(Pageable pageable);

    @Query("""
    SELECT p
    FROM CommunityPosts p
    JOIN p.feedbacks f
    WHERE f.isHelpful = false
    AND p.id = :postId
    GROUP BY p
    HAVING COUNT(f) >= 10
""")
    Optional<CommunityPosts> findSanctionPostById(@Param("postId") Long postId);

}
