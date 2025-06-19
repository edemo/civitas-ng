package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.Constants;
import civitas.crypto.msg.ElGamalMsg;

class IsPetResultTest extends TestBase implements Constants {

	@InjectMocks
	IsPetResult isPetResult;

	@Test
	@DisplayName("it is pet result, if it equals one")
	void test() {
		assertTrue(isPetResult.apply(new ElGamalMsg(ONE)));
	}

	@Test
	@DisplayName("not pet result, if it does not equal one")
	void test1() {
		assertFalse(isPetResult.apply(new ElGamalMsg(TWO)));
	}

}
