package civitas.crypto.proofdvr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class VerifyElGamalProofDVR {

	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ConvertHashToBigInt convertHashToBigInt;

	public boolean apply(ElGamalProofDVR that, ElGamalPublicKey key,
			ElGamalPublicKey verifierKey) {

		ElGamalParameters ps = key.params;

        CivitasBigInteger hv = verifierKey.y;
		CivitasBigInteger h = key.y;
		CivitasBigInteger x = that.e.getA();
		CivitasBigInteger y = that.e.getB();
		CivitasBigInteger xp = that.eprime.getA();
		CivitasBigInteger yp = that.eprime.getB();

		/*
		 * a' = (g^u) / ((x'/x)^(c+w)) b' = (h^u) / ((y'/y)^(c+w)) s' =
		 * (g^w)*((h_v)^r) c' = hash(E||a'||b'||s')
		 */

		CivitasBigInteger ap = ps.g.modPow(that.u, ps.p).modDivide(
				xp.modDivide(x, ps.p).modPow(that.c.modAdd(that.w, ps.q), ps.p), ps.p);
		CivitasBigInteger bp = h.modPow(that.u, ps.p).modDivide(
				yp.modDivide(y, ps.p).modPow(that.c.modAdd(that.w, ps.q), ps.p), ps.p);
		CivitasBigInteger sp = ps.g.modPow(that.w, ps.p)
				.modMultiply(hv.modPow(that.r, ps.p), ps.p);

		List<CivitasBigInteger> l = new ArrayList<>();
		l.add(that.e.getA());
		l.add(that.e.getB());
		l.add(that.eprime.getA());
		l.add(that.eprime.getB());
		l.add(ap);
		l.add(bp);
		l.add(sp);
		CivitasBigInteger cp = convertHashToBigInt.apply(cryptoHash.apply(l))
				.mod(ps.q);

		return cp.equals(that.c);
	}

}
