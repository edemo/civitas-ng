package civitas.common.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import civitas.common.CheckAccess;
import civitas.common.CommonConstants;
import civitas.common.Operation;
import civitas.common.board.tests.BoardClosedContentCommitmentTestData;
import civitas.crypto.signature.tests.SignatureTestData;

public class CheckAccessStub implements BoardClosedContentCommitmentTestData, SignatureTestData, CommonConstants {
	public static CheckAccess stub() {
		CheckAccess mock = mock(CheckAccess.class);
		doThrow(new SecurityException()).when(mock).apply(any(), any(), any());
		doNothing()
				.when(mock)
				.apply(Operation.POST, PUBLIC_KEY_BASE64, BOARD_CLOSED_CONTENT_COMMITMENT_META + BULLETIN_BOARD_ID);
		doNothing()
				.when(mock)
				.apply(Operation.POST, PUBLIC_KEY2_BASE64, BOARD_CLOSED_CONTENT_COMMITMENT_META + BULLETIN_BOARD_ID);
		return mock;
	}
}
