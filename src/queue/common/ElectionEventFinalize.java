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
 * A "finalize election" event. Like all election events, it is posted by the
 * election supervisor to the bulletin board. It contains a final tally of the
 * election, and a message from the supervisor (e.g., announcing who has won).
 */
public class ElectionEventFinalize extends ElectionEvent {
	public final TallyStateFinal tally;
	public final String message;

	public ElectionEventFinalize(ElectionID electionID, int sequence) {
		super(EVENT_KIND_FINALIZE, electionID, sequence);
		this.tally = null;
		this.message = null;
	}

	public ElectionEventFinalize(ElectionID electionID, int sequence, TallyStateFinal tally, String message) {
		super(EVENT_KIND_FINALIZE, electionID, sequence);
		this.tally = tally;
		this.message = message;
	}

	@Override
	protected void toXMLsub(PrintWriter sb) {
		if (sb == null)
			return;
		if (this.tally != null) {
			this.tally.toXML(sb);
		}
		if (this.message != null) {
			sb.print("<message>");
			sb.print(this.message);
			sb.print("</message>");
		}
	}

	public static ElectionEvent fromXMLsub(Reader r, ElectionID electionID, int seq)
			throws IllegalArgumentException, IOException {
		TallyStateFinal tally = null;
		String message = null;

		if (Util.isNextTag(r, TallyStateFinal.OPENING_TAG)) {
			tally = TallyStateFinal.fromXML(r);
		}
		if (Util.isNextTag(r, "message")) {
			message = Util.readSimpleTag(r, "message");
		}
		return new ElectionEventFinalize(electionID, seq, tally, message);
	}

}