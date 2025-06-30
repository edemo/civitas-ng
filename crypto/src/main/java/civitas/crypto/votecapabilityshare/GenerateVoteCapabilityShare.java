package civitas.crypto.votecapabilityshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoException;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateVoteCapabilityShare {

	@Autowired
	GenerateRandomElement generateRandomElement;
	@Autowired
	EncodeMessage encodeMessage;

	public VoteCapabilityShare apply(ElGamalParameters p) throws CryptoException {
		ElGamalParameters ps = p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		return new VoteCapabilityShare(encodeMessage.apply(x, ps));
	}

}
