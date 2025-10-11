package welkit_server.common.fixture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import welkit_server.domain.terms.entity.Term;
import welkit_server.domain.terms.entity.TermCategory;
import welkit_server.domain.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TermFixture {

    public static Term term(Long id, String name, String definition , TermCategory category, User user) {
        return Term.builder()
                .id(id)
                .name(name)
                .definition(definition)
                .category(category)
                .user(user)
                .build();
    }

}
