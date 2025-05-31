package civitas.crypto.proofdvr;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class FakeElGamalProofDVR {

	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDVR apply(ElGamalCiphertext e, ElGamalCiphertext et,
			ElGamalPublicKey key, ElGamalPublicKey verifierKey,
			ElGamalPrivateKey verifierPrivKey) {

		ElGamalParameters ps = key.params;
		// CivitasBigInteger hv = verifierKey.y;
		CivitasBigInteger zv = verifierPrivKey.x;

		CivitasBigInteger h = key.y;
		CivitasBigInteger x = e.a;
		CivitasBigInteger y = e.b;
		CivitasBigInteger xt = et.a;
		CivitasBigInteger yt = et.b;

		/*
		 * A verifier can simulate a "proof" that any e~=(x~,y~) is a reencryption
		 * of e. Select \alpha, \beta, u~ at random from Z_q Compute: o a~ (g^u~) /
		 * ((x~/x)^(\alpha)) o b~ = (h^u~) / ((y~/y)^(\alpha)) o s~ = g^(\beta) o E~
		 * = e||e~ o c~ = hash(E~||a~||b~||s~) o w~ = \alpha - c~ (mod q) o r~ =
		 * (\beta - w~)/(z_v) (mod q) (c~, w~, r~, u~) will verify as a proof for
		 * E~.
		 */
		CivitasBigInteger alpha = generateRandomElement.apply(ps.q);
		CivitasBigInteger beta = generateRandomElement.apply(ps.q);
		CivitasBigInteger ut = generateRandomElement.apply(ps.q);

		CivitasBigInteger at = ps.g.modPow(ut, ps.p)
				.modDivide(xt.modDivide(x, ps.p).modPow(alpha, ps.p), ps.p);
		CivitasBigInteger bt = h.modPow(ut, ps.p)
				.modDivide(yt.modDivide(y, ps.p).modPow(alpha, ps.p), ps.p);
		CivitasBigInteger st = ps.g.modPow(beta, ps.p);

		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(e.a);
		l.add(e.b);
		l.add(et.a);
		l.add(et.b);
		l.add(at);
		l.add(bt);
		l.add(st);
		CivitasBigInteger ct = convertHashToBigInt.apply(cryptoHash.apply(l))
				.mod(ps.q);

		CivitasBigInteger wt = alpha.modSubtract(ct, ps.q);
		CivitasBigInteger rt = beta.modSubtract(wt, ps.q).modDivide(zv, ps.q);

		return new ElGamalProofDVR(e, et, ct, wt, rt, ut);

	}

	public ElGamalProofDVR apply(ElGamalPublicKey k, ElGamalPublicKey verifierKey,
			ElGamalPrivateKey verifierPrivKey, ElGamalCiphertext e,
			ElGamalCiphertext ePrime) {
		try {
			return apply(e, ePrime, k, verifierKey, verifierPrivKey);
		} catch (ClassCastException ex) {
			return null;
		}
	}

}
