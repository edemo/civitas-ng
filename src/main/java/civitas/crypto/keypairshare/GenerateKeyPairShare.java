package civitas.crypto.keypairshare;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateKeyPairShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalKeyPairShare apply(ElGamalParameters params) {
		ElGamalParameters ps = params;

		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);

		ElGamalPublicKey pub = new ElGamalPublicKey(y, params);
		ElGamalPrivateKey priv = new ElGamalPrivateKey(x, params);
		return new ElGamalKeyPairShare(params, pub, priv);
	}

}
