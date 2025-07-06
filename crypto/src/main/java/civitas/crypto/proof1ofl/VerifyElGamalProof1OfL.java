package civitas.crypto.proof1ofl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class VerifyElGamalProof1OfL {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ConvertHashToBigInt convertHashToBigInt;

	public boolean apply(ElGamalProof1OfL self, ElGamalPublicKey pubKey,
			CiphertextList ciphertexts, int L, ElGamalCiphertextish msg) {
		if (self.L != L)
			return false;
		ElGamalCiphertextish m = msg;
		CivitasBigInteger u = m.getA();
		CivitasBigInteger v = m.getB();
		ElGamalPublicKey key = pubKey;
		ElGamalParameters ps = key.params;
		ElGamalCiphertextish[] ms = new ElGamalCiphertext[L];

		for (int i = 0; i < L; i++) {
			ms[i] = ciphertexts.get(i);
		}

		CivitasBigInteger[] as = new CivitasBigInteger[L];
		CivitasBigInteger[] bs = new CivitasBigInteger[L];
		CivitasBigInteger sum = Constants.ZERO;
		for (int i = 0; i < L; i++) {
			as[i] = (ms[i].getA().modDivide(u, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(ps.g.modPow(self.rvs[i], ps.p), ps.p);
			bs[i] = (ms[i].getB().modDivide(v, ps.p)).modPow(self.dvs[i], ps.p)
					.modMultiply(key.y.modPow(self.rvs[i], ps.p), ps.p);
			sum = sum.modAdd(self.dvs[i], ps.q);
		}

		// construct the hash of the environment
		List<CivitasBigInteger> env = new ArrayList<>();
		env.add(u);
		env.add(v);
		for (int i = 0; i < L; i++) {
			env.add(ms[i].getA());
			env.add(ms[i].getB());
			env.add(as[i]);
			env.add(bs[i]);
		}

		byte[] hashBytes = cryptoHash.apply(env);
		CivitasBigInteger c = convertHashToBigInt.apply(hashBytes).mod(ps.q);
		return sum.equals(c);
	}

}
