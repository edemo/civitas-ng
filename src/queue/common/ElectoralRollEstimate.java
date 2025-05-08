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
 * An electoral roll estimate: an estimate of the number of
 * voters that will be enrolled. This is used to determine the number of voter
 * blocks requried to give an appropriate anonymity guarantee, and is posted
 * by the registrar near the beginning of the election.
 */
public class ElectoralRollEstimate implements XMLSerializable {
    public final static String  META = "electoralRollEstimate";

    public final int  estimate;

    public ElectoralRollEstimate(int  estimate) {
        this.estimate = estimate;
    }
    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<electoralRollEstimate>");

        sb.print("<estimate>");
        sb.print(this.estimate);
        sb.print("</estimate>");
        sb.print("</electoralRollEstimate>");
    }
    
    public static ElectoralRollEstimate fromXML (  Reader   r) throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, "electoralRollEstimate");
        int estimate = Util.readSimpleIntTag(  r, "estimate");                
        Util.swallowEndTag(  r, "electoralRollEstimate");
        return new ElectoralRollEstimate(estimate);
    }    
}