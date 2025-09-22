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
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructElGamalProofDVR {
	@Autowired
	CryptoBase cryptoBase;

	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	private ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDVR apply(
			ElGamalPublicKey k,
			ElGamalPublicKey verifierKey,
			ElGamalCiphertextish e,
			ElGamalCiphertext ePrime,
			ElGamalReencryptFactor er,
			ElGamalReencryptFactor erPrime) {
		ElGamalParameters ps = k.params;
		CivitasBigInteger zeta = erPrime.r().modSubtract(er.r(), ps.q);
		return apply(e, ePrime, k, verifierKey, zeta);
	}

	public ElGamalProofDVR apply(
			ElGamalCiphertextish e,
			ElGamalCiphertext eprime,
			ElGamalPublicKey key,
			ElGamalPublicKey verifierKey,
			CivitasBigInteger zeta) {

		ElGamalParameters ps = key.params;
		CivitasBigInteger d = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger w = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger r = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger h = key.y;
		CivitasBigInteger hv = verifierKey.y;
		CivitasBigInteger a = ps.g.modPow(d, ps.p);
		CivitasBigInteger b = h.modPow(d, ps.p);
		CivitasBigInteger s = ps.g.modPow(w, ps.p).modMultiply(hv.modPow(r, ps.p), ps.p);
		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(e.getA());
		l.add(e.getB());
		l.add(eprime.getA());
		l.add(eprime.getB());
		l.add(a);
		l.add(b);
		l.add(s);

		CivitasBigInteger c = convertHashToBigInt.apply(cryptoHash.apply(l)).mod(ps.q);

		CivitasBigInteger u = d.modAdd(zeta.modMultiply(c.modAdd(w, ps.q), ps.q), ps.q);

		return new ElGamalProofDVR(e, eprime, c, w, r, u);
	}
}
