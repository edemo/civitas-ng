package civitas.crypto.votecapabilityshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigIntegerFactory;
import io.github.magwas.testing.TestBase;

class CombineVoteCapabilitySharesTest extends TestBase
		implements VoteCapabilityShareTestData {

	@InjectMocks
	CombineVoteCapabilityShares combineVoteCapabilityShares;

	@Test
	@DisplayName("multiplies each row of a matrix of vote capability shares")
	void test() {
		VoteCapability[] actual = combineVoteCapabilityShares
				.apply(CAPABILITY_SHARE_MATRIX, EL_GAMAL_PARAMETERS);
		assertEquals(CivitasBigIntegerFactory.obtain(2 * 5), actual[0].m);
		assertEquals(CivitasBigIntegerFactory.obtain(3 * 7), actual[1].m);
	}

	@Test
	@DisplayName("returns null if the matrix is null")
	void test1() {
        assertNull(combineVoteCapabilityShares.apply(null, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns null if the matrix contains null")
	void test2() {
		assertNull(combineVoteCapabilityShares
				.apply(new VoteCapabilityShare[][] { null }, EL_GAMAL_PARAMETERS));
	}

}
