package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.EncryptedVote;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import jakarta.annotation.Nonnull;

@Controller
public class GetReencryptedVoteFromMix {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public EncryptedVote apply(@Nonnull VoteMix that, int i,
			@Nonnull ElGamalReencryptFactor choiceFactor,
			@Nonnull ElGamalReencryptFactor capabilityFactor,
			@Nonnull ElGamalPublicKey key) {
		if (null == choiceFactor || null == capabilityFactor || null == key) {
			throw new NullPointerException();
		}
		EncryptedVote v = that.votes[i];
		return new EncryptedVote(v.context(),
				elGamalReencrypt.apply(key, v.encChoice(), choiceFactor),
				elGamalReencrypt.apply(key, v.encCapability(), capabilityFactor));
	}

}
