package civitas.common.ballotdesign;

import java.util.Map;

import civitas.common.CommonConstants;
import civitas.common.CondorcetTallyState;
import civitas.common.TallyState;
import civitas.crypto.CryptoException;
import civitas.crypto.CryptoUtil;
import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;

public class TallyVote implements CommonConstants {

	public void apply(String ctxt, ElGamalMsg m, String c, TallyState s,
			Map<CivitasBigInteger, Integer> decodeMap) throws IllegalArgumentException

	{

		if (!(s instanceof CondorcetTallyState)) {
			throw new IllegalArgumentException("Incorrect tally state");
		}
		CondorcetTallyState cts = (CondorcetTallyState) s;

		// interpret the context and the message
		String desiredContext = (ctxt == null ? "" : ctxt) + KIND;
		if (c == null || desiredContext == null || !c.startsWith(desiredContext)) {
			throw new IllegalArgumentException("Incorrect context");
		}

		if (m == null) {
			throw new IllegalArgumentException("Null message");
		}

		try {
			int choice = CryptoUtil.factory().elGamal1OfLValue(m, decodeMap);
			String suffix = c.substring(desiredContext.length());
			// suffix is of the form "i:j". Find i and j.
			if (suffix != null) {
				int ind = suffix.indexOf(':');
				try {
					int i = Integer.parseInt(suffix.substring(0, ind));
					int j = Integer.parseInt(suffix.substring(ind + 1));
					if (choice == VOTE_CHOICE_I_BEATS_J) {
						cts.record(i, j);
					}
					if (choice == VOTE_CHOICE_J_BEATS_I) {
						cts.record(j, i);
					}

				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("Invalid context: " + suffix);
				}
			}
		} catch (CryptoException e) {
			throw new IllegalArgumentException("Invalid vote value");
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Invalid index");
		} catch (NullPointerException imposs) {
			throw new IllegalArgumentException("impossible!");
		}
	}

}
