package civitas.common.capabilityencryption;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Data;
import lombok.NonNull;

@Data
public class CapabilityEncryption {
	@NonNull
	public final ElGamalReencryptFactor factor;
	@NonNull
	public final ElGamalCiphertext encCap;
}