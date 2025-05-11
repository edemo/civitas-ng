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
import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalParameters;
import civitas.util.CivitasBigInteger;

/**
 * The ElGamal cryptosystem defined by these parameters is over the unique order
 * q subgroup of Z*p, where p = 2kq + 1, and p and q are prime
 */
class ElGamalParametersC implements ElGamalParameters {

	/**
	 * A prime such that p = 2kq + 1 for some k.
	 */
	protected final CivitasBigInteger p;

	/**
	 * A large prime.
	 */
	protected final CivitasBigInteger q;

	/**
	 * A generator of the order q subgroup of Z*p.
	 */
	protected final CivitasBigInteger g;

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

	/**
	 * Constructs a Schnorr prime group where p = 2kq + 1. If groupLength =
	 * keyLength + 1, this is a safe prime group.
	 * 
	 * @param keyLength   The number of bits of q.
	 * @param groupLength The number of bits of p.
	 */
	protected ElGamalParametersC(int keyLength, int groupLength) {
		SchnorrPrime sp;
		if (groupLength == keyLength + 1) {
			sp = CryptoAlgs.safePrime(keyLength);
			encoder = new SafePrimeEncoder();
		} else {
			sp = CryptoAlgs.schnorrPrime(keyLength, groupLength);
			encoder = new SchnorrPrimeEncoder();
		}
		p = sp.p;
		q = sp.q;
		g = CryptoAlgs.generator(sp);
	}

	public ElGamalParametersC(CivitasBigInteger p, CivitasBigInteger q,
			CivitasBigInteger g) {
		this.p = p;
		this.q = q;
		this.g = g;
		if (p
				.equals(q.multiply(CivitasBigInteger.TWO).add(CivitasBigInteger.ONE))) {
			encoder = new SafePrimeEncoder();
		} else {
			encoder = new SchnorrPrimeEncoder();
		}
		checkGroup();
	}

	private void checkGroup() {
		if (!p.subtract(CivitasBigInteger.ONE).mod(q)
				.equals(CivitasBigInteger.ZERO)) {
			throw new CryptoError("q does not divide p-1");
		}
		if (!p.subtract(CivitasBigInteger.ONE).mod(CivitasBigInteger.TWO)
				.equals(CivitasBigInteger.ZERO)) {
			throw new CryptoError("2 does not divide p-1");
		}
		if (!g.modPow(q, p).equals(CivitasBigInteger.ONE)) {
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
		if (this.p != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.p), s);
		s.print("</p>");
		s.print("<q>");
		if (this.q != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.q), s);
		s.print("</q>");
		s.print("<g>");
		if (this.g != null)
			Util.escapeString(CryptoFactoryC.bigIntToString(this.g), s);
		s.print("</g>");

		s.print("</elGamalParameters>");
	}

	public static ElGamalParametersC fromXML(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalParameters");
		String sp = Util.unescapeString(Util.readSimpleTag(r, "p"));
		String sq = Util.unescapeString(Util.readSimpleTag(r, "q"));
		String sg = Util.unescapeString(Util.readSimpleTag(r, "g"));
		Util.swallowEndTag(r, "elGamalParameters");
		CivitasBigInteger p = CryptoFactoryC.stringToBigInt(sp);
		CivitasBigInteger q = CryptoFactoryC.stringToBigInt(sq);
		CivitasBigInteger g = CryptoFactoryC.stringToBigInt(sg);
		return new ElGamalParametersC(p, q, g);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ElGamalParametersC)) {
			return false;
		}

		ElGamalParametersC x = (ElGamalParametersC) o;

		return p.equals(x.p) && q.equals(x.q) && g.equals(x.g);
	}

	@Override
	public int hashCode() {
		return p.hashCode() ^ q.hashCode() ^ g.hashCode();
	}

	static interface Encoder {
		CivitasBigInteger encodePlaintext(CivitasBigInteger p)
				throws CryptoException;

		CivitasBigInteger decodeMessage(CivitasBigInteger m) throws CryptoException;
	}

	class SchnorrPrimeEncoder implements Encoder {
		@Override
		public CivitasBigInteger encodePlaintext(CivitasBigInteger x)
				throws CryptoException {
			if (x.compareTo(q) > 0) {
				throw new CryptoException("Message is too large for parameters");
			}
			return g.modPow(x, p);
		}

		@Override
		public CivitasBigInteger decodeMessage(CivitasBigInteger m)
				throws CryptoException {
			throw new CryptoException(
					"Decoding is not supported for Schnorr prime groups.");
		}
	}

	class SafePrimeEncoder implements Encoder {
		@Override
		public CivitasBigInteger encodePlaintext(CivitasBigInteger x)
				throws CryptoException {
			CivitasBigInteger encoding = x;
			if (CryptoAlgs.legendreSymbol(encoding, p, q) == -1) {
				encoding = p.subtract(encoding); // encoding = -m
			}
			return encoding;
		}

		@Override
		public CivitasBigInteger decodeMessage(CivitasBigInteger i)
				throws CryptoException {
			if (i.compareTo(p) > 0) {
				throw new CryptoException("Message is too large for parameters");
			}
			if (i.compareTo(q) > 0) {
				i = p.subtract(i); // i = -i
			}
			return i;
		}
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
