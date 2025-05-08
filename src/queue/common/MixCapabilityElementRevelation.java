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
 * A revelation of a single element of a vote mix. 
 */
public class MixCapabilityElementRevelation extends MixElementRevelation {
    public static final String  OPENING_TAG = "capRev";
    
    public final ElGamalReencryptFactor  reencryptFactor;
    
    public MixCapabilityElementRevelation(int  mapping, byte{} []  nonce, ElGamalReencryptFactor  reencryptFactor) {
        this.reencryptFactor = reencryptFactor;
        super(mapping, nonce);
    }
    
    boolean{} verify{}(ElGamalPublicKey{} key, int{} fromIndex, int{} toIndex, Mix{} fromMix, Mix{} toMix) {
        if (!(fromMix instanceof CapabilityMix && toMix instanceof CapabilityMix)) {
            return false;
        }
        CapabilityMix fromCMix = (CapabilityMix)fromMix;
        CapabilityMix toCMix = (CapabilityMix)toMix;
        
        try {
            ElGamalCiphertext fromCipher = fromCMix.capabilities[fromIndex];
        
            ElGamalCiphertext toCipher = toCMix.capabilities[toIndex];

            // check that toCipher is a reencryption of fromCipher using reencryptFactor
            ElGamalCiphertext recipher = CryptoUtil.factory().elGamalReencrypt(key, fromCipher, reencryptFactor);
            return recipher.equals(toCipher);
        }
        catch (NullPointerException e) {
            return false;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<");
        sb.print(OPENING_TAG);
        sb.print(">");
        sb.print("<mapping>" + mapping + "</mapping>");
        sb.print("<nonce>" + Util.constBytesToString(new label {}, nonce) + "</nonce>");
        sb.print("<r>");
        if (reencryptFactor != null)
            reencryptFactor.toXML(  sb);
        sb.print("</r>");
        sb.print("</");
        sb.print(OPENING_TAG);
        sb.print(">");        
    }
    public static MixCapabilityElementRevelation fromXMLsub{}(Reader[{}]{} r)  throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, OPENING_TAG);
        int mapping = Util.readSimpleIntTag(  r, "mapping");
        byte{}  [] nonce = Util.stringToConstBytes(  Util.readSimpleTag(  r, "nonce"));

        Util.swallowTag(  r, "r");
        ElGamalReencryptFactor reencryptFactor = null;
        try {
            reencryptFactor = CryptoUtil.factory().elGamalReencryptFactorFromXML(  r);
        }
        catch (NullPointerException imposs) { }
        Util.swallowEndTag(  r, "r");

        Util.swallowEndTag(  r, OPENING_TAG);
        
        return new MixCapabilityElementRevelation(mapping, nonce, reencryptFactor);
    }
}