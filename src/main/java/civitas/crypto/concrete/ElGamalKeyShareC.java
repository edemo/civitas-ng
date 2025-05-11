/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ElGamalAbstractKeyShare;

class ElGamalKeyShareC extends ElGamalAbstractKeyShare {

	protected ElGamalKeyShareC(ElGamalPublicKeyC pubKey,
			ElGamalProofKnowDiscLogC proof) {
		super(pubKey, proof);
	}

	@Override
	public boolean verify() {
		ElGamalProofKnowDiscLogC prf = (ElGamalProofKnowDiscLogC) proof;
		// the base of the prf is correct, as it is taken from params.
		ElGamalPublicKeyC K = (ElGamalPublicKeyC) pubKey;
		if (prf == null || K == null) {
			return false;
		}
		return prf.v.equals(K.y) && prf.verify(pubKey.getParams());
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

	public static ElGamalKeyShareC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalKeyShare");
		Util.swallowTag(r, "pubKey");
		ElGamalPublicKeyC pubKey = ElGamalPublicKeyC.fromXML(r);
		Util.swallowEndTag(r, "pubKey");
		Util.swallowTag(r, "proof");
		ElGamalProofKnowDiscLogC proof = ElGamalProofKnowDiscLogC.fromXML(r);
		Util.swallowEndTag(r, "proof");
		Util.swallowEndTag(r, "elGamalKeyShare");
		return new ElGamalKeyShareC(pubKey, proof);
	}
}
