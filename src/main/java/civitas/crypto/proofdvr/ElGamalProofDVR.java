/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofdvr;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ElGamalProofDVR {

	@NonNull
	public final ElGamalCiphertext e;
	@NonNull
	public final ElGamalCiphertext eprime;
	@NonNull
	public final CivitasBigInteger c;
	@NonNull
	public final CivitasBigInteger w;
	@NonNull
	public final CivitasBigInteger r;
	@NonNull
	public final CivitasBigInteger u;

}
