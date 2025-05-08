/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.PrintWriter;
import java.io.StringWriter;

import jif.lang.Label;
import jif.lang.LabelUtil;
import civitas.common.Util;
import civitas.crypto.common.Base64;

public abstract class KeyCiphertextC  {
    final byte[] encryptedBytes;
    public KeyCiphertextC(byte[] encrypted) {
        this.encryptedBytes = encrypted;       
    }

    public byte[] toBytes() {
        return encryptedBytes;
    }
    abstract String openingTag();

    public String toXML() {
        StringWriter sb = new StringWriter();
        toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
        return sb.toString();
    }
    public void toXML(Label   PrintWriter s) {
        s.print('<'); s.print(openingTag()); s.print('>');
        Util.escapeString(Base64.encodeBytes(encryptedBytes),   s);
        s.print("</"); s.print(openingTag()); s.print('>');
    }

}
