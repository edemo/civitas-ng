package civitas.common.capabilityencryption;

import java.util.Map;

import civitas.crypto.CryptoError;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.Use;

public class EncryptCapability {

	@Use
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;
	@Use
	ElGamalEncrypt elGamalEncrypt;

	public CapabilityEncryption apply(ElGamalPublicKey key,
			Map<String, VoteCapability> capabilities, String desiredContext)
			throws CryptoError {
		VoteCapability c = null;
		c = capabilities.get(desiredContext);
		if (c == null) {
			throw new IllegalArgumentException(
					"No capability supplied for context " + desiredContext);
		}

		ElGamalReencryptFactor encCapFactor = generateElGamalReencryptFactor
				.apply(key.params);
		ElGamalCiphertext encCap = elGamalEncrypt.apply(key, c, encCapFactor);

		return new CapabilityEncryption(encCapFactor, encCap);
	}

}
