package civitas.common.ballotdesign;

import java.util.Map;

import civitas.common.CommonConstants;
import civitas.common.tallystate.RecordBeat;
import civitas.common.tallystate.TallyState;
import civitas.crypto.CryptoException;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.parameters.DecodeChoice;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class TallyVote implements CommonConstants {

	@Use
	RecordBeat recordBeat;
	@Use
	DecodeChoice decodeChoice;

	public void apply(String additionalcontext, ElGamalMsg msg,
			String currentcontext, TallyState state,
			Map<CivitasBigInteger, Integer> decodeMap) throws IllegalArgumentException

	{

		String desiredContext = additionalcontext + KIND;
		if (currentcontext == null || !currentcontext.startsWith(desiredContext)) {
			throw new IllegalArgumentException("Incorrect context");
		}

		if (msg == null) {
			throw new IllegalArgumentException("Null message");
		}

		try {
			int choice = decodeChoice.apply(decodeMap, msg.m);
			String suffix = currentcontext.substring(desiredContext.length());
			int ind = suffix.indexOf(':');
			try {
				int i = Integer.parseInt(suffix.substring(0, ind));
				int j = Integer.parseInt(suffix.substring(ind + 1));
				if (choice == VOTE_CHOICE_I_BEATS_J) {
					recordBeat.apply(state, i, j);
				}
				if (choice == VOTE_CHOICE_J_BEATS_I) {
					recordBeat.apply(state, j, i);
				}

			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid context: " + suffix);
			}

		} catch (CryptoException e) {
			throw new IllegalArgumentException("Invalid vote value", e);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid index", e);
		}
	}

}
