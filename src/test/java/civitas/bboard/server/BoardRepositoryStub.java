package civitas.bboard.server;

import static org.mockito.Mockito.mock;

class BoardRepositoryStub {
	public static BoardRepository stub() {
		BoardRepository mock = mock(BoardRepository.class);
		return mock;
	}
}
