package welkit_server.domain.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;

@Repository
public interface LockSettingRepository extends JpaRepository<LockSetting, Long> {

    @Query("SELECT l FROM LockSetting l WHERE l.user.id = :userId AND l.featureName = :featureName")
    LockSetting findIsLockedByUserIdAndFeatureName(@Param("userId") Long userId, @Param("featureName") FeatureName featureName);

}
