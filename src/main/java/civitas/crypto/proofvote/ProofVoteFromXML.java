package civitas.crypto.proofvote;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.Use;

public class ProofVoteFromXML {

	@Use
	ConvertToBigInt convertToBigInt;

	public ProofVote apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalProofVote");
		String c = Util.unescapeString(Util.readSimpleTag(r, "c"));
		String s1 = Util.unescapeString(Util.readSimpleTag(r, "s1"));
		String s2 = Util.unescapeString(Util.readSimpleTag(r, "s2"));

		Util.swallowEndTag(r, "elGamalProofVote");
		return new ProofVote(convertToBigInt.apply(c), convertToBigInt.apply(s1),
				convertToBigInt.apply(s2));
	}

}
