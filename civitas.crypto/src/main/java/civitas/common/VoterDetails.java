/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.security.PublicKey;

import civitas.crypto.publickey.ElGamalPublicKey;

public record VoterDetails(String name, ElGamalPublicKey egPublicKey, PublicKey publicKey, int voterBlock) {
	public static final String OPENING_TAG = "voterDetails";
}
