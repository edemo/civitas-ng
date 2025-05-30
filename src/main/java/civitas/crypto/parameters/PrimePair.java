/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.parameters;

import civitas.util.CivitasBigInteger;

/**
 * A prime p of the form p = 2kq+1, where q is also prime.
 */
public class PrimePair {
	public CivitasBigInteger p;
	public CivitasBigInteger q;

	public PrimePair(CivitasBigInteger p, CivitasBigInteger q) {
		this.p = p;
		this.q = q;
	}

}
