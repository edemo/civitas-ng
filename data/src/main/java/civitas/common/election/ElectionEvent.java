/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

public interface ElectionEvent {
//public final String kind;
//public final ElectionID electionID;
//public final int sequence;
	public static final String EVENT_KIND_START = "start";
	public static final String EVENT_KIND_STOP = "stop";
	public static final String EVENT_KIND_FINALIZE = "finalize";

	public String getKind();

	public ElectionID getElectionID();

	public int getSequence();

}