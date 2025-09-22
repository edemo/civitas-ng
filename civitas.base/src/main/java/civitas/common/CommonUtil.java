/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.util.Base64;

import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;
import io.github.magwas.kodekonveyorannotations.Glue;

@Glue
public class CommonUtil {
	public static String currentVersion() {
		return "JCivitas-v0.1";
	}

	public static CivitasBigInteger asBigint(final String s) {
		return CivitasBigIntegerFactory.obtain(Base64.getDecoder().decode(s));
	}

	public static String fromBigInt(final CivitasBigInteger a) {
		return Base64.getEncoder().encodeToString(a.toByteArray());
	}
}
