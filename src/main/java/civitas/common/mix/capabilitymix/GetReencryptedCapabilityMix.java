package civitas.common.mix.capabilitymix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;

@Service
public class GetReencryptedCapabilityMix {
	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public ElGamalCiphertextish apply(CapabilityMix that, int i,
			ElGamalReencryptFactor factor, ElGamalPublicKey key) {
		return elGamalReencrypt.apply(key, that.capabilities[i], factor);
	}

}
