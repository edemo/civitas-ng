package civitas.common.ballotdesign;

import java.io.IOException;
import java.io.Reader;

import civitas.common.CommonConstants;
import civitas.common.Util;

public class BallotDesignFromXML implements CommonConstants {

	public BallotDesign apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, BallotDesignOPENING_TAG);
		int size = Util.readSimpleIntTag(r, "size");
		Util.swallowTag(r, "candidates");
		String[] n = new String[size];

		for (int i = 0; i < size; i++) {
			n[i] = Util.unescapeString(Util.readSimpleTag(r, "candidate"));
		}

		Util.swallowEndTag(r, "candidates");
		Util.swallowEndTag(r, BallotDesignOPENING_TAG);
		BallotDesign b = new BallotDesign(n);

		return b;
	}

}
