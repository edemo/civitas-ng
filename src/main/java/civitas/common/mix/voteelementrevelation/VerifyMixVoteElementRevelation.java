package civitas.common.mix.voteelementrevelation;

import civitas.common.Vote;
import civitas.common.mix.Mix;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;

public class VerifyMixVoteElementRevelation {

	@Use
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(MixVoteElementRevelation that, ElGamalPublicKey key,
			int fromIndex, int toIndex, Mix fromMix, Mix toMix) {
		if (!(fromMix instanceof VoteMix && toMix instanceof VoteMix)) {
			return false;
		}

		try {
			Vote fromVote = ((VoteMix) fromMix).votes[fromIndex];
			Vote toVote = ((VoteMix) toMix).votes[toIndex];

			ElGamalCiphertext fromChoice = fromVote.encChoice;
			ElGamalCiphertext fromCapability = fromVote.encCapability;
			ElGamalCiphertext toChoice = toVote.encChoice;
			ElGamalCiphertext toCapability = toVote.encCapability;

			ElGamalCiphertext rechoice = elGamalReencrypt.apply(key, fromChoice,
					that.choiceFactor);
			ElGamalCiphertext recapability = elGamalReencrypt.apply(key,
					fromCapability, that.reencryptFactor);
			return rechoice.equals(toChoice) && recapability.equals(toCapability);
		} catch (NullPointerException e) {
			return false;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

}
