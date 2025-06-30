package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import jakarta.annotation.Nonnull;

@Controller
public class GetReencryptedVoteFromMix {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public Vote apply(@Nonnull VoteMix that, int i,
			@Nonnull ElGamalReencryptFactor choiceFactor,
			@Nonnull ElGamalReencryptFactor capabilityFactor,
			@Nonnull ElGamalPublicKey key) throws IndexOutOfBoundsException {
		if (null == choiceFactor || null == capabilityFactor || null == key)
			throw new NullPointerException();
		Vote v = that.votes[i];
		return new Vote(v.context,
				elGamalReencrypt.apply(key, v.encChoice, choiceFactor),
				elGamalReencrypt.apply(key, v.encCapability, capabilityFactor));
	}

}
