package welkit_server.domain.retrospectives.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.retrospectives.dto.request.RetrospectiveRequest;
import welkit_server.domain.retrospectives.dto.response.GetAllRetrospectiveResponse;
import welkit_server.domain.retrospectives.dto.response.RetrospectiveResponse;
import welkit_server.domain.retrospectives.entity.Retrospective;
import welkit_server.domain.retrospectives.model.Type;
import welkit_server.domain.retrospectives.repository.RetrospectiveRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrospectiveService {

    private final RetrospectiveRepository retrospectiveRepository;

    public List<GetAllRetrospectiveResponse> getAllRetrospectives(Type type, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Retrospective> retrospectives = retrospectiveRepository.findAllRetrospectives(type, pageable);

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

    public RetrospectiveResponse getRetrospectiveById(Long retrospectiveId) {
        Retrospective retrospective = findRetrospectiveById(retrospectiveId);
        return RetrospectiveResponse.fromEntity(retrospective);
    }

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
