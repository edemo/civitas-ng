package civitas.common.mix.voteelementrevelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.EncryptedVote;
import civitas.common.mix.VoterMix;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import jakarta.annotation.Nonnull;

@Controller
public class VerifyMixVoteElementRevelation {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public boolean apply(@Nonnull MixVoteElementRevelation that,
			@Nonnull ElGamalPublicKey key, int fromIndex, int toIndex,
			@Nonnull VoterMix fromMix, @Nonnull VoterMix toMix) {
		if (null == fromMix || null == toMix) {
			throw new NullPointerException();
		}
		if (!(fromMix instanceof VoteMix && toMix instanceof VoteMix)) {
			return false;
		}

		EncryptedVote fromVote = ((VoteMix) fromMix).votes[fromIndex];
		EncryptedVote toVote = ((VoteMix) toMix).votes[toIndex];
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
