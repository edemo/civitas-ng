package civitas.bboard.server.tests;

import static org.mockito.Mockito.mock;

import civitas.bboard.server.BoardRepository;

public class BoardRepositoryStub {
	public static BoardRepository stub() {
		return mock(BoardRepository.class);
	}
}
