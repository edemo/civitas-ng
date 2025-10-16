package civitas.bboard.server;

import static org.mockito.Mockito.mock;

public class BoardRepositoryStub {
	public static BoardRepository stub() {
		return mock(BoardRepository.class);
	}
}
