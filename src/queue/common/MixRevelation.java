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
 * A revelation of a mix. For each element of the "center mix",
 * either the left or the right mapping is revealed.
 */
public class MixRevelation implements XMLSerializable {
    private final static String  META_PREFIX = "mixRevelation:";
    private static final String  META_VOTE_REVELATION = "vote";
    private static final String  META_ER_REVELATION = "elecRoll";

    public static String meta(final ElectionDetails electionDetails, boolean isVoteMix, int block, int tellerIndex) {
        String blockDesc = electionDetails==null?"":electionDetails.blockName(block);
        return META_PREFIX + (isVoteMix?META_VOTE_REVELATION:META_ER_REVELATION) +
                 ":" + blockDesc + ":" + tellerIndex;
    }

    public final int  tellerIndex;
    
    // an array of the indicators. True means the revelation is of the right mix, false means left.
    public final boolean{}[]  indicators;
    
    
    // the revelation of the mapping to either the left or the right.
    public final MixElementRevelation{}[]  revelations;

    public MixRevelation(int  tellerIndex, boolean{}[]  indicators, MixElementRevelation{}[]  revelations) {
        this.tellerIndex = tellerIndex;
        this.indicators = indicators;
        this.revelations = revelations;
    }
    
    public boolean{} verify{}(ElGamalPublicKey{} key, Mix{} initialMix, Mix{} leftMix, Mix{} rightMix, boolean{}[]{} revelationDirections) {
        // go through each revelation, and check that it matches
        if (revelationDirections == null || revelations == null || indicators == null) return false;
        if (revelations.length != revelationDirections.length || revelations.length != indicators.length) {
            return false;
        }

        for (int i = 0; i < revelations.length; i++) {
            try {
                if (revelationDirections[i] != indicators[i]) {
                    return false;
                }
                
                MixElementRevelation mer = revelations[i];
                if (mer == null) return false;
                int fromIndex = -1;
                int toIndex = -1;
                Mix fromMix = null;
                Mix toMix = null;
                
                if (revelationDirections[i]) {
                    // taken from the right                    
                    fromMix = leftMix;
                    toMix = rightMix;
                    fromIndex = i;
                    toIndex = mer.mapping;
                }
                else {
                    // taken from the left
                    fromMix = initialMix;
                    toMix = leftMix;
                    fromIndex = mer.mapping;
                    toIndex = i;
                }
                // check that the commitment to the mapping agrees with the mapping
                if (!Util.equals(new label {}, toMix.commitments[toIndex].clone(), CryptoUtil.factory().messageDigest(new label {}, mer.nonce, mer.mapping, true), true)) {
                    return false;
                }
                
                // check that the posted mix agrees with the revelation
                if (!mer.verify(key, fromIndex, toIndex, fromMix, toMix)) {
                    return false;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException e) { return false; }
        }

        return true;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<mixRevelation>");

        sb.print("<index>");
        sb.print(this.tellerIndex);
        sb.print("</index>");
        if (this.indicators != null && this.revelations != null) {
            sb.print("<size>");
            sb.print(this.indicators.length);
            sb.print("</size>");
            for (int i = 0; i < indicators.length && i < revelations.length; i++) {
                try {
                    sb.print("<rev>");
                    sb.print("<dir>");
                    sb.print(indicators[i]?"R":"L");
                    sb.print("</dir>");
                    MixElementRevelation r = revelations[i];
                    if (r != null) {
                        r.toXML(  sb);
                    }
                    sb.print("</rev>");
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
        else {
            sb.print("<size>0</size>");            
        }
        
        sb.print("</mixRevelation>");
    }
    
    public static MixRevelation{} fromXML{}(Reader[{}]{} r) throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, "mixRevelation");
        int index = Util.readSimpleIntTag(  r, "index");
        int size = Util.readSimpleIntTag(  r, "size");

        boolean{}[] inds = new boolean[size<0?0:size];
        MixElementRevelation{}[] revs = new MixElementRevelation[size<0?0:size];

        
        for (int i = 0; i < size; i++) {
            Util.swallowTag(  r, "rev");
            String ind = Util.readSimpleTag(  r, "dir");
            if (ind != null) ind = ind.toLowerCase();
            MixElementRevelation rev = MixElementRevelation.fromXML(r);
            try {
                inds[i] = (ind != null && ind.startsWith("r"));
                revs[i] = rev;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            Util.swallowEndTag(  r, "rev");
        }
                
        Util.swallowEndTag(  r, "mixRevelation");
        return new MixRevelation(index, inds, revs);
    }    
}