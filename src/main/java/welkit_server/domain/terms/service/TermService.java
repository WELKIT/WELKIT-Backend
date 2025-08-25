package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.dto.request.CreateTermRequest;
import welkit_server.domain.terms.dto.request.EditTermRequest;
import welkit_server.domain.terms.dto.response.EditTermResponse;
import welkit_server.domain.terms.dto.response.CreateTermResponse;
import welkit_server.domain.terms.dto.response.GetAllTermResponse;
import welkit_server.domain.terms.dto.response.GetCategoryTermResponse;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;
import welkit_server.domain.terms.repository.TermRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.NotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermService {

    private final TermRepository termRepository;
    private final TermCategoryRepository termCategoryRepository;
    private final TermCategoryService termCategoryService;

    public List<GetAllTermResponse> getTerms(){
        List<Term> termList = termRepository.findAllTerms();
        return termList.stream()
                .map(term -> GetAllTermResponse.builder()
                        .termId(term.getId())
                        .name(term.getName())
                        .definition(term.getDefinition())
                        .categoryId(term.getCategory().getId())
                        .updatedAt(term.getLastModifiedDate())
                        .build())
                .toList();
    }

    public List<GetCategoryTermResponse> getCategoryTerms(Long categoryId){
        if(termCategoryRepository.findTermCategoryById(categoryId).isEmpty()) {
            throw new BadRequestException(ErrorMessage.WK_ENUM_VALUE_BAD_REQUEST);
        }
        List<Term> sortedCategoryTerms = termRepository.findAllTermsByCategoryId(categoryId);
        return sortedCategoryTerms.stream()
                .map(term -> GetCategoryTermResponse.builder()
                        .termId(term.getId())
                        .name(term.getName())
                        .definition(term.getDefinition())
                        .categoryId(term.getCategory().getId())
                        .build())
                .toList();
    }

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
