/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.privatekey;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import lombok.Data;
import lombok.NonNull;

@Data
public class ElGamalPrivateKey {

	@NonNull
	public final CivitasBigInteger x;
	@NonNull
	public final ElGamalParameters params;

}
