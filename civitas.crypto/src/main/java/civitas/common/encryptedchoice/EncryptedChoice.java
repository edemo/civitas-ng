package civitas.common.encryptedchoice;

import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.NonNull;

public record EncryptedChoice(@NonNull ElGamalReencryptFactor factor, @NonNull ElGamal1OfLReencryption encChoice) {}
