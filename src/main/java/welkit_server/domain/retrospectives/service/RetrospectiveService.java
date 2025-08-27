package welkit_server.domain.retrospectives.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.retrospectives.dto.request.RetrospectiveRequest;
import welkit_server.domain.retrospectives.dto.response.RetrospectiveResponse;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.repository.RetrospectiveRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;

    @Transactional
    public RetrospectiveResponse createRetrospective(RetrospectiveRequest createRetrospectiveRequest) {
        Retrospective retrospective = retrospectiveRepository.save(createRetrospectiveRequest.toEntity());
        return RetrospectiveResponse.fromEntity(retrospective);
    }

    @Transactional
    public RetrospectiveResponse editRetrospective(Long retrospectiveId, RetrospectiveRequest editRetrospectiveRequest) {
        Retrospective retrospective = findRetrospectiveById(retrospectiveId);
        retrospective.editRetrospective(editRetrospectiveRequest);
        return RetrospectiveResponse.fromEntity(retrospective);
    }

    @Transactional
    public void deleteRetrospective(Long retrospectiveId) {
        Retrospective retrospective = findRetrospectiveById(retrospectiveId);
        retrospectiveRepository.delete(retrospective);
    }

    public Retrospective findRetrospectiveById(Long retrospectiveId) {
        return retrospectiveRepository.findById(retrospectiveId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.RETROSPECTIVE_NOT_FOUND));
    }

}
