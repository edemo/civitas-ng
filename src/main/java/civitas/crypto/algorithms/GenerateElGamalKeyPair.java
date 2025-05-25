package civitas.crypto.algorithms;

import civitas.crypto.ElGamalKeyPair;
import civitas.crypto.ElGamalKeyPairImpl;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
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
