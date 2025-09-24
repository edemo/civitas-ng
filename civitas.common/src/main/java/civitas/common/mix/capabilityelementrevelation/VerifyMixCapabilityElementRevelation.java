package civitas.common.mix.capabilityelementrevelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import jakarta.annotation.Nonnull;

@Controller
public class VerifyMixCapabilityElementRevelation {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(
			@Nonnull final MixCapabilityElementRevelation that,
			@Nonnull final ElGamalPublicKey key,
			final int fromIndex,
			final int toIndex,
			@Nonnull final CapabilityMix fromMix,
			@Nonnull final CapabilityMix toMix) {
		ElGamalCiphertextish toCipher = toMix.capabilities[toIndex];

		ElGamalCiphertextish recipher =
				elGamalReencrypt.apply(key, fromMix.capabilities[fromIndex], that.reencryptFactor);
		return recipher.equals(toCipher);
	}
}
