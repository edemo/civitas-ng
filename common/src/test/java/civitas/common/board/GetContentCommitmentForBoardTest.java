package civitas.common.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;

class GetContentCommitmentForBoardTest extends TestBase implements
		BoardsForTabulationTestData, BoardClosedContentCommitmentTestData {

	@InjectMocks
	GetContentCommitmentForBoard getContentCommitmentForBoard;

	@Test
	@DisplayName("chooses the closed content commitment for the board with the right index")
	void test() {
		assertEquals(BOARD_CLOSED_CONTENT_COMMITMENTS.get(2),
				getContentCommitmentForBoard.apply(BOARDS_FOR_TABULATION, "board1"));
	}

	@Test
	@DisplayName("if there is no commitment with the board index, null is returned ")
	void test1() {
		assertEquals(null,
				getContentCommitmentForBoard.apply(BOARDS_FOR_TABULATION, "board3"));
	}

	@Test
	@DisplayName("if there is a null in the boards, NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class, () -> getContentCommitmentForBoard
				.apply(BOARDS_FOR_TABULATION_WITH_NULL, "board1"));
	}

	@Test
	@DisplayName("if the boards is null, NullPointerException is thrown")
	void test3() {
		assertThrows(NullPointerException.class,
				() -> getContentCommitmentForBoard.apply(null, "board1"));
	}

}
