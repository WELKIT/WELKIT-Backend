package welkit_server.domain.admin.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.admin.dto.response.CommunityManagementPostDetailResponse;
import welkit_server.domain.admin.dto.response.CommunityManagementPostResponse;
import welkit_server.domain.community.entity.CommunityPosts;
import welkit_server.domain.community.repository.CommunityPostRepository;
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


    public List<CommunityManagementPostResponse> getAllSanctionPosts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<CommunityPosts> communityPosts = communityPostRepository.findSanctionPosts(pageable);

        return communityPosts.getContent().stream()
                .map(CommunityManagementPostResponse::fromEntity)
                .toList();
    }

    public CommunityManagementPostDetailResponse getSanctionPostById(Long postId) {

        CommunityPosts getSectionPost = communityPostRepository.findSanctionPostById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));

        return CommunityManagementPostDetailResponse.fromEntity(getSectionPost);
    }

    public void deleteSanctionPostById(Long postId) {
        CommunityPosts getSectionPost = communityPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.COMMUNITY_POST_NOT_FOUND));
        communityPostRepository.delete(getSectionPost);
    }

}
