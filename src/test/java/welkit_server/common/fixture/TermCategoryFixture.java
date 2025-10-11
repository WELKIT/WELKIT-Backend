package welkit_server.common.fixture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermCategoryFixture {
    public static TermCategory termCategory(Long id, String name, User user) {
        return TermCategory.builder()
                .id(id)
                .name(name)
                .user(user)
                .build();
    }

}
