package civitas.crypto.proofdvr;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.messagedigest.CryptoHash;
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

	public ElGamalProofDVR apply(ElGamalPublicKey key,
			ElGamalPublicKey verifierKey, ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertext e, ElGamalCiphertext ePrime) {
		return apply(e, ePrime, key, verifierKey, verifierPrivKey);
	}

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

}
