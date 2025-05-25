package civitas.crypto.algorithms;

import java.util.ArrayList;
import java.util.List;

import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyElGamalProofDVR {

	@Use
	CryptoHash cryptoHash;

	public boolean apply(ElGamalProofDVRC that, ElGamalPublicKey K,
			ElGamalPublicKey verifierKey) {
		CryptoFactoryC factory = CryptoFactoryC.singleton();

		ElGamalParametersC ps = (ElGamalParametersC) K.getParams();
		ElGamalPublicKeyC key = (ElGamalPublicKeyC) K;

		CivitasBigInteger hv = ((ElGamalPublicKeyC) verifierKey).y;
		CivitasBigInteger h = key.y;
		CivitasBigInteger x = that.e.a;
		CivitasBigInteger y = that.e.b;
		CivitasBigInteger xp = that.eprime.a;
		CivitasBigInteger yp = that.eprime.b;

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

		List<CivitasBigInteger> l = new ArrayList<CivitasBigInteger>();
		l.add(that.e.a);
		l.add(that.e.b);
		l.add(that.eprime.a);
		l.add(that.eprime.b);
		l.add(ap);
		l.add(bp);
		l.add(sp);
		CivitasBigInteger cp = factory.hashToBigInt(cryptoHash.apply(l)).mod(ps.q);

		return cp.equals(that.c);
	}

}
