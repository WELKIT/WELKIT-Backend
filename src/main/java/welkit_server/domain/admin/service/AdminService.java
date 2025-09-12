package welkit_server.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.admin.dto.response.AdminMainPageResponse;
import welkit_server.domain.admin.dto.response.CommunityManagementPostResponse;
import welkit_server.domain.admin.dto.response.GetAllNoticeResponse;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final NoticeService noticeService;
    private final CommunityManagementService communityManagementService;
    private final UserRepository userRepository;

    public AdminMainPageResponse getAdminMainPage(int page, int size, Authentication authentication) {
        getAuthenticatedUser(authentication);

        List<GetAllNoticeResponse> notices = noticeService.getAllNotices(authentication);
        List<CommunityManagementPostResponse> sanctionPosts = communityManagementService.getAllSanctionPosts(page, size,authentication);

        return AdminMainPageResponse.builder()
                .noticeList(notices)
                .communityManagementPosts(sanctionPosts)
                .build();

    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

}
