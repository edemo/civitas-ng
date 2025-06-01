/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.signedciphertext;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ElGamalSignedCiphertext extends ElGamalCiphertext {
	@NonNull
	public final CivitasBigInteger c;
	@NonNull
	public final CivitasBigInteger d;

	public ElGamalSignedCiphertext(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, CivitasBigInteger d) {
		super(a, b);
		this.c = c;
		this.d = d;
	}
}
