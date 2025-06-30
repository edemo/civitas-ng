package civitas.bboard.server;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.board.BulletinBoardTestData;

class GetBoardForIdStub implements BulletinBoardTestData {
	public static GetBoardForId stub() {
		GetBoardForId mock = mock(GetBoardForId.class);
		when(mock.apply(BULLETIN_BOARD_ID, true)).thenReturn(BULLETIN_BOARD);
		return mock;
	}
}
