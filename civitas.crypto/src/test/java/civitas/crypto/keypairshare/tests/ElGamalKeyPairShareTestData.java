package civitas.crypto.keypairshare.tests;

import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.publickey.tests.ElGamalPublicKeyTestData;

public interface ElGamalKeyPairShareTestData extends ElGamalPublicKeyTestData {
	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE =
			new ElGamalKeyPairShare(EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PRIVATE_KEY_E);

	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE_BAD =
			new ElGamalKeyPairShare(EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_EPRIME, EL_GAMAL_PRIVATE_KEY_EPRIME);
}
