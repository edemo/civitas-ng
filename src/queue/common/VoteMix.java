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
 * A vote mix is a sequence of <code>Vote</code>s. It is both the input type
 * and the output type for the mixing of votes performed by tabulation tellers.
 */
public class VoteMix extends Mix {
    private final static String  META = "voteMix:";

    public static String meta(final ElectionDetails details, int block, int mixNumber, boolean rightMix) {
        String blockDesc = details==null?"":details.blockName(block);
        return META + blockDesc + ":" + mixNumber + (rightMix?"R":"L");
    }

    public Vote{}[]{} votes;
    
    public VoteMix(int  number) {
        this(number, null);
    }

    public VoteMix(int  number, byte{} []  mixNonceHash) {
        this.votes = new Vote[0];
        super(number, mixNonceHash);
    }

    public Object{} get(int{} i) throws (IndexOutOfBoundsException{}) {
        try {
            return votes[i];
        }
        catch (NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }
    }
    public Vote{} getReencrypted{}(principal{} TT, int{TT->TT} i, ElGamalReencryptFactor{TT->TT} choiceFactor, ElGamalReencryptFactor{TT->TT} capabilityFactor, ElGamalPublicKey{} key) 
    throws (IndexOutOfBoundsException{})  
        Vote{}[] votes = endorse(this.votes, {TT<-TT});
        int ii = declassify(endorse(i, {TT<-TT;TT->TT}), {TT<-TT}); // declassify due to the indexoutofboundsexception that may be throw
        try {
            Vote v = endorse(votes[ii], {TT<-TT});
            return new Vote(v.context,
                            declassify(endorse(CryptoUtil.factory().elGamalReencrypt(key, v.encChoice, choiceFactor), {TT<-TT;TT->TT}), {}),
                            declassify(endorse(CryptoUtil.factory().elGamalReencrypt(key, v.encCapability, capabilityFactor), {TT<-TT;TT->TT}), {}));            
        }
        catch (NullPointerException e) {
            throw new IndexOutOfBoundsException();
        }        
    }

    public void add{}(Object{} v, byte{} []{} commitment) throws (ClassCastException{}) {
        addVote((Vote)v);
        addCommitment(commitment);
    }
    public void addVote{}(Vote{} v) {
        try {
            Vote{}[] n = new Vote[votes.length + 1];
            for (int i = 0; i < votes.length; i++) {
                try {
                    n[i] = votes[i];
                }
                catch (NullPointerException ignore) { }                
                catch (ArrayIndexOutOfBoundsException ignore) { }
            }
            n[votes.length] = v;
            this.votes = n;
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
    }
    
    public int{} size() {
        Vote{}[] vs = votes;
        return vs == null ? 0 : vs.length;
    }

    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<voteMix>");
        sb.print("<number>");
        sb.print(number);
        sb.print("</number>");
        
        if (mixNonceHash != null) {
            sb.print("<nonceHash>");
            sb.print(Util.constBytesToString(new label {}, mixNonceHash));
            sb.print("</nonceHash>");
        }

        sb.print("<votes>");
        try {
            for (int i = 0; i < votes.length; i++) {
                votes[i].toXML(  sb);
            }        
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        sb.print("</votes>");
        
        sb.print("</voteMix>");
    }
    
    public static VoteMix{} fromXML{}(Reader[{}]{} r)  throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};        
        Util.swallowTag(  r, "voteMix");
        int number = Util.readSimpleIntTag(  r, "number");
        byte{} [] nonceHash = null;
        if (Util.isNextTag(  r, "nonceHash")) {
            nonceHash = Util.stringToConstBytes(  Util.readSimpleTag(  r, "nonceHash"));
        }
        VoteMix vm = new VoteMix(number, nonceHash);
        
        Util.swallowTag(  r, "votes");
        while (Util.isNextTag(  r, Vote.OPENING_TAG)) {
            vm.addVote(Vote.fromXML(r));
        }
        
        Util.swallowEndTag(  r, "votes");
        Util.swallowEndTag(  r, "voteMix");
        return vm;
    }
}