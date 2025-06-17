package civitas.common.mix.capabilityelementrevelation;

import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;
import lombok.NonNull;

public class VerifyMixCapabilityElementRevelation {

	@Use
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(@NonNull MixCapabilityElementRevelation that,
			@NonNull ElGamalPublicKey key, int fromIndex, int toIndex,
			@NonNull CapabilityMix fromMix, @NonNull CapabilityMix toMix) {
		ElGamalCiphertextish toCipher = toMix.capabilities[toIndex];

		ElGamalCiphertextish recipher = elGamalReencrypt.apply(key,
				fromMix.capabilities[fromIndex], that.reencryptFactor);
		return recipher.equals(toCipher);
	}

}
