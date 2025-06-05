package civitas.common.votersubmission;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifiableVoteFromXML;
import civitas.util.Use;

public class VoterSubmissionFromXML {

	@Use
	CreateVoterSubmission createVoterSubmission;
	@Use
	VerifiableVoteFromXML verifiableVoteFromXML;

	public VoterSubmission apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "voterSubmission");

		int voterBlock = Util.readSimpleIntTag(r, "voterBlock");
		int size = Util.readSimpleIntTag(r, "size");

		Util.swallowTag(r, "votes");
		VerifiableVote[] votes = new VerifiableVote[size < 0 ? 0 : size];

		for (int i = 0; i < size; i++) {
			try {
				votes[i] = verifiableVoteFromXML.apply(r);
			} catch (ArrayIndexOutOfBoundsException e) {
				throw new IOException("Too many verifiable votes");
			}
		}

		Util.swallowEndTag(r, "votes");
		Util.swallowEndTag(r, "voterSubmission");
		return createVoterSubmission.apply(voterBlock, votes);
	}
}
