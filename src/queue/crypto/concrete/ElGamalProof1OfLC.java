/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import jif.lang.Label;
import jif.lang.LabelUtil;
import civitas.common.CiphertextList;
import civitas.common.Util;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalProof1OfL;
import civitas.crypto.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

public class ElGamalProof1OfLC implements ElGamalProof1OfL {
    final int L;
    final CivitasBigInteger[] dvs;
    final CivitasBigInteger[] rvs;

    public ElGamalProof1OfLC(int L, CivitasBigInteger[] dvs, CivitasBigInteger[] rvs) {
        this.L = L;
        this.dvs = dvs;
        this.rvs = rvs;
        if (dvs == null || rvs == null || dvs.length != L || rvs.length != L) {
            throw new IllegalArgumentException("Bad args");
        }
    }

    public static ElGamalProof1OfLC constructProof(ElGamalPublicKeyC key, 
            ElGamalCiphertext[] ciphertexts, 
            int L, 
            int choice, 
            ElGamalCiphertextC m, 
            ElGamalReencryptFactorC factor) {
        CryptoFactoryC factory = CryptoFactoryC.singleton();

        ElGamalParametersC ps = (ElGamalParametersC)key.params;
        CivitasBigInteger u = m.a;
        CivitasBigInteger v = m.b;
        CivitasBigInteger r = factor.r;
        
        ElGamalCiphertextC[] ms = new ElGamalCiphertextC[L];
        for (int i = 0; i < L; i++) {
            ms[i] = (ElGamalCiphertextC)ciphertexts[i];
        }

        // choose d1 .. dL, and r1 ... rL at random.
        CivitasBigInteger[] ds = new CivitasBigInteger[L];
        CivitasBigInteger[] rs = new CivitasBigInteger[L];
        for (int i = 0; i < L; i++) {
            ds[i] = CryptoAlgs.randomElement(ps.q);
            rs[i] = CryptoAlgs.randomElement(ps.q);
        }
        
        // compute a_i's and b_i's
        CivitasBigInteger[] as = new CivitasBigInteger[L];
        CivitasBigInteger[] bs = new CivitasBigInteger[L];
        for (int i = 0; i < L; i++) {
            as[i] = ms[i].a.modDivide(u, ps.p).modPow(ds[i], ps.p).modMultiply(ps.g.modPow(rs[i], ps.p), ps.p).mod(ps.p);
            bs[i] = ms[i].b.modDivide(v, ps.p).modPow(ds[i], ps.p).modMultiply(key.y.modPow(rs[i], ps.p), ps.p).mod(ps.p);
        }
        
        List<CivitasBigInteger> env = new ArrayList<CivitasBigInteger>(2 + 4*L);
        env.add(u);
        env.add(v);
        for (int i = 0; i < L; i++) {
            env.add(ms[i].a);
            env.add(ms[i].b);            
            env.add(as[i]);            
            env.add(bs[i]);            
        }
        CivitasBigInteger c = factory.hashToBigInt(factory.hash(env)).mod(ps.q); 
        CivitasBigInteger w = (r.modNegate(ps.q).modMultiply(ds[choice], ps.q)).modAdd(rs[choice], ps.q);
        CivitasBigInteger sum = CivitasBigInteger.ZERO;
        for (int i = 0; i < L; i++) {
            if (i != choice) {
                sum = sum.modAdd(ds[i], ps.q);
            }
        }
        CivitasBigInteger dprimet = c.modSubtract(sum, ps.q);
        CivitasBigInteger rprimet = w.modAdd(r.modMultiply(dprimet, ps.q), ps.q);
        
        CivitasBigInteger[] dvs = new CivitasBigInteger[L];
        CivitasBigInteger[] rvs = new CivitasBigInteger[L];
        for (int i = 0; i < L; i++) {
            if (i != choice) {
                dvs[i] = ds[i];
                rvs[i] = rs[i];
            }
            else {
                dvs[i] = dprimet;                
                rvs[i] = rprimet;                
            }
        }

        return new ElGamalProof1OfLC(L, dvs, rvs);        
    }
    public boolean verify(ElGamalPublicKey pubKey, CiphertextList ciphertexts, int L, ElGamalCiphertext msg) {
        if (this.L != L) return false;
        ElGamalCiphertextC m = (ElGamalCiphertextC)msg;
        CivitasBigInteger u = m.a;
        CivitasBigInteger v = m.b;
        ElGamalPublicKeyC key = (ElGamalPublicKeyC)pubKey;
        ElGamalParametersC ps = (ElGamalParametersC)key.params;
        ElGamalCiphertextC[] ms = new ElGamalCiphertextC[L];

        for (int i = 0; i < L; i++) {
            ms[i] = (ElGamalCiphertextC)ciphertexts.get(i);
        }

        CryptoFactoryC factory = CryptoFactoryC.singleton();
        CivitasBigInteger[] as = new CivitasBigInteger[L];
        CivitasBigInteger[] bs = new CivitasBigInteger[L];
        CivitasBigInteger sum = CivitasBigInteger.ZERO;
        for (int i = 0; i < L; i++) {
            as[i] = (ms[i].a.modDivide(u, ps.p)).modPow(dvs[i], ps.p).modMultiply(ps.g.modPow(rvs[i], ps.p), ps.p);
            bs[i] = (ms[i].b.modDivide(v, ps.p)).modPow(dvs[i], ps.p).modMultiply(key.y.modPow(rvs[i], ps.p), ps.p);
            sum = sum.modAdd(dvs[i], ps.q);
        }
        
        // construct the hash of the environment
        List<CivitasBigInteger> env = new ArrayList<CivitasBigInteger>(2 + 4*L);
        env.add(u);
        env.add(v);
        for (int i = 0; i < L; i++) {
            env.add(ms[i].a);
            env.add(ms[i].b);            
            env.add(as[i]);            
            env.add(bs[i]);            
        }
        CivitasBigInteger c = factory.hashToBigInt(factory.hash(env)).mod(ps.q);         
        return sum.equals(c);
    }
    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }
    public void toXML(Label   PrintWriter s) {
        s.print("<elGamalProof1OfL>");
        s.print("<size>");
        s.print(L);
        s.print("</size>");
        for (int i = 0; i < L; i++) {
            s.print("<dv>");
            if (dvs[i] != null) Util.escapeString(CryptoFactoryC.bigIntToString(this.dvs[i]),   s);
            s.print("</dv>");
        }
        for (int i = 0; i < L; i++) {
            s.print("<rv>");
            if (rvs[i] != null) Util.escapeString(CryptoFactoryC.bigIntToString(this.rvs[i]),   s);
            s.print("</rv>");            
        }
        s.print("</elGamalProof1OfL>");
    }
    public boolean equals(ElGamalProof1OfL p) {
        if (p instanceof ElGamalProof1OfLC) {
            ElGamalProof1OfLC that = (ElGamalProof1OfLC)p;
            if (this.L != that.L) return false;

            for (int i = 0; i < L; i++) {
                try {
                    if (!dvs[i].equals(that.dvs[i])) return false;
                    if (!rvs[i].equals(that.rvs[i])) return false;
                }
                catch (NullPointerException e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static ElGamalProof1OfLC fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        Util.swallowTag(  r, "elGamalProof1OfL");
        int L = Util.readSimpleIntTag(  r, "size");
        CivitasBigInteger[] dvs = new CivitasBigInteger[L];
        CivitasBigInteger[] rvs = new CivitasBigInteger[L];
        for (int i = 0; i < L; i++) {
            dvs[i] = CryptoFactoryC.stringToBigInt(Util.unescapeString(Util.readSimpleTag(  r, "dv")));
        }
        for (int i = 0; i < L; i++) {
            rvs[i] = CryptoFactoryC.stringToBigInt(Util.unescapeString(Util.readSimpleTag(  r, "rv")));
        }

        Util.swallowEndTag(  r, "elGamalProof1OfL");
        return new ElGamalProof1OfLC(L, dvs, rvs);
    }

}
