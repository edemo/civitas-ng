/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import civitas.crypto.*;
import civitas.util.CivitasBigInteger;

public class TestSchnorr {
	public static final CryptoFactoryC f = CryptoFactoryC.singleton();
	public static ElGamalParametersC ps;

	public static ElGamalMsgC m; 
	public static final String attack = "Attack at dawn";

	public static void test(String s, boolean b) {
		System.out.println(s + " ? " + (b ? "ok" : "oops !!!!!!!!!!!!!!!!!!!!!!!!!!!!"));
	}


	public static void main(String[] args) {
		genTest();
//		genTimeTest();
		msgTest();
		encTest();
		qrTest();
	}


	public static void genTest() {
		ps = (ElGamalParametersC) f.generateElGamalParameters(160, 512);
		test("p prime", ps.p.isProbablePrime(80));
		test("q prime", ps.q.isProbablePrime(80));
		test("2q divides p-1", ps.p.mod(ps.q.multiply(CivitasBigInteger.TWO)).equals(CivitasBigInteger.ONE));
		test("g is order q", ps.g.modPow(ps.q, ps.p).equals(CivitasBigInteger.ONE));
	}

	public static void genTimeTest() {
		long totalTime = 0;
		for (int i = 1; i <= 10; i++) {
			long start = System.currentTimeMillis();
			ps = (ElGamalParametersC) f.generateElGamalParameters(160, 1024);
			long end = System.currentTimeMillis();
			System.out.println("Elapsed time = " + (end-start)/1000.0 + " sec.");
			totalTime += (end-start);

			test("p prime", ps.p.isProbablePrime(80));
			test("q prime", ps.q.isProbablePrime(80));
			test("2q divides p-1", ps.p.mod(ps.q.multiply(CivitasBigInteger.TWO)).equals(CivitasBigInteger.ONE));
			test("g is order q", ps.g.modPow(ps.q, ps.p).equals(CivitasBigInteger.ONE));
		}
		System.out.println("Total time = " + totalTime/1000.0 + " sec.");
	}

	public static void msgTest() {
		try {
			m = new ElGamalMsgC(attack, ps);
		} catch (CryptoException e) {
			System.out.println("oops");
		}
		boolean caught = false;
		try {
			new ElGamalMsgC(ps.q.add(CivitasBigInteger.ONE), ps);
		} catch (CryptoException e) {
			System.out.println("Reject q+1 as message ? ok");
			caught = true;
		}
		if (!caught) {
			System.out.println("Reject q+1 as message ? oops");
		}
	} 

	public static void encTest() {
		ElGamalKeyPair p = f.generateElGamalKeyPair(ps);
		ElGamalPublicKey K = p.publicKey();
		ElGamalPrivateKey k = p.privateKey();
		ElGamalCiphertext c = f.elGamalEncrypt(K,m);
		ElGamalKeyPair p2 = f.generateElGamalKeyPair(ps);
		ElGamalPrivateKey k2 = p2.privateKey();

		try {
			ElGamalMsgC m1 = (ElGamalMsgC) f.elGamalDecrypt(k,c);
			test("Encryption correct", m.equals(m1));

			ElGamalMsgC m2 = (ElGamalMsgC) f.elGamalDecrypt(k2,c);
			test("Decryption under wrong key", !m.equals(m2));

			ElGamalCiphertext c2 = f.elGamalReencrypt(K, c);
			test("Reencryption changes ciphertext", !c.equals(c2));

			ElGamalMsgC m3 = (ElGamalMsgC) f.elGamalDecrypt(k,c2);
			test("Reencryption preserves message", m.equals(m3));

			ElGamalSignedCiphertextC c3 = (ElGamalSignedCiphertextC)f.elGamalSignedEncrypt(K, m);
			ElGamalMsgC m4 = (ElGamalMsgC)f.elGamalDecrypt(k,c);
			test("Signed encryption correct", m.equals(m4));

			boolean b = f.elGamalVerify(ps, c3);
			test("Scnorr signature checks", b);

			ElGamalSignedCiphertext c4 = new ElGamalSignedCiphertextC(c3.a, c3.b, CivitasBigInteger.ONE, CivitasBigInteger.ONE);
			boolean b2 = f.elGamalVerify(ps, c4);
			test("Corrupted signature detected", !b2);

		} catch (CryptoException e) {
			System.out.println("oops: " + e);
		}
	}

	public static void qrTest() {
		ElGamalParametersC ps2 = (ElGamalParametersC) f.generateElGamalParameters(160);
		try {
			ElGamalMsgC m2 = (ElGamalMsgC) f.elGamalMsg("Attack at dawn", ps2); 
			test("Decode QR", m2.plaintextStringValue(ps2).equals(attack));
		} catch (CryptoException ce) {
			System.out.println("oops: " + ce);
		}
	}

	public static CivitasBigInteger findQR() {
		return findQR(1);
	}

	public static CivitasBigInteger findNonQR() {
		return findQR(-1);
	}

	/**
	 * @param flag 1 to find QR, -1 to find non-QR
	 */
	public static CivitasBigInteger findQR(int flag) {
		CivitasBigInteger i = null;
		do {
			i = CryptoAlgs.randomElement(ps.q);
		} while (CryptoAlgs.legendreSymbol(i, ps.p, ps.q) != flag);
		return i;
	}

}
