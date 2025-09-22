package civitas.crypto.proofdvr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class FakeElGamalProofDVR {

	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDVR apply(
			ElGamalPublicKey key,
			ElGamalPublicKey verifierKey,
			ElGamalPrivateKey verifierPrivKey,
			ElGamalCiphertextish e,
			ElGamalCiphertext ePrime) {
		return apply(e, ePrime, key, verifierKey, verifierPrivKey);
	}

	public ElGamalProofDVR apply(
			ElGamalCiphertextish e,
			ElGamalCiphertext et,
			ElGamalPublicKey key,
			ElGamalPublicKey verifierKey,
			ElGamalPrivateKey verifierPrivKey) {

		ElGamalParameters ps = key.params;
		// CivitasBigInteger hv = verifierKey.y;
		CivitasBigInteger zv = verifierPrivKey.x();

		CivitasBigInteger h = key.y;
		CivitasBigInteger x = e.getA();
		CivitasBigInteger y = e.getB();
		CivitasBigInteger xt = et.getA();
		CivitasBigInteger yt = et.getB();

		CivitasBigInteger alpha = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger beta = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger ut = cryptoBase.generateRandomElement(ps.q);

		CivitasBigInteger at =
				ps.g.modPow(ut, ps.p).modDivide(xt.modDivide(x, ps.p).modPow(alpha, ps.p), ps.p);
		CivitasBigInteger bt =
				h.modPow(ut, ps.p).modDivide(yt.modDivide(y, ps.p).modPow(alpha, ps.p), ps.p);
		CivitasBigInteger st = ps.g.modPow(beta, ps.p);

		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(e.getA());
		l.add(e.getB());
		l.add(et.getA());
		l.add(et.getB());
		l.add(at);
		l.add(bt);
		l.add(st);
		CivitasBigInteger ct = convertHashToBigInt.apply(cryptoHash.apply(l)).mod(ps.q);

		CivitasBigInteger wt = alpha.modSubtract(ct, ps.q);
		CivitasBigInteger rt = beta.modSubtract(wt, ps.q).modDivide(zv, ps.q);

		return new ElGamalProofDVR(e, et, ct, wt, rt, ut);
	}
}
