package civitas.bboard.common.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.controllers.tests.AnswerOrThrowable;
import civitas.bboard.server.controllers.tests.EnvDependentAnswer;
import civitas.common.board.tests.BulletinBoardTestData;
import civitas.common.tests.EnvironmentState;

public class BBPostRepositoryStub implements BulletinBoardTestData, BBPostTestData {
	public static BBPostRepository stub() {
		BBPostRepository mock = mock(BBPostRepository.class);
		when(mock.findByBbidOrderBySerialDesc(BULLETIN_BOARD_ID))
				.thenAnswer(new EnvDependentAnswer<>(Map.of(
						EnvironmentState.NORMAL,
						new AnswerOrThrowable<>(null, BBPOSTS),
						EnvironmentState.EMPTY_BOARD,
						new AnswerOrThrowable<>(null, List.of()))));
		return mock;
	}
}
