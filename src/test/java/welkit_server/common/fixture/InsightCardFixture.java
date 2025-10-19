package welkit_server.common.fixture;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;
import welkit_server.domain.user.entity.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InsightCardFixture {

	public static InsightCard insightCard(Long id, String title, String description, String note, CardType type,boolean isFavorite, User user) {
		return InsightCard.builder()
			.id(id)
			.title(title)
			.description(description)
			.note(note)
			.type(type)
			.isFavorite(isFavorite)
			.user(user)
			.build();
	}
}
