package welkit_server.domain.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import welkit_server.domain.community.entity.CommunityFeedBack;
import welkit_server.domain.community.model.TargetType;
import welkit_server.domain.user.entity.User;

import java.util.Optional;

public interface CommunityFeedBackRepository extends JpaRepository<CommunityFeedBack, Long> {
    Optional<CommunityFeedBack> findByTargetTypeAndTargetIdAndUser(TargetType targetType, Long targetId, User user);
    int countByTargetTypeAndTargetIdAndIsHelpful(TargetType targetType, Long targetId, Boolean isHelpful);
}
