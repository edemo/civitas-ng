package civitas.crypto.votecapabilityshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoException;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateVoteCapabilityShare {

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	EncodeMessage encodeMessage;

	public VoteCapabilityShare apply(final ElGamalParameters p) throws CryptoException {
		ElGamalParameters ps = p;
		CivitasBigInteger x = cryptoBase.generateRandomElement(ps.q);
		return new VoteCapabilityShare(encodeMessage.apply(x, ps));
	}
}
