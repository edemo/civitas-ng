package civitas.crypto.algorithms;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProof1OfLC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalProof1OfL {
	@Use
	private static GenerateRandomElement generateRandomElement;

	public ElGamalProof1OfLC apply(ElGamalPublicKeyC key,
			ElGamalCiphertext[] ciphertexts, int L, int choice, ElGamalCiphertextC m,
			ElGamalReencryptFactorC factor) {
		CryptoFactoryC factory = CryptoFactoryC.singleton();

		ElGamalParametersC ps = (ElGamalParametersC) key.params;
		CivitasBigInteger u = m.a;
		CivitasBigInteger v = m.b;
		CivitasBigInteger r = factor.r;

		ElGamalCiphertextC[] ms = new ElGamalCiphertextC[L];
		for (int i = 0; i < L; i++) {
			ms[i] = (ElGamalCiphertextC) ciphertexts[i];
		}

		// choose d1 .. dL, and r1 ... rL at random.
		CivitasBigInteger[] ds = new CivitasBigInteger[L];
		CivitasBigInteger[] rs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			ds[i] = generateRandomElement.apply(ps.q);
			rs[i] = generateRandomElement.apply(ps.q);
		}

		// compute a_i's and b_i's
		CivitasBigInteger[] as = new CivitasBigInteger[L];
		CivitasBigInteger[] bs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			as[i] = ms[i].a.modDivide(u, ps.p).modPow(ds[i], ps.p)
					.modMultiply(ps.g.modPow(rs[i], ps.p), ps.p).mod(ps.p);
			bs[i] = ms[i].b.modDivide(v, ps.p).modPow(ds[i], ps.p)
					.modMultiply(key.y.modPow(rs[i], ps.p), ps.p).mod(ps.p);
		}

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
		CivitasBigInteger w = (r.modNegate(ps.q).modMultiply(ds[choice], ps.q))
				.modAdd(rs[choice], ps.q);
		CivitasBigInteger sum = CivitasBigInteger.ZERO;
		for (int i = 0; i < L; i++) {
			if (i != choice) {
				sum = sum.modAdd(ds[i], ps.q);
			}
		}
		CivitasBigInteger dprimet = c.modSubtract(sum, ps.q);
		CivitasBigInteger rprimet = w.modAdd(r.modMultiply(dprimet, ps.q), ps.q);

		CivitasBigInteger[] dvs = new CivitasBigInteger[L];
		CivitasBigInteger[] rvs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			if (i != choice) {
				dvs[i] = ds[i];
				rvs[i] = rs[i];
			} else {
				dvs[i] = dprimet;
				rvs[i] = rprimet;
			}
		}

		return new ElGamalProof1OfLC(L, dvs, rvs);
	}

}
