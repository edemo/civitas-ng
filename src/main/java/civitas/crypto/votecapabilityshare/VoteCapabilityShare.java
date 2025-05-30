/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.votecapabilityshare;

import java.io.PrintWriter;

import civitas.crypto.msg.ElGamalMsg;

public interface VoteCapabilityShare extends ElGamalMsg {
	public static final String OPENING_TAG = "voteCapabilityShare";

	public void toXML(PrintWriter sb);
}