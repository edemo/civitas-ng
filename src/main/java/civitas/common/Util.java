/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.util.Base64;

import civitas.util.Boilerplate;
import civitas.util.CivitasBigInteger;

/**
 * Some miscellaneous utility functions.
 */
@Boilerplate
public class Util {
	public static String currentVersion() {
		return "JCivitas-v0.1";
	}

	public static CivitasBigInteger asBigint(String s) {
		return new CivitasBigInteger(Base64.getDecoder().decode(s));
	}

	public static String fromBigInt(CivitasBigInteger a) {
		return Base64.getEncoder().encodeToString(a.toByteArray());
	}

}
