package civitas.common.mix.capabilitymix;

import java.util.Arrays;

import civitas.crypto.ciphertext.ElGamalCiphertext;

public class AddEncryptedCapability {

	public void apply(CapabilityMix that, ElGamalCiphertext v) {
		ElGamalCiphertext[] caps = that.capabilities;
		ElGamalCiphertext[] n = Arrays.copyOf(caps, caps.length + 1);
		n[that.capabilities.length] = v;
		that.capabilities = n;
	}

}
