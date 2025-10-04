/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.proofvote;

import civitas.util.CivitasBigInteger;
import lombok.NonNull;
import lombok.Value;

@Value
public class ProofVote {

	@NonNull public CivitasBigInteger c;

	@NonNull public CivitasBigInteger s1;

	@NonNull public CivitasBigInteger s2;
}
