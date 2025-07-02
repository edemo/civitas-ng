package civitas.common.mix.capabilitymix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.mix.confirmation.MixConfirmationTestData;

class GetMixConfirmationMetaTest extends TestBase
		implements MixConfirmationTestData {

	@InjectMocks
	GetMixConfirmationMeta getMixConfirmationMeta;

	@Test
	@DisplayName("if vote mix, the format is 'mixConfirm:vote:<speakerindex>:<tellerindex>'")
	void test() {
		assertEquals(MIX_CONFIRM_VOTE_META,
				getMixConfirmationMeta.apply(true, 1, 2));
	}

	@Test
	@DisplayName("if not vote mix, the format is 'mixConfirm:elecRoll:<speakerindex>:<tellerindex>'")
	void test1() {
		assertEquals(MIX_CONFIRM_ROLL_META,
				getMixConfirmationMeta.apply(false, 1, 2));
	}

}
