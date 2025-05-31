/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofknowndisclog;

import civitas.util.CivitasBigInteger;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ElGamalProofKnowDiscLog {

	@NonNull
	public final CivitasBigInteger a;
	@NonNull
	public final CivitasBigInteger c;
	@NonNull
	public final CivitasBigInteger r;
	@NonNull
	public final CivitasBigInteger v;

}