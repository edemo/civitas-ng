package civitas.bboard.server.tests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.bboard.server.GetBoardForId;
import civitas.common.board.tests.BulletinBoardTestData;

public class GetBoardForIdStub implements BulletinBoardTestData {
	public static GetBoardForId stub() {
		GetBoardForId mock = mock(GetBoardForId.class);
		when(mock.apply(BULLETIN_BOARD_ID, true)).thenReturn(BULLETIN_BOARD);
		return mock;
	}
}
