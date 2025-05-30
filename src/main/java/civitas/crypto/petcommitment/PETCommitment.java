/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.petcommitment;

import java.io.PrintWriter;

/**
 * A server's commimtment for the PET share
 */
public interface PETCommitment {
	public final static String OPENING_TAG = "petC";

	public void toXML(PrintWriter sb);
}