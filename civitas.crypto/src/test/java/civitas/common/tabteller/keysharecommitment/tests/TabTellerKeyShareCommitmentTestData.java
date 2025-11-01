package civitas.common.tabteller.keysharecommitment.tests;

import java.util.Base64;

import civitas.common.electionresults.tests.TellerTestData;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.common.tabteller.tests.TabTellerKeyShareTestData;

public interface TabTellerKeyShareCommitmentTestData extends TabTellerKeyShareTestData, TellerTestData {
	TabTellerKeyShareCommitment TAB_TELLER_KEY_SHARE_COMMITMENT = new TabTellerKeyShareCommitment(
			TELLER_INDEX, Base64.getEncoder().encodeToString(TAB_TELLER_KEY_SHARE_HASH));
}
