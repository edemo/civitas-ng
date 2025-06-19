package civitas.crypto.votecapabilityshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoError;
import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Service
public class GenerateVoteCapabilityShare {

	@Autowired
	GenerateRandomElement generateRandomElement;
	@Autowired
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
