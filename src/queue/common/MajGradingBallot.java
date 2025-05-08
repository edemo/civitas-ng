/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.io.*;

/**
 * This class is the ballot of a majority grading race. In a majority 
 * grading race, there
 * is a slate of candidates, and the voter assigns each candidate a grade. 
 * MajGradingBallot records the grades the voter assigns each candidate.
 */
public class MajGradingBallot[label L] extends Ballot[L] {
    public String{L}[]{L} candidates;
    public int{L}[]{L} grades;
    public MajGradingBallot{L}() {
        candidates = new String[0];
        grades = new int[0];
        super();
    }
    
    public void addCandidate{L}(String{L} c, int{L} grade) {
        try {
            String{L}[] n = new String[candidates.length + 1];
            int{L}[] b = new int[candidates.length + 1];
            for (int i = 0; i < candidates.length; i++) {
                try {
                    n[i] = candidates[i];
                    b[i] = grades[i];
                }
                catch (NullPointerException ignore) { }                
                catch (ArrayIndexOutOfBoundsException ignore) { }
            }
            n[candidates.length] = c;
            b[candidates.length] = grade;
            this.candidates = n;
            this.grades = b;
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
    }

    public void toXML{L}(PrintWriter[L]{L} sb) {
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<kind>");
        sb.print(MajGradingBallotDesign.KIND);
        sb.print("</kind>");
        sb.print("<candidates>");
        try {
            for (int i = 0; i < candidates.length; i++) {
                sb.print("<candidate>");
                sb.print("<name>");
                Util.escapeString(candidates[i], L, sb);
                sb.print("</name>");
                sb.print("<grade>");
                sb.print(Integer.toString(grades[i]));
                sb.print("</grade>");
                sb.print("</candidate>");
            }        
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        sb.print("</candidates>");
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static Ballot   fromXML (  Reader   r)  throws (IllegalArgumentException , IOException ) {
        MajGradingBallot  b = new MajGradingBallot ();

        Util.swallowTag(  r, "candidates");
        
        while (Util.isNextTag(  r, "candidate")) {
            Util.swallowTag(  r, "candidate");
            b.addCandidate(Util.unescapeString(Util.readSimpleTag(  r, "name")), Util.readSimpleIntTag(  r, "grade"));
            Util.swallowEndTag(  r, "candidate");
        }
        
        Util.swallowEndTag(  r, "candidates");
        return b;
    }
}