/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.parameters;

import civitas.util.CivitasBigInteger;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor
@EqualsAndHashCode
public class ElGamalParameters {

	@NonNull
	public final CivitasBigInteger p;
	@NonNull
	public final CivitasBigInteger q;
	@NonNull
	public final CivitasBigInteger g;

}
