package civitas.crypto.keypairshare;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateKeyPairShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalKeyPairShare apply(ElGamalParameters params) {
		// The zero knowledge proof is constructed later, in the call to
		// constructKeyShare
		ElGamalParameters ps = (ElGamalParameters) params;

		// choose x in Z_q at random. This is the share of the private key.
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		// the public part of the key is y
		CivitasBigInteger y = ps.g.modPow(x, ps.p);

		ElGamalPublicKey pub = new ElGamalPublicKey(y, params);
		ElGamalPrivateKey priv = new ElGamalPrivateKey(x, params);
		return new ElGamalKeyPairShare(params, pub, priv);
	}

}
