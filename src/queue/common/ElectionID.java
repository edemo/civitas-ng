/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.Reader;

/**
 * This class encapsulates an election identifier. An election identitifier is
 * composed of the bulletin board host, port, and a bulletin board id.
 */
public class ElectionID extends BulletinBoardID {
	public ElectionID(String host, int port, String id) {
		super(host, port, id);
	}

	public boolean equals(ElectionID that) {
		return this.equals((BulletinBoardID) that);
	}

	private static final String TAG = "electionID";

	@Override
	protected String tag() {
		return TAG;
	}

	public static ElectionID fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, TAG);
		String host = Util.unescapeString(Util.readSimpleTag(r, "host"));
		int port = Util.readSimpleIntTag(r, "port");
		String id = Util.unescapeString(Util.readSimpleTag(r, "id"));
		Util.swallowEndTag(r, TAG);
		return new ElectionID(host, port, id);
	}
}