package welkit_server.domain.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import welkit_server.domain.admin.entity.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Page<Notice> findAllByOrderByLastModifiedDateDesc(Pageable pageable);
}
