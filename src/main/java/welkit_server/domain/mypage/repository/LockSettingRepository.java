package welkit_server.domain.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;

@Repository
public interface LockSettingRepository extends JpaRepository<LockSetting, Long> {

    LockSetting findByUserIdAndFeatureName(Long userId, FeatureName featureName);

}
