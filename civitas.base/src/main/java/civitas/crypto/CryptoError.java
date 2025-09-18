/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

public class CryptoError extends RuntimeException {
	private static final long serialVersionUID = 20061102L;

	public CryptoError(final String m) {
		super(m);
	}

	public CryptoError(final String m, final Throwable cause) {
		super(m, cause);
	}

	public CryptoError(final Throwable cause) {
		super(cause);
	}
}
