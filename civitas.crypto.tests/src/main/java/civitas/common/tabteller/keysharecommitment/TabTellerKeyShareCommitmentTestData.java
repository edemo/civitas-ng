package civitas.common.tabteller.keysharecommitment;

import java.util.Base64;

import civitas.common.electionresults.TellerTestData;
import civitas.common.tabteller.TabTellerKeyShareTestData;

public interface TabTellerKeyShareCommitmentTestData extends TabTellerKeyShareTestData, TellerTestData {
	TabTellerKeyShareCommitment TAB_TELLER_KEY_SHARE_COMMITMENT = new TabTellerKeyShareCommitment(
			TELLER_INDEX, Base64.getEncoder().encodeToString(TAB_TELLER_KEY_SHARE_HASH));
}
