package civitas.bboard.common;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import civitas.bboard.server.controllers.AnswerOrThrowable;
import civitas.bboard.server.controllers.EnvDependentAnswer;
import civitas.common.EnvironmentState;
import civitas.common.board.BulletinBoardTestData;

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
