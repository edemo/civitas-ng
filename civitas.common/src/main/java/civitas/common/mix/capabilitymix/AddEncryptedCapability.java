package civitas.common.mix.capabilitymix;

import civitas.crypto.ciphertext.ElGamalCiphertextish;

public class AddEncryptedCapability {

	public void apply(CapabilityMix that, ElGamalCiphertextish v) {
		ElGamalCiphertextish[] caps = that.capabilities;

		ElGamalCiphertextish[] n = new ElGamalCiphertextish[caps.length + 1];
		for (int i = 0; i < caps.length; i++) {
			n[i] = caps[i];
		}

		n[that.capabilities.length] = v;
		that.capabilities = n;
	}

}
