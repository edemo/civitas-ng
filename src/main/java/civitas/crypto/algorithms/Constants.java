/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.algorithms;

import java.security.SecureRandom;
import java.util.Random;

public class Constants {
	/**
	 * 2^-CERTAINTY is false positive rate for probablePrime.
	 */
	public final static int CERTAINTY = 80; // 2^80 recommended by FIPS 186.

	// TODO: should we be requesting a specific RNG algorithm in the constructor
	// call?
	public static Random RANDOM_GENERATOR = new SecureRandom();

}
