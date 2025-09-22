/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keypairshare;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import lombok.NonNull;

public record ElGamalKeyPairShare(@NonNull ElGamalParameters params,
		@NonNull ElGamalPublicKey pubKey, @NonNull ElGamalPrivateKey privKey) {
}
