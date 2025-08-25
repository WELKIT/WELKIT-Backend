package welkit_server.domain.terms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.terms.repository.TermCategoryRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TermCategoryService {

    private final TermCategoryRepository termCategoryRepository;

    @Transactional
    public TermCategory findOrCreate(Long categoryId, String categoryName) {
        return termCategoryRepository.findTermCategoryById(categoryId)
                .orElseGet(() -> termCategoryRepository.save(
                        TermCategory.builder().name(categoryName).build()
                ));
    }

}
