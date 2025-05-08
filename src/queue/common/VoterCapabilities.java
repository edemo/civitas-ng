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
 * A collection of VoteCapability. Includes a voter block. This data structure
 * is produced by a voter by combining all of the vote capability shares it
 * receives from the registration tellers.
 */
public class VoterCapabilities {
    public static final String  OPENING_TAG = "voterCapabilities";
    
    //  the order of these capabilities is important: the ith capability must
    // be used for the ith context, i.e., the ith produced vote.
    public final VoteCapability   []  capabilities;  
    
    public final int  voterBlock;

    public VoterCapabilities(VoteCapability   []  capabilities, int  voterBlock) {
        this.capabilities = capabilities;
        this.voterBlock = voterBlock;
    }
            
    public void toXML (  PrintWriter   sb) {
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<capabilities>");
        if (capabilities != null) {
            sb.print("<size>");
            sb.print(this.capabilities.length);
            sb.print("</size>");
            for (int i = 0; i < capabilities.length; i++) {
                try {
                    VoteCapability vc = capabilities[i];
                    if (vc != null) {
                        vc.toXML(  sb);
                    }
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
        else {
            sb.print("<size>0</size>");
            
        }
        sb.print("</capabilities>");
        sb.print("<voterBlock>" + voterBlock + "</voterBlock>");
        
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static VoterCapabilities  fromXML (  Reader   r)  throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, OPENING_TAG);

        Util.swallowTag(  r, "capabilities");
        int size = Util.readSimpleIntTag(  r, "size");
        VoteCapability []  caps = new VoteCapability[size<0?0:size];
        for (int i = 0; i < size; i++) {
            VoteCapability c = null;
            try {
                c = CryptoUtil.factory().voteCapabilityFromXML(  r);
            }
            catch (NullPointerException imposs) { }
            try {
                caps[i] = c;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }

        Util.swallowEndTag(  r, "capabilities");
        int voterBlock = Util.readSimpleIntTag(  r, "voterBlock");

        
        Util.swallowEndTag(  r, OPENING_TAG);
        VoterCapabilities vc = new VoterCapabilities(caps.clone(), voterBlock);
        return vc;
    }    
}