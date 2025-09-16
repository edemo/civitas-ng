package civitas.crypto.decriptionshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructElGamalDecryptionShare {

	@Autowired
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public ElGamalDecryptionShare apply(ElGamalCiphertextish c,
			ElGamalKeyPairShare keyShare) {
		ElGamalPrivateKey priv = keyShare.privKey();
		ElGamalParameters params = priv.params();
		CivitasBigInteger ai = c.getA().modPow(priv.x(), params.p());
		return new ElGamalDecryptionShare(ai, constructElGamalDiscLogEqualityProof
				.apply(params, c.getA(), params.g(), priv.x()));
	}

}
