package welkit_server.service.insightcard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import welkit_server.common.fixture.InsightCardFixture;
import welkit_server.common.fixture.UserFixture;
import welkit_server.domain.insightcard.dto.response.GetInsightCardResponse;
import welkit_server.domain.insightcard.entity.InsightCard;
import welkit_server.domain.insightcard.model.CardType;
import welkit_server.domain.insightcard.repository.InsightCardRepository;
import welkit_server.domain.insightcard.service.InsightCardService;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;
import welkit_server.domain.user.service.UserService;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class InsightCardServiceTest {

	@InjectMocks
	private InsightCardService insightCardService;

	@Mock
	UserService userService;

	@Mock
	InsightCardRepository insightCardRepository;

	@Mock
	private Authentication authentication;

	private User user;
	private InsightCard insightCard;

	@BeforeEach
	void setUp() {
		user = UserFixture.user(
			1L,
			"test@test.com",
			"qwer1234",
			JobRole.AI_DEVELOP_DATA,
			UserType.USER
		);
		insightCard = InsightCardFixture.insightCard(
			1L,
			"이수민 데이터 엔지니어",
			"대규모 데이터 파이프라인 설계 및 운영",
			"Hadoop, Spark, Airflow 기반 데이터 처리 경험.",
			CardType.PERSON,
			true,
			user
		);
	}

	@Test
	@DisplayName("인사이트 카드에서 전체 인물카드를 조회한다")
	void getAllInsightCards() {

		Pageable pageable = PageRequest.of(0, 10);
		Page<InsightCard> insightCards = new PageImpl<>(List.of(insightCard), pageable, 1);

		given(userService.getAuthenticatedUser(authentication)).willReturn(user);
		given(insightCardRepository.findAllInsightPersonCards(user, pageable)).willReturn(insightCards);
		given(insightCardRepository.countByUserAndType(user, CardType.PERSON)).willReturn(1L);

		//when
		GetInsightCardResponse response = insightCardService.getAllInsightPersonCards(0,10,authentication);

		//then
		assertThat(response).isNotNull();
		assertThat(response.getTotalAmount()).isEqualTo(1);
		assertThat(response.getCards()).hasSize(1);
		assertThat(response.getCards().get(0).getTitle()).isEqualTo("이수민 데이터 엔지니어");
		assertThat(response.getCards().get(0).getDescription()).isEqualTo("대규모 데이터 파이프라인 설계 및 운영");
		assertThat(response.getCards().get(0).getType()).isEqualTo(CardType.PERSON);

	}
}
