/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.*;

import jif.lang.Label;
import jif.lang.LabelUtil;
import civitas.common.CiphertextList;
import civitas.common.Util;
import civitas.crypto.*;

public class ElGamal1OfLReencryptionC implements ElGamal1OfLReencryption {
    public final ElGamalCiphertextC m;
    public final ElGamalProof1OfLC proof;
    
    public ElGamal1OfLReencryptionC(ElGamalCiphertextC m, ElGamalProof1OfLC proof) {
        this.m = m;
        this.proof = proof;
    }
    
    public ElGamalCiphertext getCiphertext() {
        return m;
    }
    
    public ElGamalProof1OfL getProof() {
        return proof;
    }

    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }
    public void toXML(Label   PrintWriter s) {
        s.print("<elGamal1OfLReencryption>");
        if (m != null) this.m.toXML(  s);
        if (proof != null) this.proof.toXML(  s);
        s.print("</elGamal1OfLReencryption>");
    }
    
    public static ElGamal1OfLReencryption fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        Util.swallowTag(  r, "elGamal1OfLReencryption");
        ElGamalCiphertextC m = (ElGamalCiphertextC)CryptoFactoryC.singleton().elGamalCiphertextFromXML(  r);
        ElGamalProof1OfLC proof = ElGamalProof1OfLC.fromXML(  r);
        Util.swallowEndTag(  r, "elGamal1OfLReencryption");
        return new ElGamal1OfLReencryptionC(m, proof);
    }

    public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts, int L) {
        if (proof == null) return false;
        // check the proof is consistent
        return proof.verify(pubKey, ciphertexts, L, m);
    }

    public boolean equals(ElGamal1OfLReencryption r) {
        if (r instanceof ElGamal1OfLReencryptionC) {
            ElGamal1OfLReencryptionC that = (ElGamal1OfLReencryptionC)r;
            try {
                return this.m.equals(that.m) && this.proof.equals(that.proof);
            }
            catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }     
}
