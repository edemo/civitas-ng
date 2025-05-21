package civitas.crypto.algorithms;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalDiscLogEqualityProof {
	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalProofDiscLogEqualityC apply(ElGamalParametersC params,
			CivitasBigInteger g1, CivitasBigInteger g2, CivitasBigInteger x) {

		CryptoFactoryC factory = CryptoFactoryC.singleton();

		CivitasBigInteger v = g1.modPow(x, params.p);
		CivitasBigInteger w = g2.modPow(x, params.p);

		CivitasBigInteger z = generateRandomElement.apply(params.q);
		CivitasBigInteger a = g1.modPow(z, params.p);
		CivitasBigInteger b = g2.modPow(z, params.p);

		List<CivitasBigInteger> l = new ArrayList<CivitasBigInteger>();
		l.add(v);
		l.add(w);
		l.add(a);
		l.add(b);
		CivitasBigInteger c = factory.hashToBigInt(factory.hash(l)).mod(params.q);

		CivitasBigInteger r = z.modAdd(c.modMultiply(x, params.q), params.q);

		return new ElGamalProofDiscLogEqualityC(g1, g2, a, v, w, b, c, r);
	}

}
