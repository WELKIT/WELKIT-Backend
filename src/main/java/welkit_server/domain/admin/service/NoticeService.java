package welkit_server.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.admin.dto.request.NoticeAdminRequest;
import welkit_server.domain.admin.dto.response.NoticeAdminResponse;
import welkit_server.domain.admin.dto.response.GetAllNoticeResponse;
import welkit_server.domain.admin.dto.response.AdminPageInfoResponse;
import welkit_server.domain.admin.dto.response.NoticeResponse;
import welkit_server.domain.admin.entity.Notice;
import welkit_server.domain.admin.repository.NoticeRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public NoticeResponse getAllNotices(int page, int size, Authentication authentication) {
        getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page,size);
        Page<Notice> notices = noticeRepository.findAllByOrderByLastModifiedDateDesc(pageable);

        List<GetAllNoticeResponse> getAllNotices =  notices.getContent()
                .stream()
                .map(notice -> GetAllNoticeResponse.fromEntity(notice))
                .toList();

        AdminPageInfoResponse noticePageInfo = AdminPageInfoResponse.builder()
                .totalPages(notices.getTotalPages())
                .totalElements(notices.getTotalElements())
                .build();

        return NoticeResponse.builder()
                .noticePageInfo(noticePageInfo)
                .notices(getAllNotices)
                .build();
    }

    public NoticeAdminResponse getNotice(Long noticeId, Authentication authentication) {
        getAuthenticatedUser(authentication);

        Notice notice = findNoticeById(noticeId);

        return NoticeAdminResponse.fromEntity(notice);
    }

    @Transactional
    public NoticeAdminResponse createNotice(NoticeAdminRequest createNoticeRequest, Authentication authentication) {
        getAuthenticatedUser(authentication);

        Notice notice = createNoticeRequest.toEntity();
        noticeRepository.save(notice);

        return NoticeAdminResponse.fromEntity(notice);
    }

    @Transactional
    public NoticeAdminResponse updateNotice(Long noticeId, NoticeAdminRequest updateNoticeRequest, Authentication authentication) {
        getAuthenticatedUser(authentication);

        Notice notice = findNoticeById(noticeId);
        notice.editNotice(updateNoticeRequest);

        return NoticeAdminResponse.fromEntity(notice);
    }

    @Transactional
    public void deleteNotice(Long noticeId, Authentication authentication) {
        getAuthenticatedUser(authentication);

        Notice notice = findNoticeById(noticeId);
        noticeRepository.delete(notice);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

    public Notice findNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.MYP_NOTICE_NOT_FOUND));
    }

}
