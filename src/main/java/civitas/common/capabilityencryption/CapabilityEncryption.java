package civitas.common.capabilityencryption;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Data;
import lombok.NonNull;

@Data
public class CapabilityEncryption {
	@NonNull
	public final ElGamalReencryptFactor factor;
	@NonNull
	public final ElGamalCiphertextish encCap;
}