package civitas.common.capabilityencryption;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.NonNull;

public record CapabilityEncryption(@NonNull ElGamalReencryptFactor factor, @NonNull ElGamalCiphertextish encCap) {}
