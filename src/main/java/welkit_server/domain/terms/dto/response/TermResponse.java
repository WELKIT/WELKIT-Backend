package welkit_server.domain.terms.dto.response;

public record TermResponse(
        Long termId,
        String name,
        String definition,
        Long categoryId,
        String categoryName
) {}
