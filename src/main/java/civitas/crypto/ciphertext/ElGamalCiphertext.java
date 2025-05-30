/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.ciphertext;

import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ElGamalCiphertext {
	public static final String OPENING_TAG = "elGamalCiphertext";

	public final CivitasBigInteger a;
	public final CivitasBigInteger b;

	public ElGamalCiphertext(CivitasBigInteger a, CivitasBigInteger b) {
		this.a = a;
		this.b = b;
	}

}
