package civitas.crypto.votecapabilityshare;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateVoteCapabilityShare {

	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	EncodeMessage encodeMessage;

	public VoteCapabilityShare apply(ElGamalParameters p) throws CryptoError {
		ElGamalParameters ps = p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		try {
			return new VoteCapabilityShare(encodeMessage.apply(x, ps));
		} catch (CryptoException imposs) {
			throw new CryptoError(imposs);
		}
	}

}
