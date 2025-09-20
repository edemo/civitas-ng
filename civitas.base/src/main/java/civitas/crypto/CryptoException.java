/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

public class CryptoException extends Exception {

	private static final long serialVersionUID = 20060925L;

	public CryptoException(final String m) {
		super(m);
	}

	public CryptoException(final String m, final Exception e) {
		super(m, e);
	}
}
