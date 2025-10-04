/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.signedciphertext;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.util.CivitasBigInteger;
import lombok.NonNull;
import lombok.Value;

@Value
public class ElGamalSignedCiphertext implements ElGamalCiphertextish {
	@NonNull public CivitasBigInteger a;

	@NonNull public CivitasBigInteger b;

	@NonNull public CivitasBigInteger c;

	@NonNull public CivitasBigInteger d;
}
