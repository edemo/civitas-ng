/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapability;

import java.io.PrintWriter;

import civitas.crypto.msg.ElGamalMsg;

/**
 * A capability that a voter uses to vote with.
 */
public interface VoteCapability extends ElGamalMsg {
	public static final String OPENING_TAG = "voteCapability";

	public void toXML(PrintWriter sb);
}