/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalProofDVR;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;
import civitas.util.Use;

public class ElGamalProofDVRC implements ElGamalProofDVR {
	@Use
	private static GenerateRandomElement generateRandomElement = DI
			.get(GenerateRandomElement.class);

	private final ElGamalCiphertextC e;
	private final ElGamalCiphertextC eprime;
	public final CivitasBigInteger c;
	public final CivitasBigInteger w;
	public final CivitasBigInteger r;
	public final CivitasBigInteger u;

	public ElGamalProofDVRC(ElGamalCiphertextC e, ElGamalCiphertextC eprime,
			CivitasBigInteger c, CivitasBigInteger w, CivitasBigInteger r,
			CivitasBigInteger u) {
		this.e = e;
		this.eprime = eprime;
		this.c = c;
		this.w = w;
		this.r = r;
		this.u = u;
	}

	public static ElGamalProofDVRC constructProof(ElGamalCiphertextC e,
			ElGamalCiphertextC eprime, ElGamalPublicKeyC key,
			ElGamalPublicKeyC verifierKey, CivitasBigInteger zeta) {

		CryptoFactoryC factory = CryptoFactoryC.singleton();

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
		List<CivitasBigInteger> l = new ArrayList<CivitasBigInteger>();
		l.add(e.a);
		l.add(e.b);
		l.add(eprime.a);
		l.add(eprime.b);
		l.add(a);
		l.add(b);
		l.add(s);
		CivitasBigInteger c = factory.hashToBigInt(factory.hash(l)).mod(ps.q);

		CivitasBigInteger u = d.modAdd(zeta.modMultiply(c.modAdd(w, ps.q), ps.q),
				ps.q);

		return new ElGamalProofDVRC(e, eprime, c, w, r, u);

	}

	public static ElGamalProofDVRC fakeProof(ElGamalCiphertextC e,
			ElGamalCiphertextC et, ElGamalPublicKeyC key,
			ElGamalPublicKeyC verifierKey, ElGamalPrivateKeyC verifierPrivKey) {
		CryptoFactoryC factory = CryptoFactoryC.singleton();

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
		CivitasBigInteger ct = factory.hashToBigInt(factory.hash(l)).mod(ps.q);

		CivitasBigInteger wt = alpha.modSubtract(ct, ps.q);
		CivitasBigInteger rt = beta.modSubtract(wt, ps.q).modDivide(zv, ps.q);

		return new ElGamalProofDVRC(e, et, ct, wt, rt, ut);

	}

	@Override
	public boolean verify(ElGamalPublicKey K, ElGamalPublicKey verifierKey) {
		CryptoFactoryC factory = CryptoFactoryC.singleton();

		ElGamalParametersC ps = (ElGamalParametersC) K.getParams();
		ElGamalPublicKeyC key = (ElGamalPublicKeyC) K;

		CivitasBigInteger hv = ((ElGamalPublicKeyC) verifierKey).y;
		CivitasBigInteger h = key.y;
		CivitasBigInteger x = e.a;
		CivitasBigInteger y = e.b;
		CivitasBigInteger xp = eprime.a;
		CivitasBigInteger yp = eprime.b;

		/*
		 * a' = (g^u) / ((x'/x)^(c+w)) b' = (h^u) / ((y'/y)^(c+w)) s' =
		 * (g^w)*((h_v)^r) c' = hash(E||a'||b'||s')
		 */

		CivitasBigInteger ap = ps.g.modPow(u, ps.p)
				.modDivide(xp.modDivide(x, ps.p).modPow(c.modAdd(w, ps.q), ps.p), ps.p);
		CivitasBigInteger bp = h.modPow(u, ps.p)
				.modDivide(yp.modDivide(y, ps.p).modPow(c.modAdd(w, ps.q), ps.p), ps.p);
		CivitasBigInteger sp = ps.g.modPow(w, ps.p).modMultiply(hv.modPow(r, ps.p),
				ps.p);

		List<CivitasBigInteger> l = new ArrayList<CivitasBigInteger>();
		l.add(e.a);
		l.add(e.b);
		l.add(eprime.a);
		l.add(eprime.b);
		l.add(ap);
		l.add(bp);
		l.add(sp);
		CivitasBigInteger cp = factory.hashToBigInt(factory.hash(l)).mod(ps.q);

		return cp.equals(c);
	}

	@Override
	public ElGamalCiphertext getE() {
		return e;
	}

	@Override
	public ElGamalCiphertext getEprime() {
		return eprime;
	}

	public String toXML() {
		StringWriter sb = new StringWriter();
		toXML(new PrintWriter(sb));
		return sb.toString();
	}

	@Override
	public void toXML(PrintWriter s) {
		s.print("<elGamalProofDVR>");
		e.toXML(s);
		eprime.toXML(s);
		s.print("<c>");
		Util.escapeString(CryptoFactoryC.bigIntToString(c), s);
		s.print("</c>");
		s.print("<w>");
		Util.escapeString(CryptoFactoryC.bigIntToString(w), s);
		s.print("</w>");
		s.print("<r>");
		Util.escapeString(CryptoFactoryC.bigIntToString(r), s);
		s.print("</r>");
		s.print("<u>");
		Util.escapeString(CryptoFactoryC.bigIntToString(u), s);
		s.print("</u>");
		s.print("</elGamalProofDVR>");
	}

	public static ElGamalProofDVR fromXML(Reader reader)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(reader, "elGamalProofDVR");
		ElGamalCiphertextC e = (ElGamalCiphertextC) CryptoFactoryC.singleton()
				.elGamalCiphertextFromXML(reader);
		ElGamalCiphertextC eprime = (ElGamalCiphertextC) CryptoFactoryC.singleton()
				.elGamalCiphertextFromXML(reader);
		CivitasBigInteger c = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "c")));
		CivitasBigInteger w = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "w")));
		CivitasBigInteger r = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "r")));
		CivitasBigInteger u = CryptoFactoryC
				.stringToBigInt(Util.unescapeString(Util.readSimpleTag(reader, "u")));

		Util.swallowEndTag(reader, "elGamalProofDVR");
		return new ElGamalProofDVRC(e, eprime, c, w, r, u);
	}

}
