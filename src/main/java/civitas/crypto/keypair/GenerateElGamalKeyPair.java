package civitas.crypto.keypair;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateElGamalKeyPair {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalKeyPair apply(ElGamalParameters p) {
		ElGamalParametersC ps = (ElGamalParametersC) p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);
		ElGamalPrivateKey k = new ElGamalPrivateKey(x, ps);
		ElGamalPublicKey K = new ElGamalPublicKey(y, ps);
		return new ElGamalKeyPair(K, k);
	}

}
