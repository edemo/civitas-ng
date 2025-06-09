/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.rsapublickey.PublicKey;
import lombok.Data;

@Data
public class VoterDetails {
	public static final String OPENING_TAG = "voterDetails";

	public final String name;

	public final ElGamalPublicKey egPublicKey;
	public final PublicKey publicKey;

	public final int voterBlock;

}