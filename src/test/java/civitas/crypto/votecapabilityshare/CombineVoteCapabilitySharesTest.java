package civitas.crypto.votecapabilityshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.votecapability.VoteCapability;
import civitas.crypto.votecapability.VoteCapabilityC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class CombineVoteCapabilitySharesTest extends TestBase
		implements VoteCapabilityShareTestData {

	@Tested
	CombineVoteCapabilityShares combineVoteCapabilityShares;

	@Test
	@DisplayName("multiplies each row of a matrix of vote capability shares")
	void test() {
		VoteCapability[] actual = combineVoteCapabilityShares
				.apply(CAPABILITY_SHARE_MATRIX, EL_GAMAL_PARAMETERS);
		assertEquals(CivitasBigInteger.valueOf(2 * 5),
				((VoteCapabilityC) actual[0]).m);
		assertEquals(CivitasBigInteger.valueOf(3 * 7),
				((VoteCapabilityC) actual[1]).m);
	}

	@Test
	@DisplayName("returns null if the matrix is null")
	void test1() {
		assertEquals(null,
				combineVoteCapabilityShares.apply(null, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("returns null if the matrix contains null")
	void test2() {
		assertEquals(null, combineVoteCapabilityShares
				.apply(new VoteCapabilityShare[][] { null }, EL_GAMAL_PARAMETERS));
	}

}
