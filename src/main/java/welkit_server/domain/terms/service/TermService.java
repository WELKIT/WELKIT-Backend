package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.EditTermResponse;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

    private final TermRepository termRepository;
    private final TermCategoryService termCategoryService;

    @Transactional
    public CreateTermResponse createTerm(CreateTermRequest createTermRequest) {
        TermCategory category = termCategoryService.findOrCreate(createTermRequest.getCategoryId(), createTermRequest.getCategoryName());
        Term term = termRepository.save(createTermRequest.toEntity(category));
        return CreateTermResponse.of(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                category.getId(),
                category.getName()
        );
    }

    @Transactional
    public EditTermResponse editTerm(Long termId, EditTermRequest editTermRequest) {
        Term term = getTermById(termId);
        TermCategory category = termCategoryService.findOrCreate(editTermRequest.getCategoryId(), editTermRequest.getCategoryName());
        term.editTerm(editTermRequest, category);
        return EditTermResponse.of(
                term.getId(),
                term.getName(),
                term.getDefinition(),
                term.getCategory().getId()
        );
    }

    @Transactional
    public void deleteTerm(Long termId) {
        Term term = getTermById(termId);
        termRepository.delete(term);
    }

    public Term getTermById(Long termId) {
        return termRepository.findById(termId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.TERM_NOT_FOUND));
    }
}
