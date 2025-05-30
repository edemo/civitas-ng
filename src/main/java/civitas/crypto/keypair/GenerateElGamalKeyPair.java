package civitas.crypto.keypair;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.privatekey.ElGamalPrivateKeyC;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateElGamalKeyPair {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalKeyPair apply(ElGamalParameters p) {
		ElGamalParametersC ps = (ElGamalParametersC) p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);
		ElGamalPrivateKeyC k = new ElGamalPrivateKeyC(x, ps);
		ElGamalPublicKeyC K = new ElGamalPublicKeyC(y, ps);
		return new ElGamalKeyPairImpl(K, k);
	}

}
