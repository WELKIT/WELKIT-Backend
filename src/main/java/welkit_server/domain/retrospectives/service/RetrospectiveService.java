package welkit_server.domain.retrospectives.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.retrospectives.dto.request.RetrospectiveRequest;
import welkit_server.domain.retrospectives.dto.response.GetAllRetrospectiveResponse;
import welkit_server.domain.retrospectives.dto.response.RetrospectiveResponse;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;
import welkit_server.domain.retrospectives.repository.RetrospectiveRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.ForbiddenException;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.exception.model.UnauthorizedException;
import welkit_server.global.security.dto.CustomUserDetails;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;
    private final UserRepository userRepository;

    public List<GetAllRetrospectiveResponse> getAllRetrospectives(Type type, int page, int size, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);
        Pageable pageable = PageRequest.of(page, size);

        Page<Retrospective> retrospectives =
                retrospectiveRepository.findAllRetrospectives(currentUser, type, pageable);

        return retrospectives.getContent().stream()
                .map(retrospective -> GetAllRetrospectiveResponse.builder()
                        .retrospectiveId(retrospective.getId())
                        .title(retrospective.getTitle())
                        .type(retrospective.getType())
                        .startDate(retrospective.getStartDate())
                        .endDate(retrospective.getEndDate())
                        .createdAt(retrospective.getCreatedDate())
                        .build())
                .toList();
    }

    public RetrospectiveResponse getRetrospectiveById(Long retrospectiveId, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);
        return RetrospectiveResponse.fromEntity(findOwnedRetrospective(userId, retrospectiveId));
    }

    @Transactional
    public RetrospectiveResponse createRetrospective(RetrospectiveRequest createRetrospectiveRequest, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        Retrospective retrospective = createRetrospectiveRequest.toEntity();
        retrospective.setUser(user);

        retrospectiveRepository.save(retrospective);

        return RetrospectiveResponse.fromEntity(retrospective);
    }

    @Transactional
    public RetrospectiveResponse editRetrospective(Long retrospectiveId, RetrospectiveRequest editRetrospectiveRequest, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        Retrospective retrospective  = findOwnedRetrospective(userId, retrospectiveId);
        retrospective.editRetrospective(editRetrospectiveRequest);

        return RetrospectiveResponse.fromEntity(retrospective);
    }

    @Transactional
    public void deleteRetrospective(Long retrospectiveId, Authentication authentication) {
        Long userId = getAuthenticatedUserId(authentication);

        Retrospective retrospective = findOwnedRetrospective(userId,retrospectiveId);

        retrospectiveRepository.delete(retrospective);
    }

    public Long getAuthenticatedUserId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getUserId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        return userRepository.findById(getAuthenticatedUserId(authentication))
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.SESSION_EXPIRED));
    }

    public Retrospective findRetrospectiveById(Long retrospectiveId) {
        return retrospectiveRepository.findById(retrospectiveId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.RETROSPECTIVE_NOT_FOUND));
    }

    private void validateOwnership(Long currentUserId, Retrospective retrospective) {
        if (!retrospective.getUser().getId().equals(currentUserId)) {
            throw new ForbiddenException(ErrorMessage.WK_NO_PERMISSION);
        }
    }

    public Retrospective findOwnedRetrospective(Long userId, Long retrospectiveId) {
        Retrospective retrospective = findRetrospectiveById(retrospectiveId);
        validateOwnership(userId, retrospective);
        return retrospective;
    }


}
