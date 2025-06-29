package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.Vote;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import lombok.NonNull;

@Controller
public class GetReencryptedVoteFromMix {

	@Autowired
	ElGamalReencrypt elGamalReencrypt;

	public Vote apply(@NonNull VoteMix that, int i,
			@NonNull ElGamalReencryptFactor choiceFactor,
			@NonNull ElGamalReencryptFactor capabilityFactor,
			@NonNull ElGamalPublicKey key) throws IndexOutOfBoundsException {
		Vote v = that.votes[i];
		return new Vote(v.context,
				elGamalReencrypt.apply(key, v.encChoice, choiceFactor),
				elGamalReencrypt.apply(key, v.encCapability, capabilityFactor));
	}

}
