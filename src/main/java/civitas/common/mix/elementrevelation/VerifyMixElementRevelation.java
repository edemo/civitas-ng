package civitas.common.mix.elementrevelation;

import civitas.common.mix.Mix;
import civitas.common.mix.capabilityelementrevelation.MixCapabilityElementRevelation;
import civitas.common.mix.capabilityelementrevelation.VerifyMixCapabilityElementRevelation;
import civitas.common.mix.capabilitymix.CapabilityMix;
import civitas.common.mix.voteelementrevelation.MixVoteElementRevelation;
import civitas.common.mix.voteelementrevelation.VerifyMixVoteElementRevelation;
import civitas.common.mix.votemix.VoteMix;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Use;
import lombok.NonNull;

public class VerifyMixElementRevelation {

	@Use
	ElGamalReencrypt elGamalReencrypt;
	@Use
	VerifyMixCapabilityElementRevelation verifyMixCapabilityElementRevelation;
	@Use
	VerifyMixVoteElementRevelation VerifyMixVoteElementRevelation;

	public boolean apply(@NonNull MixElementRevelation that,
			@NonNull ElGamalPublicKey key, int fromIndex, int toIndex,
			@NonNull Mix fromMix, @NonNull Mix toMix) {
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
