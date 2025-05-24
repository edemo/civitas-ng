package civitas.crypto.algorithms;

import java.util.ArrayList;
import java.util.List;

import civitas.common.CiphertextList;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;

public class VerifyElGamalProof1OfLC {
	public boolean apply(ElGamalProof1OfLC self, ElGamalPublicKey pubKey,
			CiphertextList ciphertexts, int L, ElGamalCiphertext msg) {
		if (self.L != L)
			return false;
		ElGamalCiphertextC m = (ElGamalCiphertextC) msg;
		CivitasBigInteger u = m.a;
		CivitasBigInteger v = m.b;
		ElGamalPublicKeyC key = (ElGamalPublicKeyC) pubKey;
		ElGamalParametersC ps = (ElGamalParametersC) key.params;
		ElGamalCiphertextC[] ms = new ElGamalCiphertextC[L];

		for (int i = 0; i < L; i++) {
			ms[i] = (ElGamalCiphertextC) ciphertexts.get(i);
		}

		CryptoFactoryC factory = CryptoFactoryC.singleton();
		CivitasBigInteger[] as = new CivitasBigInteger[L];
		CivitasBigInteger[] bs = new CivitasBigInteger[L];
		CivitasBigInteger sum = CivitasBigInteger.ZERO;
		for (int i = 0; i < L; i++) {
			as[i] = (ms[i].a.modDivide(u, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(ps.g.modPow(self.rvs[i], ps.p), ps.p);
			bs[i] = (ms[i].b.modDivide(v, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(key.y.modPow(self.rvs[i], ps.p), ps.p);
			sum = sum.modAdd(self.dvs[i], ps.q);
		}

		// construct the hash of the environment
		List<CivitasBigInteger> env = new ArrayList<CivitasBigInteger>(2 + 4 * L);
		env.add(u);
		env.add(v);
		for (int i = 0; i < L; i++) {
			env.add(ms[i].a);
			env.add(ms[i].b);
			env.add(as[i]);
			env.add(bs[i]);
		}
		CivitasBigInteger c = factory.hashToBigInt(factory.hash(env)).mod(ps.q);
		return sum.equals(c);
	}

}
