package civitas.common.mix.capabilitymix;

import java.util.Arrays;

import civitas.crypto.ciphertext.ElGamalCiphertextish;

public class AddEncryptedCapability {

	public void apply(final CapabilityMix that, final ElGamalCiphertextish v) {
		ElGamalCiphertextish[] caps = that.capabilities;

		ElGamalCiphertextish[] n = Arrays.copyOf(caps, caps.length + 1, ElGamalCiphertextish[].class);
		n[caps.length] = v;

		that.capabilities = n;
	}
}
