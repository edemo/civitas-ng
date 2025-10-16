package civitas.common.electoralroll;

import civitas.common.VoterEncCapabilitySharesTestData;
import civitas.crypto.signature.Signature;

public interface ElectoralRollCapabilitySharesTestData extends VoterEncCapabilitySharesTestData {
	String META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES = "electoralRollCapShares:teller:1:voterBlock:3";

	ElectoralRollCapabilityShares ELECTORAL_ROLL_CAPABILITY_SHARES =
			new ElectoralRollCapabilityShares(VOTER_ENC_CAPABILITY_SHARES, TELLER_INDEX, BOARD_INDEX);
	Signature SIGNATURE_ELECTORAL_ROLL_CAPABILITY_SHARES =
			new Signature("sigelecrollcapshares".getBytes(), PUBLIC_KEY_BASE64);
	Signature SIGNATURE_ELECTORAL_ROLL_CAPABILITY_SHARES_BAD =
			new Signature("sigelecrollcapshares".getBytes(), PUBLIC_KEY2_BASE64);

	String ELECTORAL_ROLL_CAPABILITY_SHARES_XML = "mock value for ELECTORAL_ROLL_CAPABILITY_SHARES_XML";
	byte[] ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH = "elecrollcapsharexmlhash".getBytes();
}
