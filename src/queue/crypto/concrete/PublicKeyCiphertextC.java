/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import java.io.IOException;
import java.io.Reader;

import jif.lang.Label;
import civitas.common.Util;
import civitas.crypto.PublicKeyCiphertext;
import civitas.crypto.common.Base64;

public class PublicKeyCiphertextC extends KeyCiphertextC implements PublicKeyCiphertext {
    public PublicKeyCiphertextC(byte[] encrypted) {
        super(encrypted);
    }

    public static PublicKeyCiphertext fromXML(Label   Reader r) throws IllegalArgumentException, IOException {
        String s = Util.unescapeString(Util.readSimpleTag(  r, OPENING_TAG));
        return new PublicKeyCiphertextC(Base64.decode(s));
    }

    String openingTag() {
        return OPENING_TAG;
    }

}
