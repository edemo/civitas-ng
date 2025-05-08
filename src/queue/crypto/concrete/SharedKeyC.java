/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.*;

import javax.crypto.SecretKey;

import jif.lang.*;
import civitas.common.Util;
import civitas.crypto.SharedKey;
import civitas.crypto.common.Base64;

public class SharedKeyC implements SharedKey {
    final SecretKey k;
    final String name;

    public SharedKeyC(SecretKey k, String name) {
        this.k = k;
        this.name = name;
    }

    public boolean delegatesTo(Principal p) {
        return false;
    }

    public boolean equals(Principal p) {
        if (p instanceof SharedKeyC) {
            SharedKeyC that = (SharedKeyC)p;
            return this.k.equals(that.k);
        }
        return false;
    }

    public String name() {
        return name;
    }

    public ActsForProof findProofDownto(Principal q, Object searchState) {
        return null;
    }

    public ActsForProof findProofUpto(Principal p, Object searchState) {
        return null;
    }

    public boolean isAuthorized(Object authPrf, Closure closure, Label lb,
            boolean executeNow) {
        return false;
    }


    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }

    public void toXML(Label   PrintWriter s) {
        s.print('<'); s.print(OPENING_TAG); s.print('>');
        s.print("<n>");
        s.print(name);
        s.print("</n>");
        s.print("<k>");
        CryptoFactoryC factory = CryptoFactoryC.singleton();
        byte[] bs = factory.sharedKeyToBytes(k);
        Util.escapeString(Base64.encodeBytes(bs),   s);
        s.print("</k>");
        s.print("</"); s.print(OPENING_TAG); s.print('>');
    }

    public void toWire(Label   PrintWriter s) {
        s.print(name);
        s.print('\n');
        CryptoFactoryC factory = CryptoFactoryC.singleton();
        byte[] bs = factory.sharedKeyToBytes(k);
        s.print(Base64.encodeBytes(bs, Base64.DONT_BREAK_LINES | Base64.GZIP));
        s.print('\n');
    }

    public static SharedKey fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        Util.swallowTag(  r, OPENING_TAG);
        String name = Util.readSimpleTag(  r, "n");
        String s = Util.unescapeString(Util.readSimpleTag(  r, "k"));
        Util.swallowEndTag(  r, OPENING_TAG);

        byte[] bs = Base64.decode(s);
        CryptoFactoryC factory = CryptoFactoryC.singleton();
        return new SharedKeyC(factory.sharedKeyFromBytes(bs), name);
    }
    public static SharedKey fromWire(Label   BufferedReader r) throws IllegalArgumentException, IOException {
        String name = r.readLine();
        String s = r.readLine();
        byte[] bs = Base64.decode(s);
        CryptoFactoryC factory = CryptoFactoryC.singleton();
        return new SharedKeyC(factory.sharedKeyFromBytes(bs), name);
    }
    
}
