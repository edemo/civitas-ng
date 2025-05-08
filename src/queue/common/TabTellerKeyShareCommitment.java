/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import civitas.crypto.*;
import java.io.*;

/**
 * A teller's commitment to a share of a public key.
 */
public class TabTellerKeyShareCommitment implements XMLSerializable {
    public final static String  META = "ttKeyShareCommitment";

    public final int  tellerIndex;
    public final String  keyShareHash;

    public TabTellerKeyShareCommitment(int  tellerIndex, String  keyShareHash) {
        this.tellerIndex = tellerIndex;
        this.keyShareHash = keyShareHash;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<tabTellerKeyShareCommitment>");

        sb.print("<index>");
        sb.print(this.tellerIndex);
        sb.print("</index>");
        sb.print("<hash>");
        sb.print(this.keyShareHash);
        sb.print("</hash>");
        sb.print("</tabTellerKeyShareCommitment>");
    }
    
    public static TabTellerKeyShareCommitment fromXML{}(Reader[{}]{} r) throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, "tabTellerKeyShareCommitment");
        int index = Util.readSimpleIntTag(  r, "index");
        String hash = Util.readSimpleTag(  r, "hash");
        Util.swallowEndTag(  r, "tabTellerKeyShareCommitment");
        return new TabTellerKeyShareCommitment(index, hash);
    }    
}