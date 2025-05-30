/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.key;

import java.io.PrintWriter;

import civitas.crypto.parameters.ElGamalParameters;

public interface ElGamalKey {
	public ElGamalParameters getParams();

	public void toXML(PrintWriter sb);
}
