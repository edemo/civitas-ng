package civitas.crypto.concrete;

import civitas.crypto.ElGamalKeyPairShare;

public interface ElGamalKeyPairShareTestData extends ElGamalPublicKeyCTestData {
	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE_NO_PROOF = new ElGamalKeyPairShare(
			EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_E, ELGAMAL_PRIVATE_KEY_E);

	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE_BAD_PROOF_GENERATED = new ElGamalKeyPairShare(
			EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_EPRIME,
			ELGAMAL_PRIVATE_KEY_EPRIME);

}
