/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickey;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ElGamalPublicKey {

	@NonNull public CivitasBigInteger y;

	@NonNull public ElGamalParameters params;
}
