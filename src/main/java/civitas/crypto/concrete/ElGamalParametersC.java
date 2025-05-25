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
import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.Encoder;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.FindGenerator;
import civitas.crypto.algorithms.GenerateSafePrime;
import civitas.crypto.algorithms.GenerateSchnorrPrime;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

/**
 * The ElGamal cryptosystem defined by these parameters is over the unique order
 * q subgroup of Z*p, where p = 2kq + 1, and p and q are prime
 */
public class ElGamalParametersC implements ElGamalParameters, Constants {

	@Use
	GenerateSafePrime generateSafePrime;
	@Use
	GenerateSchnorrPrime generateSchnorrPrime;
	@Use
	FindGenerator findGenerator;
	/**
	 * A prime such that p = 2kq + 1 for some k.
	 */
	public final CivitasBigInteger p;

	/**
	 * A large prime.
	 */
	public final CivitasBigInteger q;

	/**
	 * A generator of the order q subgroup of Z*p.
	 */
	public final CivitasBigInteger g;

	/**
	 * A helper object to encode plaintexts into messages, and also decode
	 * messages to plaintexts.
	 */
	protected Encoder encoder;

	/**
	 * No implicit construction allowed.
	 */
	protected ElGamalParametersC() {
		throw new UnsupportedOperationException();
	}

	public ElGamalParametersC(CivitasBigInteger p, CivitasBigInteger q,
			CivitasBigInteger g) {
		this.p = p;
		this.q = q;
		this.g = g;
		if (p.equals(q.multiply(TWO).add(ONE))) {
			encoder = new SafePrimeEncoder(this);
		} else {
			encoder = new SchnorrPrimeEncoder(this);
		}
		checkGroup();
	}

	private void checkGroup() {
		if (!p.isProbablePrime(CERTAINTY))
			throw new CryptoError("p is not prime");
		if (!q.isProbablePrime(CERTAINTY))
			throw new CryptoError("q is not prime");
		if (!p.subtract(ONE).mod(q).equals(ZERO)) {
			throw new CryptoError("q does not divide p-1");
		}
		if (!g.modPow(q, p).equals(ONE)) {
			throw new CryptoError("g is not order q");
		}

	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalParameters>");

		s.print("<p>");
		Util.escapeString(CryptoFactoryC.bigIntToString(this.p), s);
		s.print("</p>");
		s.print("<q>");
		Util.escapeString(CryptoFactoryC.bigIntToString(this.q), s);
		s.print("</q>");
		s.print("<g>");
		Util.escapeString(CryptoFactoryC.bigIntToString(this.g), s);
		s.print("</g>");

		s.print("</elGamalParameters>");
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ElGamalParametersC)) {
			return false;
		}

		ElGamalParametersC x = (ElGamalParametersC) o;

		return q.equals(x.q) && p.equals(x.p) && g.equals(x.g);
	}

	@Override
	public int hashCode() {
		return p.hashCode() ^ q.hashCode() ^ g.hashCode();
	}

	public CivitasBigInteger decodeMessage(CivitasBigInteger m)
			throws CryptoException {
		return this.encoder.decodeMessage(m);
	}

	public CivitasBigInteger encodePlaintext(CivitasBigInteger p)
			throws CryptoException {
		return this.encoder.encodePlaintext(p);
	}

	/**
	 * Attempt to decode a message by brute force.
	 * 
	 * @return If m does not decode to an integer i such that 1 <= i <= L.
	 */
	@Deprecated
	public int bruteForceDecode(CivitasBigInteger m, int L)
			throws CryptoException {
		// first, try doing this the nice way
		try {
			CivitasBigInteger c = decodeMessage(m);
			int i = c.intValue();
			if (1 <= i && i <= L) {
				return i;
			}
		} catch (CryptoException c) {
			// ignore and attempt brute force
		}

		// now try brute force
		CivitasBigInteger x = g;
		for (int i = 1; i <= L; i++) {
			if (x.equals(m)) {
				return i;
			}
			x = x.modMultiply(g, p);
		}

		throw new CryptoException("Brute force decoding failed");
	}
}
