/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petcommitment;

import civitas.crypto.Constants;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.NonNull;

@Data
public class PETCommitment implements Constants {
	@NonNull
	public final CivitasBigInteger hash;

}