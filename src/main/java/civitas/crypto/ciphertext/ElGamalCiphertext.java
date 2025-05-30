/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.ciphertext;

import java.io.PrintWriter;

public interface ElGamalCiphertext {
	public static final String OPENING_TAG = "elGamalCiphertext";

	public void toXML(PrintWriter sb);

	@Deprecated
	public void toUnsignedCiphertextXML(PrintWriter sb);

	public boolean equals(ElGamalCiphertext o);

	@Override
	public int hashCode();
}
