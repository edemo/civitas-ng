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
 * A tabulation teller's view of what voter submissions
 * belong in a given voter block. That is, it contains a hash of the voter 
 * submissions that the tabulation teller believes belongs in the voter
 * block. This data structure is produced by each tabulation teller for
 * each voter block, and posted to the bulletin board. Tabulation tellers
 * then check that they all agree on the contents of each voter block. 
 */
public class TabTellerVoterBlockContents implements XMLSerializable {
    public static String meta(int tellerIndex, int voterBlock) {
        return "ttVoterBlockContents:teller"+tellerIndex+":voterBlock"+voterBlock;
    }
    public final int  tellerIndex;
    public final int  voterBlock;
    public final String  hash;

    public TabTellerVoterBlockContents(int  tellerIndex, int  voterBlock, String  hash) {
        this.tellerIndex = tellerIndex;
        this.voterBlock = voterBlock;
        this.hash = hash;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<tabTellerVoterBlockContents>");

        sb.print("<index>");
        sb.print(this.tellerIndex);
        sb.print("</index>");
        sb.print("<voterBlock>");
        sb.print(this.voterBlock);
        sb.print("</voterBlock>");
        sb.print("<hash>");
        Util.escapeString(this.hash,   sb);
        sb.print("</hash>");        
        sb.print("</tabTellerVoterBlockContents>");
    }
    public static TabTellerVoterBlockContents  fromXML (  Reader   r) throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, "tabTellerVoterBlockContents");
        int index = Util.readSimpleIntTag(  r, "index");
        int voterBlock = Util.readSimpleIntTag(  r, "voterBlock");
        String hash = Util.unescapeString(Util.readSimpleTag(  r, "hash"));
        Util.swallowEndTag(  r, "tabTellerVoterBlockContents");
        return new TabTellerVoterBlockContents(index, voterBlock, hash);
    }    
}