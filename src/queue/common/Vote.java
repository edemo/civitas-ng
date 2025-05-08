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
 * A <code>Vote</code> is part of the 
 * decomposition of a ballot submitted by a voter. It contains a context
 * for interpreting the choice, an encrypted choice (which is a 
 * one-out-of-L encryption, where
 * L is determined in combination with the context and the ballot design
 * for the election), and an encrypted capability.
 */
public class Vote implements XMLSerializable {

    public static final String  OPENING_TAG = "vote";

    /**
     * Indicates the election and block that this vote belongs to.
     */
    public final String  context;
    
    /**
     * Encrypted choice
     */
    public final ElGamalCiphertext  encChoice;
    
    /**
     * Encrypted choice
     */
    public final ElGamalCiphertext  encCapability;

    public Vote(String  context, 
                          ElGamalCiphertext  encChoice,
                          ElGamalCiphertext  encCapability) {
        this.context = context;
        this.encChoice = encChoice;
        this.encCapability = encCapability;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<context>");
        Util.escapeString(this.context,   sb);
        sb.print("</context>");
        sb.print("<encChoice>");
        if (this.encChoice != null) {
            this.encChoice.toXML(  sb);
        }
        sb.print("</encChoice>");
        sb.print("<encCapability>");
        if (this.encCapability != null) {
            this.encCapability.toUnsignedCiphertextXML(  sb);
        }
        sb.print("</encCapability>");
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static Vote fromXML{}(Reader[{}]{} r) throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, OPENING_TAG);
        String context = Util.unescapeString(Util.readSimpleTag(  r, "context"));
        Util.swallowTag(  r, "encChoice");
        ElGamalCiphertext encChoice = null;
        try { 
            encChoice = CryptoUtil.factory().elGamalCiphertextFromXML(  r);
        }
        catch (NullPointerException imposs) { }

        Util.swallowEndTag(  r, "encChoice");
        Util.swallowTag(  r, "encCapability");
        ElGamalCiphertext encCapability = null;
        try {           
            encCapability = CryptoUtil.factory().elGamalCiphertextFromXML(  r);
        }
        catch (NullPointerException imposs) { }

        Util.swallowEndTag(  r, "encCapability");
        Util.swallowEndTag(  r, OPENING_TAG);
        return new Vote(context, encChoice, encCapability);
    }    
}