package civitas.common.mix.voteelementrevelation;

import civitas.common.Vote;
import civitas.common.mix.Mix;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;
import lombok.NonNull;

public class VerifyMixVoteElementRevelation {

	@Use
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(@NonNull MixVoteElementRevelation that,
			@NonNull ElGamalPublicKey key, int fromIndex, int toIndex,
			@NonNull Mix fromMix, @NonNull Mix toMix) {
		if (!(fromMix instanceof VoteMix && toMix instanceof VoteMix)) {
			return false;
		}

		Vote fromVote = ((VoteMix) fromMix).votes[fromIndex];
		Vote toVote = ((VoteMix) toMix).votes[toIndex];
		ElGamalCiphertext fromChoice = fromVote.encChoice;
		ElGamalCiphertext fromCapability = fromVote.encCapability;
		ElGamalCiphertext toChoice = toVote.encChoice;
		ElGamalCiphertext toCapability = toVote.encCapability;

		ElGamalCiphertext rechoice = elGamalReencrypt.apply(key, fromChoice,
				that.choiceFactor);
		ElGamalCiphertext recapability = elGamalReencrypt.apply(key, fromCapability,
				that.reencryptFactor);

		return rechoice.equals(toChoice) && recapability.equals(toCapability);
	}

}
