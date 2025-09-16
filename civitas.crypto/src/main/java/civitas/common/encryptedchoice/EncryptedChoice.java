package civitas.common.encryptedchoice;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.Data;
import lombok.NonNull;

@Data
public class EncryptedChoice {
	@NonNull
	public final ElGamalReencryptFactor factor;
	@NonNull
	public final ElGamal1OfLReencryption encChoice;
}