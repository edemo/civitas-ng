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
 * This class encapsulates the identiy of a bulletin board: the host, port, and
 * a bulletin board id.
 */
public class BulletinBoardID implements XMLSerializable {
	public final String host;
	public final int port;
	public final String id;

	public BulletinBoardID(String host, int port, String id) {
		this.host = host;
		this.port = port;
		this.id = id;
	}

	@Override
	public String toString() {
		return this.host + ":" + this.port + ":" + this.id;
	}

	public boolean equals(BulletinBoardID that) {
		return that != null && this.port == that.port
				&& (this.host == that.host || (this.host != null && this.host.equals(that.host)))
				&& (this.id == that.id || (this.id != null && this.id.equals(that.id)));
	}

	private static final String TAG = "bulletinBoardID";

	protected String tag() {
		return TAG;
	}

//    public String  toBulletinBoardXML() {
//        return toXMLImpl(TAG);
//    }
//    public String  toXML() {
//        return toXMLImpl(tag());
//    }
//    private String{this;t} toXMLImpl(String t) {
//        String s = "<" + t + ">";
//
//        s += "<host>";
//        s += this.host;
//        s += "</host>";
//        s += "<port>";
//        s += this.port;
//        s += "</port>";
//        s += "<id>";
//        s += this.id;
//        s += "</id>";
//        
//        s += "</" + t + ">";
//                
//        return s;
//    }
	@Override
	public void toXML(PrintWriter sb) {
		toXMLImpl(tag(), sb);
	}

	public void toBulletinBoardXML(PrintWriter sb) {
		toXMLImpl(TAG, sb);
	}

	private void toXMLImpl(String t, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + t + ">");

		sb.print("<host>");
		Util.escapeString(this.host, sb);
		sb.print("</host>");
		sb.print("<port>");
		sb.print(this.port);
		sb.print("</port>");
		sb.print("<id>");
		Util.escapeString(this.id, sb);
		sb.print("</id>");

		sb.print("</" + t + ">");
	}

	public static BulletinBoardID bbIDFromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, TAG);
		String host = Util.unescapeString(Util.readSimpleTag(r, "host"));
		int port = Util.readSimpleIntTag(r, "port");
		String id = Util.unescapeString(Util.readSimpleTag(r, "id"));
		Util.swallowEndTag(r, TAG);
		return new BulletinBoardID(host, port, id);
	}
}