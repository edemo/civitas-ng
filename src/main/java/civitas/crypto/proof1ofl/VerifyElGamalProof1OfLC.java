package civitas.crypto.proof1ofl;

import java.util.ArrayList;
import java.util.List;

import civitas.common.CiphertextList;
import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyElGamalProof1OfLC {
	@Use
	CryptoHash cryptoHash;
	@Use
	ConvertHashToBigInt convertHashToBigInt;

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

		CivitasBigInteger[] as = new CivitasBigInteger[L];
		CivitasBigInteger[] bs = new CivitasBigInteger[L];
		CivitasBigInteger sum = Constants.ZERO;
		for (int i = 0; i < L; i++) {
			as[i] = (ms[i].a.modDivide(u, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(ps.g.modPow(self.rvs[i], ps.p), ps.p);
			bs[i] = (ms[i].b.modDivide(v, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(key.y.modPow(self.rvs[i], ps.p), ps.p);
			sum = sum.modAdd(self.dvs[i], ps.q);
		}

		// construct the hash of the environment
		List<CivitasBigInteger> env = new ArrayList<>(2 + 4 * L);
		env.add(u);
		env.add(v);
		for (int i = 0; i < L; i++) {
			env.add(ms[i].a);
			env.add(ms[i].b);
			env.add(as[i]);
			env.add(bs[i]);
		}

		CivitasBigInteger c = convertHashToBigInt.apply(cryptoHash.apply(env))
				.mod(ps.q);
		return sum.equals(c);
	}

}
