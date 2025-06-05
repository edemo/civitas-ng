package civitas.common.ballot;

import java.io.IOException;
import java.io.Reader;

import civitas.common.CommonConstants;
import civitas.common.Util;
import civitas.common.ballotdesign.ConvertStringToChoice;
import civitas.util.Use;

public class BallotFromXML implements CommonConstants {
	@Use
	RecordOnePairInBallot recordOnePairInBallot;
	@Use
	CreateEmptyBallot createEmptyBallot;
	@Use
	ConvertStringToChoice convertStringToChoice;

	public Ballot apply(Reader r) throws IllegalArgumentException, IOException {
		Util.swallowTag(r, CondorcetBallotOPENING_TAG);
		int k = Util.readSimpleIntTag(r, "k");
		Ballot ballot = createEmptyBallot.apply(k);
		Util.swallowTag(r, "matrix");
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				Util.swallowTag(r, "entry");
				if (i != Util.readSimpleIntTag(r, "i")
						|| j != Util.readSimpleIntTag(r, "j")) {
					throw new IOException("Expected pair (" + i + "," + j + ")");
				}
				byte choice = convertStringToChoice
						.apply(Util.readSimpleTag(r, "choice"));
				recordOnePairInBallot.apply(ballot, i, j, choice);
				Util.swallowEndTag(r, "entry");
			}
		}

		Util.swallowEndTag(r, "matrix");
		Util.swallowEndTag(r, CondorcetBallotOPENING_TAG);
		return ballot;
	}
}
