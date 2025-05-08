/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

/**
 * This class is the super class of all election events, such as start, stop,
 * and finalize election. Election events are posted by the election supervisor
 * to the bulletin board, and indicate what phase the election is in. For
 * example the election start event is posted when all of the election setup has
 * been produced (e.g., teller details, electoral roll). The election stop event
 * is posted when voting has finished, and tabulation of the election can begin.
 * The finalize event indicates the election has been finalized, and the results
 * declared.
 */
public class ElectionEvent implements XMLSerializable {
	public static final String META = "electionEvent";

	public static final String EVENT_KIND_START = "start";
	public static final String EVENT_KIND_STOP = "stop";
	public static final String EVENT_KIND_FINALIZE = "finalize";

	public final String kind;
	public final ElectionID electionID;

	/**
	 * The sequence number of an event must be a non-negative integer greater than
	 * the sequence number of any event previously posted by the supervisor for the
	 * election. This ensures that the signature for each new event will be
	 * different to the signature of any previous event, preventing replay attacks.
	 */
	public final int sequence;

	public ElectionEvent(String kind, ElectionID electionID, int sequence) {
		this.kind = kind;
		this.electionID = electionID;
		this.sequence = sequence;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<electionEvent>");
		sb.print("<kind>");
		Util.escapeString(this.kind, sb);
		sb.print("</kind>");
		if (electionID != null)
			electionID.toXML(sb);
		sb.print("<seq>" + sequence + "</seq>");
		toXMLsub(sb);
		sb.print("</electionEvent>");
	}

	protected void toXMLsub(PrintWriter sb) {

	}

	/**
	 * Convert an xml string into an election event
	 * 
	 * @throws IllegalArgumentException of the xml is not appropriate for the
	 *                                  object.
	 */
	public static ElectionEvent fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "electionEvent");
		String kind = Util.unescapeString(Util.readSimpleTag(r, "kind"));
		ElectionID electionID = ElectionID.fromXML(r);
		int seq = Util.readSimpleIntTag(r, "seq");

		if (EVENT_KIND_FINALIZE != null && EVENT_KIND_FINALIZE.equals(kind)) {
			ElectionEvent e = ElectionEventFinalize.fromXMLsub(r, electionID, seq);
			Util.swallowEndTag(r, "electionEvent");
			return e;
		}

		Util.swallowEndTag(r, "electionEvent");
		return new ElectionEvent(kind, electionID, seq);
	}

}