package civitas.common.mix.votemix;

import civitas.common.Vote;
import civitas.crypto.ciphertext.ElGamalReencrypt;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.Use;

public class GetReencryptedVoteMix {

	@Use
	ElGamalReencrypt elGamalReencrypt;

	public Vote apply(VoteMix that, int i, ElGamalReencryptFactor choiceFactor,
			ElGamalReencryptFactor capabilityFactor, ElGamalPublicKey key)
			throws IndexOutOfBoundsException {
		try {
			Vote v = that.votes[i];
			return new Vote(v.context,
					elGamalReencrypt.apply(key, v.encChoice, choiceFactor),
					elGamalReencrypt.apply(key, v.encCapability, capabilityFactor));
		} catch (NullPointerException e) {
			throw new IndexOutOfBoundsException();
		}
	}

}
