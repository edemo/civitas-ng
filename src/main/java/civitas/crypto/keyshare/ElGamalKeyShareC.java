/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.keyshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogC;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.util.Use;

public class ElGamalKeyShareC extends ElGamalAbstractKeyShare {

	@Use
	VerifyElGamalKeyShare verifyElGamalKeyShare;

	public ElGamalKeyShareC(ElGamalPublicKeyC pubKey,
			ElGamalProofKnowDiscLogC proof) {
		super(pubKey, proof);
	}

	@Override
	public boolean verify() {
		return verifyElGamalKeyShare.apply(this);
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalKeyShare>");
		s.print("<pubKey>");
		if (pubKey != null)
			pubKey.toXML(s);
		s.print("</pubKey>");
		s.print("<proof>");
		if (proof != null)
			proof.toXML(s);
		s.print("</proof>");
		s.print("</elGamalKeyShare>");
	}

}
