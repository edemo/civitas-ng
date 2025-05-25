package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.VoteCapabilityShare;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.VoteCapabilityShareC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateVoteCapabilityShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public VoteCapabilityShare apply(ElGamalParameters p) throws CryptoError {
		ElGamalParametersC ps = (ElGamalParametersC) p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		try {
			return new VoteCapabilityShareC(x, ps);
		} catch (CryptoException imposs) {
			throw new CryptoError(imposs);
		}
	}

}
