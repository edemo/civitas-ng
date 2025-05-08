/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import jif.lang.Label;
import jif.lang.LabelUtil;
import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ProofVote;
import civitas.util.CivitasBigInteger;

/**
 * This is a "non-malleable" (in some informal sense), NIZK proof of 
 * knowledge of the voter's vote, including its capability, choice, and 
 * context. The prover here is the voter, and the verifier is any auditor 
 * (including tabulation teller) of the BB. The core of it is a conjunction 
 * of two Schnorr signatures. The basic design is due to [Jan Camenisch 
 * and Markus Stadler. Efficient Group Signatures for Large Groups.]
 */
class ProofVoteC implements ProofVote {
    /*
     *  Public inputs
          o ElGamal parameters (p,g)
          o Encrypted capability = (a1,b1)
          o Encrypted choice = (a2,b2)
          o Vote context ctx
          o Let proof environment E = (g,a1,b1,a2,b2,ctx) 
     * Prover private inputs:
          o alpha1, alpha2 s.t. ai = g^{alphai} 
     * Prover:
          o Select r1,r2 at random from Z_q
          o Compute:
                + c = hash(E,g^r1,g^r2)
                + s1 = r1 - c*alpha1 mod p
                + s2 = r2 - c*alpha2 mod p 
     * Prover -> Verifier: (c,s1,s2)
     * Verifier:
          o Check c = hash(E, g^s1 * a1^c, g^s2 * a2^c) 

     */

    final CivitasBigInteger c;
    final CivitasBigInteger s1;
    final CivitasBigInteger s2;

    ProofVoteC(final CivitasBigInteger c, final CivitasBigInteger s1, final CivitasBigInteger s2) {
        this.c = c;
        this.s1 = s1;
        this.s2 = s2;
    }

    ProofVoteC(ElGamalParametersC params, 
               ElGamalCiphertextC encCapability, ElGamalCiphertextC encChoice, 
               String context, 
               ElGamalReencryptFactorC alpha1, ElGamalReencryptFactorC alpha2)
               {
        CryptoFactoryC factory = CryptoFactoryC.singleton();

        CivitasBigInteger r1 = CryptoAlgs.randomElement(params.q);
        CivitasBigInteger r2 = CryptoAlgs.randomElement(params.q);

        List<CivitasBigInteger> E = proofEnv(params, encCapability, encChoice, context);
        E.add(params.g.modPow(r1, params.p));
        E.add(params.g.modPow(r2, params.p));
//      System.err.println("Adding more");
//      System.err.println("   " + params.g.modPow(r1, params.p));
//      System.err.println("   " + params.g.modPow(r2, params.p));
        
        c = factory.hashToBigInt(factory.hash(E)).mod(params.q);
        s1 = r1.modSubtract(c.modMultiply(alpha1.r, params.q), params.q);
        s2 = r2.modSubtract(c.modMultiply(alpha2.r, params.q), params.q);
//      System.err.println(" c =  " + c);
//      System.err.println(" s1=  " + s1);
//      System.err.println(" s2=  " + s2);
               }

    List<CivitasBigInteger> proofEnv(ElGamalParametersC params, 
                                  ElGamalCiphertextC encCapability, ElGamalCiphertextC encChoice, 
                                  String context) 
                                  {
        CryptoFactoryC factory = CryptoFactoryC.singleton();
        List<CivitasBigInteger> E = new LinkedList<CivitasBigInteger>();
        E.add(params.g);
        E.add(encCapability.a);
        E.add(encCapability.b);
        E.add(encChoice.a);
        E.add(encChoice.b);
        E.add(factory.hashToBigInt(factory.messageDigest(LabelUtil.singleton().noComponents(), context.getBytes())));

//      System.err.println("Constructing proof env");
//      System.err.println("   " + params.g);
//      System.err.println("   " + encCapability.a);
//      System.err.println("   " + encCapability.b);
//      System.err.println("   " + encChoice.a);
//      System.err.println("   " + encChoice.b);
//      System.err.println("   " + factory.hashToBigInt(factory.messageDigest(context)));
        return E;
                                  }

    public boolean verify(ElGamalParameters params, 
            ElGamalCiphertext encCapability, ElGamalCiphertext encChoice, 
            String context) 
    {
        try {
            CryptoFactoryC factory = CryptoFactoryC.singleton();
            ElGamalParametersC paramsC = (ElGamalParametersC)params;
            ElGamalCiphertextC encCapabilityC = (ElGamalCiphertextC)encCapability;
            ElGamalCiphertextC encChoiceC = (ElGamalCiphertextC)encChoice;
            CivitasBigInteger a1 = encCapabilityC.a;
            CivitasBigInteger a2 = encChoiceC.a;
            CivitasBigInteger p = paramsC.p;
            CivitasBigInteger q = paramsC.q;

            List<CivitasBigInteger> E = proofEnv(paramsC, encCapabilityC, encChoiceC, context);
            E.add(paramsC.g.modPow(this.s1, p).modMultiply(a1.modPow(this.c, p), p));
            E.add(paramsC.g.modPow(this.s2, p).modMultiply(a2.modPow(this.c, p), p));
//          System.err.println("Adding more");
//          System.err.println("   " + paramsC.g.modPow(this.s1, p).multiply(a1.modPow(this.c, p)).mod(p));
//          System.err.println("   " + paramsC.g.modPow(this.s2, p).multiply(a2.modPow(this.c, p)).mod(p));

            // c =? hash(E, g^s1 * a1^c, g^s2 * a2^c)
            CivitasBigInteger x = factory.hashToBigInt(factory.hash(E)).mod(q);
            boolean ret = c.equals(x);
//          System.err.println("ret is " + ret);
            return ret;
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }
    public void toXML(Label   PrintWriter s) {
        s.print("<elGamalProofVote>");

        s.print("<c>");
        if (this.c != null) Util.escapeString(CryptoFactoryC.bigIntToString(this.c),   s);
        s.print("</c>");
        s.print("<s1>");
        if (this.s1 != null) Util.escapeString(CryptoFactoryC.bigIntToString(this.s1),   s);
        s.print("</s1>");
        s.print("<s2>");
        if (this.s2 != null) Util.escapeString(CryptoFactoryC.bigIntToString(this.s2),   s);
        s.print("</s2>");

        s.print("</elGamalProofVote>");
    }

    public boolean equals(ProofVote p) {
        if (p instanceof ProofVoteC) {
            ProofVoteC that = (ProofVoteC)p;
            try {
                return this.c.equals(that.c) && this.s1.equals(that.s1) && this.s2.equals(that.s2);
            }
            catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }
    public static ProofVoteC fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        Util.swallowTag(  r, "elGamalProofVote");
        String c = Util.unescapeString(Util.readSimpleTag(  r, "c"));
        String s1 = Util.unescapeString(Util.readSimpleTag(  r, "s1"));
        String s2 = Util.unescapeString(Util.readSimpleTag(  r, "s2"));

        Util.swallowEndTag(  r, "elGamalProofVote");
        return new ProofVoteC(CryptoFactoryC.stringToBigInt(c), 
                              CryptoFactoryC.stringToBigInt(s1),
                              CryptoFactoryC.stringToBigInt(s2));
    }	
}
