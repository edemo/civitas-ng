/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ProofVote;
import civitas.util.CivitasBigInteger;

/**
 * This is a "non-malleable" (in some informal sense), NIZK proof of knowledge
 * of the voter's vote, including its capability, choice, and context. The
 * prover here is the voter, and the verifier is any auditor (including
 * tabulation teller) of the BB. The core of it is a conjunction of two Schnorr
 * signatures. The basic design is due to [Jan Camenisch and Markus Stadler.
 * Efficient Group Signatures for Large Groups.]
 */
public class ProofVoteC implements ProofVote {

	/*
	 * Public inputs o ElGamal parameters (p,g) o Encrypted capability = (a1,b1) o
	 * Encrypted choice = (a2,b2) o Vote context ctx o Let proof environment E =
	 * (g,a1,b1,a2,b2,ctx) Prover private inputs: o alpha1, alpha2 s.t. ai =
	 * g^{alphai} Prover: o Select r1,r2 at random from Z_q o Compute: + c =
	 * hash(E,g^r1,g^r2) + s1 = r1 - c*alpha1 mod p + s2 = r2 - c*alpha2 mod p
	 * Prover -> Verifier: (c,s1,s2) Verifier: o Check c = hash(E, g^s1 * a1^c,
	 * g^s2 * a2^c)
	 * 
	 */

	public final CivitasBigInteger c;
	public final CivitasBigInteger s1;
	public final CivitasBigInteger s2;

	public ProofVoteC(final CivitasBigInteger c, final CivitasBigInteger s1,
			final CivitasBigInteger s2) {
		this.c = c;
		this.s1 = s1;
		this.s2 = s2;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalProofVote>");

		s.print("<c>");
		if (this.c != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.c), s);
		s.print("</c>");
		s.print("<s1>");
		if (this.s1 != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.s1), s);
		s.print("</s1>");
		s.print("<s2>");
		if (this.s2 != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.s2), s);
		s.print("</s2>");

		s.print("</elGamalProofVote>");
	}

	@Override
	public boolean equals(ProofVote p) {
		if (p instanceof ProofVoteC) {
			ProofVoteC that = (ProofVoteC) p;
			try {
				return this.c.equals(that.c) //
						&& this.s1.equals(that.s1) //
						&& this.s2.equals(that.s2);
			} catch (NullPointerException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean verify(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context) {
		throw new UnsupportedOperationException("use VerifyProofVote");
	}
}
