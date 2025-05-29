/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import java.io.PrintWriter;

/**
 * The NIZK proof that accompanies a vote when posted on a BB.
 */
public interface ProofVote {
	public boolean verify(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context);

	public void toXML(PrintWriter sb);

	public boolean equals(ProofVote p);
}