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
 * A data structure to to combine a list of registration tellers, a list of
 * tabulation tellers, and a list of voter ballot boxes.
 */
public class TellerDetails implements XMLSerializable {
	public final Host[] registrationTellers;
	public final Host[] tabulationTellers;
	public final Host[] voterBBs;

	public static final String META = "tellerDetails";

	public TellerDetails(Host[] registrationTellers, Host[] tabulationTellers, Host[] voterBBs) {
		Host[] rts = null;
		if (registrationTellers != null) {
			rts = registrationTellers.clone();
		}
		Host[] tts = null;
		if (tabulationTellers != null) {
			tts = tabulationTellers.clone();
		}
		Host[] bbs = null;
		if (voterBBs != null) {
			bbs = voterBBs.clone();
		}

		this.registrationTellers = rts;
		this.tabulationTellers = tts;
		this.voterBBs = bbs;
	}

	@Override
	public String toString() {
		String s = "";

		s += "registrationTellers: ";
		try {
			for (int i = 0; i < registrationTellers.length; i++) {
				if (i != 0)
					s += ", ";
				s += registrationTellers[i].toString();
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}

		s += "  tabulationTellers: ";
		try {
			for (int i = 0; i < tabulationTellers.length; i++) {
				if (i != 0)
					s += ", ";
				s += tabulationTellers[i].toString();
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}

		s += "  voter BBs: ";
		try {
			for (int i = 0; i < voterBBs.length; i++) {
				if (i != 0)
					s += ", ";
				s += voterBBs[i].toString();
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}

		return s;
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<tellerDetails>");

		if (this.registrationTellers != null) {
			sb.print("<numRegTellers>");
			sb.print(this.registrationTellers.length);
			sb.print("</numRegTellers>");
		} else {
			sb.print("<numRegTellers>0</numRegTellers>");
		}
		sb.print("<registrationTellers>");
		try {
			for (int i = 0; i < registrationTellers.length; i++) {
				registrationTellers[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</registrationTellers>");

		if (this.tabulationTellers != null) {
			sb.print("<numTabTellers>");
			sb.print(this.tabulationTellers.length);
			sb.print("</numTabTellers>");
		} else {
			sb.print("<numTabTellers>0</numTabTellers>");
		}
		sb.print("<tabulationTellers>");
		try {
			for (int i = 0; i < tabulationTellers.length; i++) {
				tabulationTellers[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</tabulationTellers>");

		if (this.voterBBs != null) {
			sb.print("<numVoterBBs>");
			sb.print(this.voterBBs.length);
			sb.print("</numVoterBBs>");
		} else {
			sb.print("<numVoterBBs>0</numVoterBBs>");
		}
		sb.print("<voterBBs>");
		try {
			for (int i = 0; i < voterBBs.length; i++) {
				voterBBs[i].toXML(sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</voterBBs>");

		sb.print("</tellerDetails>");
	}

	public static TellerDetails fromXML(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "tellerDetails");

		int rtSize = Util.readSimpleIntTag(r, "numRegTellers");
		Host[] n = new Host[rtSize < 0 ? 0 : rtSize];
		Util.swallowTag(r, "registrationTellers");
		for (int i = 0; i < rtSize; i++) {
			try {
				n[i] = Host.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		Util.swallowEndTag(r, "registrationTellers");

		int ttSize = Util.readSimpleIntTag(r, "numTabTellers");
		Host[] m = new Host[ttSize < 0 ? 0 : ttSize];
		Util.swallowTag(r, "tabulationTellers");
		for (int i = 0; i < ttSize; i++) {
			try {
				m[i] = Host.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		Util.swallowEndTag(r, "tabulationTellers");

		int bbSize = Util.readSimpleIntTag(r, "numVoterBBs");
		Host[] bbs = new Host[bbSize < 0 ? 0 : bbSize];
		Util.swallowTag(r, "voterBBs");
		for (int i = 0; i < bbSize; i++) {
			try {
				bbs[i] = Host.fromXML(r);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		Util.swallowEndTag(r, "voterBBs");
		Util.swallowEndTag(r, "tellerDetails");

		TellerDetails td = new TellerDetails(n, m, bbs);
		return td;
	}
}