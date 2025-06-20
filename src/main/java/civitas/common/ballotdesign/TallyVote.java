package civitas.common.ballotdesign;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;
import civitas.common.VoteChoice;
import civitas.common.tallystate.RecordBeat;
import civitas.common.tallystate.TallyState;
import civitas.crypto.CryptoException;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.parameters.DecodeChoice;
import civitas.util.CivitasBigInteger;

@Service
public class TallyVote implements CommonConstants {

	@Autowired
	RecordBeat recordBeat;
	@Autowired
	DecodeChoice decodeChoice;

	public void apply(String additionalcontext, ElGamalMsg msg,
			String currentcontext, TallyState state,
			Map<CivitasBigInteger, VoteChoice> decodeMap)
			throws IllegalArgumentException

	{

		String desiredContext = additionalcontext + KIND;
		if (currentcontext == null || !currentcontext.startsWith(desiredContext)) {
			throw new IllegalArgumentException("Incorrect context");
		}

		if (msg == null) {
			throw new IllegalArgumentException("Null message");
		}

		try {
			VoteChoice choice = decodeChoice.apply(decodeMap, msg.m);
			String suffix = currentcontext.substring(desiredContext.length());
			int ind = suffix.indexOf(':');
			try {
				int i = Integer.parseInt(suffix.substring(0, ind));
				int j = Integer.parseInt(suffix.substring(ind + 1));
				if (choice == VoteChoice.I_BEATS_J) {
					recordBeat.apply(state, i, j);
				}
				if (choice == VoteChoice.J_BEATS_I) {
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
