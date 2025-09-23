package civitas.crypto.keypairshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateKeyPairShare {

	@Autowired
	CryptoBase cryptoBase;

	public ElGamalKeyPairShare apply(final ElGamalParameters params) {
		ElGamalParameters ps = params;

		CivitasBigInteger x = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);

		ElGamalPublicKey pub = new ElGamalPublicKey(y, params);
		ElGamalPrivateKey priv = new ElGamalPrivateKey(x, params);
		return new ElGamalKeyPairShare(params, pub, priv);
	}
}
