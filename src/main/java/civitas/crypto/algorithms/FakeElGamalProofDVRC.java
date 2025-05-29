package civitas.crypto.algorithms;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPrivateKey;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class FakeElGamalProofDVRC {

	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDVRC apply(ElGamalCiphertextC e, ElGamalCiphertextC et,
			ElGamalPublicKeyC key, ElGamalPublicKeyC verifierKey,
			ElGamalPrivateKeyC verifierPrivKey) {

		ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
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

		List<CivitasBigInteger> l = new ArrayList<CivitasBigInteger>();
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

		return new ElGamalProofDVRC(e, et, ct, wt, rt, ut);

	}

	public ElGamalProofDVR apply(ElGamalPublicKey k, ElGamalPublicKey verifierKey,
			ElGamalPrivateKey verifierPrivKey, ElGamalCiphertext e,
			ElGamalCiphertext ePrime) {
		try {
			return apply((ElGamalCiphertextC) e, (ElGamalCiphertextC) ePrime,
					(ElGamalPublicKeyC) k, (ElGamalPublicKeyC) verifierKey,
					(ElGamalPrivateKeyC) verifierPrivKey);
		} catch (ClassCastException ex) {
			return null;
		}
	}

}
