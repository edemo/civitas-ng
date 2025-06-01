package civitas.crypto.keypairshare;

import civitas.crypto.publickey.ElGamalPublicKeyTestData;

public interface ElGamalKeyPairShareTestData extends ElGamalPublicKeyTestData {
	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE = new ElGamalKeyPairShare(
			EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PRIVATE_KEY_E);

	ElGamalKeyPairShare EL_GAMAL_KEYPAIR_SHARE_BAD = new ElGamalKeyPairShare(
			EL_GAMAL_PARAMETERS, EL_GAMAL_PUBLIC_KEY_EPRIME,
			EL_GAMAL_PRIVATE_KEY_EPRIME);

	String EL_GAMAL_KEYPAIR_SHARE_XML = "<elGamalKeyPairShare>"
			+ EL_GAMAL_PUBLIC_KEY_E_XML + EL_GAMAL_PRIVATE_KEY_E_XML
			+ "</elGamalKeyPairShare>";
}
