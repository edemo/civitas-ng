package civitas.common.capabilityencryption;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;
import civitas.crypto.votecapability.VoteCapability;

@Controller
public class EncryptCapability {

	@Autowired
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	@Autowired
	ElGamalEncrypt elGamalEncrypt;

	public CapabilityEncryption apply(
			final ElGamalPublicKey key, final Map<String, VoteCapability> capabilities, final String desiredContext) {
		VoteCapability c = capabilities.get(desiredContext);
		if (c == null) {
			throw new IllegalArgumentException("No capability supplied for context " + desiredContext);
		}

		ElGamalReencryptFactor encCapFactor = generateElGamalReencryptFactor.apply(key.params);
		ElGamalCiphertextish encCap = elGamalEncrypt.apply(key, c, encCapFactor);

		return new CapabilityEncryption(encCapFactor, encCap);
	}
}
