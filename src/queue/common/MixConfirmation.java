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
 * This message is a statement by teller speakerIndex that teller tellerIndex's mix
 * of kind kind was correct.
 */
public class MixConfirmation implements XMLSerializable {
    public final static String  META_PREFIX = "mixConfirm:";
    private static final String  META_VOTE_REVELATION = "vote";
    private static final String  META_ER_REVELATION = "elecRoll";

    public static String meta(boolean isVoteMix, int speakerIndex, int tellerIndex) {
        return META_PREFIX + (isVoteMix?META_VOTE_REVELATION:META_ER_REVELATION) + ":" + speakerIndex + ":" + tellerIndex;
    }

    public final int  speakerIndex;
    public final int  tellerIndex;
    public final boolean  isVoteMix;
    public final ElectionID  electionID;
    

    public MixConfirmation(ElectionID  electionID, int  speakerIndex, int  tellerIndex, boolean  isVoteMix) {
        this.electionID = electionID;
        this.tellerIndex = tellerIndex;
        this.speakerIndex = speakerIndex;
        this.isVoteMix = isVoteMix;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<mixConfirmation>");

        if (electionID != null) {
            this.electionID.toXML(  sb);
        }

        sb.print("<speaker>");
        sb.print(this.speakerIndex);
        sb.print("</speaker>");
        sb.print("<teller>");
        sb.print(this.tellerIndex);
        sb.print("</teller>");
        sb.print("<kind>");
        sb.print((isVoteMix?"vote":"electoralRoll"));
        sb.print("</kind>");
        
        sb.print("</mixConfirmation>");
    }
    
    public static MixConfirmation  fromXML (  Reader   r) throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, "mixConfirmation");
        ElectionID electionID = ElectionID.fromXML(  r);
        int speaker = Util.readSimpleIntTag(  r, "speaker");
        int teller = Util.readSimpleIntTag(  r, "teller");
        String kind = Util.readSimpleTag(  r, "kind");
        Util.swallowEndTag(  r, "mixConfirmation");
        
        return new MixConfirmation(electionID, speaker, teller, "vote".equals(kind));
    }    
}