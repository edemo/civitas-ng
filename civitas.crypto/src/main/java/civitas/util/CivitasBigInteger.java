/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.util;

import java.math.BigInteger;

@Boilerplate
public class CivitasBigInteger extends CivitasBigintegerBase {

	CivitasBigInteger(final BigInteger integer) {
		super(integer);
	}

	public CivitasBigInteger add(final CivitasBigInteger x) {
		if (x == ZERO) {
			return this;
		}
		return new CivitasBigInteger(i.add(x.i));
	}

	public CivitasBigInteger mod(final CivitasBigInteger q) {
		BigInteger m = this.i.mod(q.i);
		if (this.i.equals(m)) {
			return this;
		}
		return new CivitasBigInteger(m);
	}

	public CivitasBigInteger modAdd(final CivitasBigInteger x, final CivitasBigInteger p) {
		if (x == ZERO) {
			return this.mod(p);
		}
		return new CivitasBigInteger(i.add(x.i).mod(p.i));
	}

	public CivitasBigInteger modPow(final CivitasBigInteger x, final CivitasBigInteger p) {
		return new CivitasBigInteger(this.i.modPow(x.i, p.i));
	}

	public CivitasBigInteger multiply(final CivitasBigInteger x) {
		if (x == ONE) {
			return this;
		}
		if (x == ZERO) {
			return ZERO;
		}
		return new CivitasBigInteger(this.i.multiply(x.i));
	}

	public CivitasBigInteger modMultiply(final CivitasBigInteger x, final CivitasBigInteger p) {
		if (x == ZERO) {
			return ZERO;
		}
		if (x == ONE) {
			return this.mod(p);
		}
		return new CivitasBigInteger(this.i.multiply(x.i).mod(p.i));
	}

	public CivitasBigInteger modDivide(final CivitasBigInteger x, final CivitasBigInteger p) {
		if (x == ONE) {
			return this.mod(p);
		}
		return new CivitasBigInteger(this.i.multiply(x.i.modInverse(p.i)).mod(p.i));
	}

	public CivitasBigInteger subtract(final CivitasBigInteger x) {
		if (x == ZERO) {
			return this;
		}
		return new CivitasBigInteger(this.i.subtract(x.i));
	}

	public CivitasBigInteger modSubtract(final CivitasBigInteger x, final CivitasBigInteger p) {
		if (x == ZERO) {
			return this.mod(p);
		}
		return new CivitasBigInteger(this.i.subtract(x.i).mod(p.i));
	}
}
