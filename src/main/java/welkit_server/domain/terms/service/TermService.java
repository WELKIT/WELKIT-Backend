package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.response.TermResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.terms.repository.TermRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class TermService {

    private final TermCategoryRepository termCategoryRepository;
    private final TermRepository termRepository;

    public TermResponse createTerm(CreateTermRequest createTermRequest) {

        TermCategory category = termCategoryRepository.findTermCategoryById(createTermRequest.getCategoryId())
                .orElseGet( () -> {
                   TermCategory newCategory = TermCategory.builder()
                           .name(createTermRequest.getName())
                           .build();
                   return termCategoryRepository.save(newCategory);
                });

        Term term = createTermRequest.toEntity(category);
        Term savedTerm = termRepository.save(term);

        return new TermResponse(
                savedTerm.getId(),
                savedTerm.getName(),
                savedTerm.getDefinition(),
                category.getId(),
                category.getName()
        );
    }


}
