package civitas.crypto.proof1ofl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Service
public class ConstructElGamalProof1OfL implements Constants {
	@Autowired
	public GenerateRandomElement generateRandomElement;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	private ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProof1OfL apply(ElGamalPublicKey key,
			CiphertextList ciphertexts, int L, int choice, ElGamalCiphertextish m,
			ElGamalReencryptFactor factor) {

		ElGamalParameters ps = key.params;
		CivitasBigInteger u = m.getA();
		CivitasBigInteger v = m.getB();
		CivitasBigInteger r = factor.r;

		ElGamalCiphertextish[] ms = new ElGamalCiphertext[L];
		for (int i = 0; i < L; i++) {
			ms[i] = ciphertexts.get(i);
		}

		// choose d1 .. dL, and r1 ... rL at random.
		CivitasBigInteger[] ds = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			ds[i] = generateRandomElement.apply(ps.q);
		}
		CivitasBigInteger[] rs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			rs[i] = generateRandomElement.apply(ps.q);
		}

		// compute a_i's and b_i's
		CivitasBigInteger[] as = new CivitasBigInteger[L];
		CivitasBigInteger[] bs = new CivitasBigInteger[L];
		for (int i = 0; i < L; i++) {
			as[i] = ms[i].getA().modDivide(u, ps.p).modPow(ds[i], ps.p)
					.modMultiply(ps.g.modPow(rs[i], ps.p), ps.p).mod(ps.p);
			bs[i] = ms[i].getB().modDivide(v, ps.p).modPow(ds[i], ps.p)
					.modMultiply(key.y.modPow(rs[i], ps.p), ps.p).mod(ps.p);
		}

		List<CivitasBigInteger> env = new ArrayList<>(2 + 4 * L);
		env.add(u);
		env.add(v);
		for (int i = 0; i < L; i++) {
			env.add(ms[i].getA());
			env.add(ms[i].getB());
			env.add(as[i]);
			env.add(bs[i]);
		}
		CivitasBigInteger c = convertHashToBigInt.apply(cryptoHash.apply(env))
				.mod(ps.q);
		CivitasBigInteger w = (r.modNegate(ps.q).modMultiply(ds[choice], ps.q))
				.modAdd(rs[choice], ps.q);
		CivitasBigInteger sum = ZERO;
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

		return new ElGamalProof1OfL(L, dvs, rvs);
	}

}
