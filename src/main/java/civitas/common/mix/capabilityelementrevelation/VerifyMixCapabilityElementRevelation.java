package civitas.common.mix.capabilityelementrevelation;

import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.crypto.ciphertext.ElGamalCiphertext;
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
		ElGamalCiphertext fromCipher = fromMix.capabilities[fromIndex];
		ElGamalCiphertext toCipher = toMix.capabilities[toIndex];

		ElGamalCiphertext recipher = elGamalReencrypt.apply(key, fromCipher,
				that.reencryptFactor);
		return recipher.equals(toCipher);
	}

}
