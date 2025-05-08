/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.io.*;
import civitas.crypto.*;

/**
 * The unique identifier of a host, such as a tab teller, 
 * a registration teller, or a bulletin board. It consists
 * of the address, port, and public key of the host.
 */
public class Host implements XMLSerializable {
    public final String  address;
    public final int  port;
    public final PublicKey  publicKey;
    
    public Host(String  address, int  port, PublicKey  publicKey) {
        this.address = address;
        this.port = port;
        this.publicKey = publicKey;
    }
    
    public String  toString() {
        String s = this.address;
        s += ":";
        s += this.port;
        return s;
    }

    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<host>");

        sb.print("<address>");
        Util.escapeString(this.address,   sb);
        sb.print("</address>");
        sb.print("<port>");
        sb.print(this.port);
        sb.print("</port>");
        if (this.publicKey != null) {
            this.publicKey.toXML(  sb);
        }
        
        sb.print("</host>");
    }
    
    public static Host  fromXML (  Reader   r) throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, "host");
        String address = Util.unescapeString(Util.readSimpleTag(  r, "address"));
        int port = Util.readSimpleIntTag(  r, "port");
        PublicKey publicKey = null;
        if (Util.isNextTag(  r, PublicKey.OPENING_TAG)) {
            try {
                publicKey = CryptoUtil.factory().publicKeyFromXML(  r);
            }
            catch (NullPointerException imposs) { }
        }
        Util.swallowEndTag(  r, "host");
        return new Host(address, port, publicKey);
    }    
}