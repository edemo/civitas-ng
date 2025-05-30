/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.publickey;

import java.io.PrintWriter;

public interface PublicKey {
	public final static String OPENING_TAG = "publicKey";

	public void toXML(PrintWriter sb);
}
