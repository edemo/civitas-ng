/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common.election;

public interface ElectionEvent {
	String EVENT_KIND_START = "start";
	String EVENT_KIND_STOP = "stop";
	String EVENT_KIND_FINALIZE = "finalize";

	String getKind();

	ElectionID getElectionID();

	int getSequence();

}