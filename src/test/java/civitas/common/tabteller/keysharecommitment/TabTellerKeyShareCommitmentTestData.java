package civitas.common.tabteller.keysharecommitment;

import civitas.common.tabteller.TabTellerKeyShareTestData;

public interface TabTellerKeyShareCommitmentTestData
		extends TabTellerKeyShareTestData {
	String KEY_SHARE_HASH = "jN6i6/0luyl1PR1FGg6usf56pV/IKpK//D3DFCXJ33g=";
	TabTellerKeyShareCommitment TAB_TELLER_KEY_SHARE_COMMITMENT = new TabTellerKeyShareCommitment(
			TELLER_INDEX, KEY_SHARE_HASH);

}
