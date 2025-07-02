package civitas.crypto.oneoflreencryption;

import java.util.Map;

import civitas.common.ConstructTestData;
import civitas.common.VoteChoice;
import civitas.comon.VoteChoiceTestData;
import civitas.crypto.proof1ofl.ElGamalProof1OfLTestData;

public interface ElGamal1OfLReencryptionTestData
		extends VoteChoiceTestData, ElGamalProof1OfLTestData {

	public static final ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION = new ElGamal1OfLReencryption(
			REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	Map<VoteChoice, ElGamal1OfLReencryption> EL_GAMAL_1_OF_L_REENCRYPTION_MAP = ConstructTestData
			.constructTestData(CHOICES,
					(choice) -> new ElGamal1OfLReencryption(
							REENCRYPTED_CHOICE_MAP.get(choice),
							EL_GAMAL_PROOF_1_OF_L_MAP.get(choice)));

}
