package civitas.crypto.oneoflreencryption.tests;

import java.util.Map;

import civitas.common.VoteChoice;
import civitas.common.tests.ConstructTestData;
import civitas.common.tests.VoteChoiceTestData;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proof1ofl.tests.ElGamalProof1OfLTestData;

public interface ElGamal1OfLReencryptionTestData extends VoteChoiceTestData, ElGamalProof1OfLTestData {

	ElGamal1OfLReencryption EL_GAMAL_1_OF_L_REENCRYPTION =
			new ElGamal1OfLReencryption(REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	Map<VoteChoice, ElGamal1OfLReencryption> EL_GAMAL_1_OF_L_REENCRYPTION_MAP = ConstructTestData.constructTestData(
			CHOICES,
			choice -> new ElGamal1OfLReencryption(
					REENCRYPTED_CHOICE_MAP.get(choice), EL_GAMAL_PROOF_1_OF_L_MAP.get(choice)));
}
