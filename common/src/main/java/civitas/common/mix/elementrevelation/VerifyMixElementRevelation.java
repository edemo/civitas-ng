package civitas.common.mix.elementrevelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.mix.Mix;
import civitas.common.mix.capabilityelementrevelation.MixCapabilityElementRevelation;
import civitas.common.mix.capabilityelementrevelation.VerifyMixCapabilityElementRevelation;
import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.common.mix.voteelementrevelation.MixVoteElementRevelation;
import civitas.common.mix.voteelementrevelation.VerifyMixVoteElementRevelation;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import jakarta.annotation.Nonnull;

@Controller
public class VerifyMixElementRevelation {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;
	@Autowired
	VerifyMixCapabilityElementRevelation verifyMixCapabilityElementRevelation;
	@Autowired
	VerifyMixVoteElementRevelation VerifyMixVoteElementRevelation;

	public boolean apply(@Nonnull MixElementRevelation that,
			@Nonnull ElGamalPublicKey key, int fromIndex, int toIndex,
			@Nonnull Mix fromMix, @Nonnull Mix toMix) {
		if (key == null || fromMix == null || toMix == null)
			throw new NullPointerException();
		if (that.getClass().equals(MixCapabilityElementRevelation.class))
			return verifyMixCapabilityElementRevelation.apply(
					(MixCapabilityElementRevelation) that, key, fromIndex, toIndex,
					(CapabilityMix) fromMix, (CapabilityMix) toMix);
		if (that.getClass().equals(MixVoteElementRevelation.class))
			return VerifyMixVoteElementRevelation.apply(
					(MixVoteElementRevelation) that, key, fromIndex, toIndex,
					(VoteMix) fromMix, (VoteMix) toMix);
		throw new IllegalArgumentException("I do not know this type of revelation");

	}

}
