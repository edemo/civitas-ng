package civitas.crypto.proofdvr;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalProofDVR {
	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	private ConvertHashToBigInt convertHashToBigInt;

	public ElGamalProofDVR apply(ElGamalPublicKey k, ElGamalPublicKey verifierKey,
			ElGamalCiphertext e, ElGamalCiphertext ePrime, ElGamalReencryptFactor er,
			ElGamalReencryptFactor erPrime) {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) k.getParams();
			CivitasBigInteger zeta = ((ElGamalReencryptFactorC) erPrime).r
					.modSubtract(((ElGamalReencryptFactorC) er).r, ps.q);
			return apply((ElGamalCiphertext) e, (ElGamalCiphertext) ePrime,
					(ElGamalPublicKeyC) k, (ElGamalPublicKeyC) verifierKey, zeta);
		} catch (ClassCastException ex) {
			return null;
		}
	}

	public ElGamalProofDVRC apply(ElGamalCiphertext e, ElGamalCiphertext eprime,
			ElGamalPublicKeyC key, ElGamalPublicKeyC verifierKey,
			CivitasBigInteger zeta) {

// check that the inputs are correct
//        if (!factory.elGamalReencrypt(key, e, new ElGamalReencryptFactorC(zeta)).equals(eprime)) {
//            throw new CryptoError("Incorrect value for zeta passed in");
//        }

		ElGamalParametersC ps = (ElGamalParametersC) key.getParams();
		CivitasBigInteger d = generateRandomElement.apply(ps.q);
		CivitasBigInteger w = generateRandomElement.apply(ps.q);
		CivitasBigInteger r = generateRandomElement.apply(ps.q);
		CivitasBigInteger h = key.y;
		CivitasBigInteger hv = verifierKey.y;
		CivitasBigInteger a = ps.g.modPow(d, ps.p);
		CivitasBigInteger b = h.modPow(d, ps.p);
		CivitasBigInteger s = ps.g.modPow(w, ps.p).modMultiply(hv.modPow(r, ps.p),
				ps.p);
		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(e.a);
		l.add(e.b);
		l.add(eprime.a);
		l.add(eprime.b);
		l.add(a);
		l.add(b);
		l.add(s);

		CivitasBigInteger c = convertHashToBigInt.apply(cryptoHash.apply(l))
				.mod(ps.q);

		CivitasBigInteger u = d.modAdd(zeta.modMultiply(c.modAdd(w, ps.q), ps.q),
				ps.q);

		return new ElGamalProofDVRC(e, eprime, c, w, r, u);

	}

}
