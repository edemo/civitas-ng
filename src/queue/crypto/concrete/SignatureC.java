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
import civitas.common.Util;
import civitas.crypto.Signature;
import civitas.crypto.common.Base64;

public class SignatureC implements Signature {
    final byte[] signature;
    public SignatureC(byte[] signature) {
        this.signature = signature;       
    }

    public byte[] toBytes() {
        return signature;
    }

    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }
    public void toXML(Label   PrintWriter s) {
        s.print('<'); s.print(Signature.OPENING_TAG); s.print('>');
        Util.escapeString(Base64.encodeBytes(signature),   s);
        s.print("</"); s.print(Signature.OPENING_TAG); s.print('>');
    }

    public static Signature fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        String s = Util.unescapeString(Util.readSimpleTag(  r, OPENING_TAG));
        return new SignatureC(Base64.decode(s));
    }
}
