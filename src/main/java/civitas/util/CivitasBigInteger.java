/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.util;

import java.math.BigInteger;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import civitas.crypto.Constants;
import jakarta.persistence.Embeddable;

@Boilerplate
@JsonSerialize(using = CivitasBigIntegerSerializer.class)
@JsonDeserialize(using = CivitasBigIntegerDeserializer.class)
@Embeddable
public class CivitasBigInteger implements Constants {

	public final BigInteger i;

	public CivitasBigInteger(byte[] bytes) {
		i = new BigInteger(bytes);
	}

	public CivitasBigInteger(int signum, byte[] magnitude) {
		i = new BigInteger(signum, magnitude);
	}

	/** Construct a probable prime. */
	public CivitasBigInteger(int length, int certainty, Random random) {
		i = new BigInteger(length, certainty, random);
	}

	/** Construct a random integer. */
	public CivitasBigInteger(int i, Random random) {
		this.i = new BigInteger(i, random);
	}

	private CivitasBigInteger(BigInteger integer) {
		this.i = integer;
	}

	public static CivitasBigInteger valueOf(int i) {
		return new CivitasBigInteger(BigInteger.valueOf(i));
	}

	public int bitLength() {
		return i.bitLength();
	}

	public CivitasBigInteger add(CivitasBigInteger x) {
		if (x == ZERO)
			return this;
		return new CivitasBigInteger(i.add(x.i));
	}

	public CivitasBigInteger modAdd(CivitasBigInteger x, CivitasBigInteger p) {
		if (x == ZERO)
			return this.mod(p);
		return new CivitasBigInteger(i.add(x.i).mod(p.i));
	}

	public int intValue() {
		return i.intValue();
	}

	public int compareTo(CivitasBigInteger n) {
		return i.compareTo(n.i);
	}

	public CivitasBigInteger modPow(CivitasBigInteger x, CivitasBigInteger p) {
		return new CivitasBigInteger(this.i.modPow(x.i, p.i));
	}

	public boolean isProbablePrime(int certainty) {
		return i.isProbablePrime(certainty);
	}

	public CivitasBigInteger nextProbablePrime() {
		return new CivitasBigInteger(i.nextProbablePrime());
	}

	public CivitasBigInteger mod(CivitasBigInteger q) {
		BigInteger m = this.i.mod(q.i);
		if (this.i == m)
			return this;
		return new CivitasBigInteger(m);
	}

	public CivitasBigInteger multiply(CivitasBigInteger x) {
		if (x == ONE)
			return this;
		if (x == ZERO)
			return ZERO;
		return new CivitasBigInteger(this.i.multiply(x.i));
	}

	public CivitasBigInteger divide(CivitasBigInteger q) {
		return new CivitasBigInteger(this.i.divide(q.i));
	}

	public CivitasBigInteger modMultiply(CivitasBigInteger x,
			CivitasBigInteger p) {
		if (x == ZERO)
			return ZERO;
		if (x == ONE)
			return this.mod(p);
		return new CivitasBigInteger(this.i.multiply(x.i).mod(p.i));
	}

	public CivitasBigInteger modDivide(CivitasBigInteger x, CivitasBigInteger p) {
		if (x == ONE)
			return this.mod(p);
		return new CivitasBigInteger(this.i.multiply(x.i.modInverse(p.i)).mod(p.i));
	}

	public CivitasBigInteger modInverse(CivitasBigInteger p) {
		return new CivitasBigInteger(this.i.modInverse(p.i));
	}

	public CivitasBigInteger modNegate(CivitasBigInteger p) {
		return new CivitasBigInteger(this.i.negate().mod(p.i));
	}

	public CivitasBigInteger subtract(CivitasBigInteger x) {
		if (x == ZERO)
			return this;
		return new CivitasBigInteger(this.i.subtract(x.i));
	}

	public CivitasBigInteger modSubtract(CivitasBigInteger x,
			CivitasBigInteger p) {
		if (x == ZERO)
			return this.mod(p);
		return new CivitasBigInteger(this.i.subtract(x.i).mod(p.i));
	}

	public CivitasBigInteger pow(int j) {
		return new CivitasBigInteger(this.i.pow(j));
	}

	@Override
	public String toString() {
		return i.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CivitasBigInteger) {
			return this.i.equals(((CivitasBigInteger) o).i);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return i.hashCode();
	}

	public byte[] toByteArray() {
		return i.toByteArray();
	}

	@Autowired
	static ObtainProbablePrime obtainProbablePrime;

	public static CivitasBigInteger probablePrime(int bitLenght, int certainty,
			Random random) {
		return obtainProbablePrime.apply(bitLenght, certainty, random);
	}

}
