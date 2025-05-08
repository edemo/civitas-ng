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
public class MixVoteElementRevelation extends MixElementRevelation {
    public static final String  OPENING_TAG = "voteRev";
    
    public final ElGamalReencryptFactor  choiceFactor;
    public final ElGamalReencryptFactor  reencryptFactor;
    
    public MixVoteElementRevelation(int  mapping, byte{} []  nonce, ElGamalReencryptFactor  choiceFactor, ElGamalReencryptFactor  reencryptFactor) {
        this.choiceFactor = choiceFactor;
        this.reencryptFactor = reencryptFactor;
        super(mapping, nonce);
    }
    boolean{} verify{}(ElGamalPublicKey{} key, int{} fromIndex, int{} toIndex, Mix{} fromMix, Mix{} toMix) {
        if (!(fromMix instanceof VoteMix && toMix instanceof VoteMix)) {
            return false;
        }
        VoteMix fromVMix = (VoteMix)fromMix;
        VoteMix toVMix = (VoteMix)toMix;
        
        try {
            Vote fromVote = fromVMix.votes[fromIndex];
            Vote toVote = toVMix.votes[toIndex];

            ElGamalCiphertext fromChoice = fromVote.encChoice;
            ElGamalCiphertext fromCapability = fromVote.encCapability;
            ElGamalCiphertext toChoice = toVote.encChoice;
            ElGamalCiphertext toCapability = toVote.encCapability;

            ElGamalCiphertext rechoice = CryptoUtil.factory().elGamalReencrypt(key, fromChoice, choiceFactor);
            ElGamalCiphertext recapability = CryptoUtil.factory().elGamalReencrypt(key, fromCapability, reencryptFactor);
            return rechoice.equals(toChoice) && recapability.equals(toCapability);
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
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<mapping>" + mapping + "</mapping>");
        sb.print("<nonce>" + Util.constBytesToString(new label {}, nonce) + "</nonce>");
        sb.print("<r1>");
        if (choiceFactor != null)
            choiceFactor.toXML(  sb);
        sb.print("</r1>");
        sb.print("<r2>");
        if (reencryptFactor != null)
            reencryptFactor.toXML(  sb);
        sb.print("</r2>");
        sb.print("</" + OPENING_TAG + ">");
    }
    public static MixVoteElementRevelation fromXMLsub{}(Reader[{}]{} r)  throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, OPENING_TAG);
        int mapping = Util.readSimpleIntTag(  r, "mapping");
        byte{}[] nonce = Util.stringToBytes(  Util.readSimpleTag(  r, "nonce"));
        byte{} [] nonceC = nonce==null?null:nonce.clone();
        Util.swallowTag(  r, "r1");
        ElGamalReencryptFactor choiceFactor = null;
        try {
            choiceFactor = CryptoUtil.factory().elGamalReencryptFactorFromXML(  r);
        }
        catch (NullPointerException imposs) { }
        Util.swallowEndTag(  r, "r1");
        
        Util.swallowTag(  r, "r2");
        ElGamalReencryptFactor reencryptFactor = null;
        try {
            reencryptFactor = CryptoUtil.factory().elGamalReencryptFactorFromXML(  r);
        }
        catch (NullPointerException imposs) { }
        Util.swallowEndTag(  r, "r2");

        Util.swallowEndTag(  r, OPENING_TAG);
        
        return new MixVoteElementRevelation(mapping, nonceC, choiceFactor, reencryptFactor);
    }
}