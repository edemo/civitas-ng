/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class PETShare {

	@NonNull
	public final ElGamalCiphertext ciphertext1;
	@NonNull
	public final ElGamalCiphertext ciphertext2;
	@NonNull
	public final CivitasBigInteger exponent;

}