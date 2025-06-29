package civitas.common.mix.voteelementrevelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import civitas.common.mix.Mix;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import lombok.NonNull;

@Controller
public class VerifyMixVoteElementRevelation {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(@NonNull MixVoteElementRevelation that,
			@NonNull ElGamalPublicKey key, int fromIndex, int toIndex,
			@NonNull Mix fromMix, @NonNull Mix toMix) {
		if (!(fromMix instanceof VoteMix && toMix instanceof VoteMix)) {
			return false;
		}

		Vote fromVote = ((VoteMix) fromMix).votes[fromIndex];
		Vote toVote = ((VoteMix) toMix).votes[toIndex];
		ElGamalCiphertextish fromChoice = fromVote.encChoice;
		ElGamalCiphertextish fromCapability = fromVote.encCapability;
		ElGamalCiphertextish toChoice = toVote.encChoice;
		ElGamalCiphertextish toCapability = toVote.encCapability;

		ElGamalCiphertextish rechoice = elGamalReencrypt.apply(key, fromChoice,
				that.choiceFactor);
		ElGamalCiphertextish recapability = elGamalReencrypt.apply(key,
				fromCapability, that.reencryptFactor);
		return rechoice.equals(toChoice) && recapability.equals(toCapability);
	}

}
