/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto;

import civitas.util.Boilerplate;

@Boilerplate
public class CryptoUtil {
	private static final CryptoFactory factory = CryptoFactoryC.singleton();

	private CryptoUtil() {
	}

	public static CryptoFactory factory() {
		return factory;
	}
}
