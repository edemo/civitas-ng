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
 * A revelation of a mix hash. A mix hash is a commitment by a tabulation teller
 * to a sequence of random bits; this data structure reveals what those random
 * bits are.
 */
public class MixHashRevelation implements XMLSerializable {
    private final static String  META_PREFIX = "mixHashRevelation:";
    private static final String  META_VOTE_REVELATION = "vote";
    private static final String  META_ER_REVELATION = "elecRoll";

    public static String meta(final ElectionDetails electionDetails, boolean isVoteMix, int block, int tellerIndex) {
        String blockDesc = electionDetails==null?"":electionDetails.blockName(block);
        return META_PREFIX + (isVoteMix?META_VOTE_REVELATION:META_ER_REVELATION) +
                 ":" + blockDesc + ":" + tellerIndex;
    }

    public final int  tellerIndex;
    
    public final byte{} const []  mixNonce;
    
    
    public MixHashRevelation(int  tellerIndex, byte{} const []  mixNonce) {
        this.tellerIndex = tellerIndex;
        this.mixNonce = mixNonce;
    }
    
    
    public void toXML{}(PrintWriter[{}]{{}} sb) {
        if (sb == null) return;
        sb.print("<mixHashRevelation>");

        sb.print("<index>");
        sb.print(this.tellerIndex);
        sb.print("</index>");
        sb.print("<nonce>");
        if (this.mixNonce != null) {
            sb.print(Util.constBytesToString(new label {}, this.mixNonce));
        }
        sb.print("</nonce>");
        sb.print("</mixHashRevelation>");
    }
    
    public static MixHashRevelation{} fromXML{}(Reader[{}]{} r) throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        Util.swallowTag(  r, "mixHashRevelation");
        int index = Util.readSimpleIntTag(  r, "index");
        byte{}[] mixNonce = Util.stringToBytes(  Util.readSimpleTag(  r, "nonce"));
        Util.swallowEndTag(  r, "mixHashRevelation");
        byte{} const [] mn = null;
        if (mixNonce != null) mn = mixNonce.clone();
        return new MixHashRevelation(index, mn);
    }    
}