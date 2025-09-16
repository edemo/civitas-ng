package civitas.common.mix.capabilitymix;

import civitas.crypto.ciphertext.ElGamalCiphertextish;

import java.util.Arrays;

public class AddEncryptedCapability {

	public void apply(CapabilityMix that, ElGamalCiphertextish v) {
		ElGamalCiphertextish[] caps = that.capabilities;

		ElGamalCiphertextish[] n = Arrays.copyOf(caps, caps.length + 1);
		n[caps.length] = v;

		that.capabilities = n;
	}

}
