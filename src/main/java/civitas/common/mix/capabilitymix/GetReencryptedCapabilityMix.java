package civitas.common.mix.capabilitymix;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.Use;

public class GetReencryptedCapabilityMix {
	@Use
	ElGamalReencrypt elGamalReencrypt;

	public ElGamalCiphertextish apply(CapabilityMix that, int i,
			ElGamalReencryptFactor factor, ElGamalPublicKey key) {
		return elGamalReencrypt.apply(key, that.capabilities[i], factor);
	}

}
