package welkit_server.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.admin.dto.response.*;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.repository.CommunityPostRepository;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityManagementService {

    private final CommunityPostRepository communityPostRepository;
    private final UserService userService;

    public CommunityManagementPostResponse getAllSanctionPosts(int page, int size, Authentication authentication) {
        userService.getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page,size);
        Page<CommunityPosts> communityPosts = communityPostRepository.findSanctionPosts(pageable);

        List<GetAllCommunityManagementPostResponse> getAllCommunityManagementPosts = communityPosts.getContent()
                .stream()
                .map(communityPost -> GetAllCommunityManagementPostResponse.fromEntity(communityPost))
                .toList();

        AdminPageInfoResponse communityManagementPostPageInfo = AdminPageInfoResponse.builder()
                .totalPages(communityPosts.getTotalPages())
                .totalElements(communityPosts.getTotalElements())
                .build();

        return CommunityManagementPostResponse.builder()
                .communityManagementPostInfo(communityManagementPostPageInfo)
                .communityManagementPostResponses(getAllCommunityManagementPosts)
                .build();


    }

    public CommunityManagementPostDetailResponse getSanctionPostById(Long postId,Authentication authentication) {
        userService.getAuthenticatedUser(authentication);

        CommunityPosts getSectionPost = communityPostRepository.findSanctionPostById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        return CommunityManagementPostDetailResponse.fromEntity(getSectionPost);
    }

    @Transactional
    public void deleteSanctionPostById(Long postId, Authentication authentication) {
        userService.getAuthenticatedUser(authentication);

        CommunityPosts getSectionPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));
        communityPostRepository.delete(getSectionPost);
    }

}
