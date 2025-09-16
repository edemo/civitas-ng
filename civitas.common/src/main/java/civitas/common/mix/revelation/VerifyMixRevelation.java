package civitas.common.mix.revelation;

import java.math.BigInteger;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.mix.VoterMix;
import civitas.common.mix.elementrevelation.MixElementRevelation;
import civitas.common.mix.elementrevelation.VerifyMixElementRevelation;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.publickey.ElGamalPublicKey;

@Controller
public class VerifyMixRevelation {
	@Autowired
	VerifyMixElementRevelation verifyMixElementRevelation;
	@Autowired
	CryptoHash cryptoHash;

	public boolean apply(MixRevelation that, ElGamalPublicKey key, VoterMix initialMix,
			VoterMix leftMix, VoterMix rightMix, boolean[] revelationDirections) {
		if (revelationDirections == null
				|| that.revelations.length != revelationDirections.length
				|| that.revelations.length != that.indicators.length) {
			return false;
		}

		for (int i = 0; i < that.revelations.length; i++) {
			if (revelationDirections[i] != that.indicators[i]) {
				return false;
			}

			MixElementRevelation mer = that.revelations[i];
			if (mer == null) {
				return false;
			}
			int fromIndex = -1;
			int toIndex = -1;
			VoterMix fromMix = null;
			VoterMix toMix = null;

			if (revelationDirections[i]) {
				// taken from the right
				fromMix = leftMix;
				toMix = rightMix;
				fromIndex = i;
				toIndex = mer.getMapping();
			} else {
				// taken from the left
				fromMix = initialMix;
				toMix = leftMix;
				fromIndex = mer.getMapping();
				toIndex = i;
			}
			byte[] commitment = toMix.getCommitments()[toIndex].clone();
			byte[] mappingBytes = BigInteger.valueOf(mer.getMapping()).toByteArray();
			byte[] hash = cryptoHash.apply(mer.getNonce(),
					mappingBytes);
			if (!Arrays.equals(commitment, hash) || !verifyMixElementRevelation
					.apply(mer, key, fromIndex, toIndex, fromMix, toMix)) {
				return false;
			}
		}

		return true;
	}

}
