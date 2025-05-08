/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.util.*;
import java.io.*;

/**
 * TallyStateFinal for a MajGradingBallotDesign.
 */
public class MajGradingTallyStateFinal extends TallyStateFinal {        
    public final String   []  candidates;
    public final String   []  grades;
    private final int   [][]  candGrades;
    
    MajGradingTallyStateFinal(  
                              String   []  candidates,
                              String   []  grades,
                            int [][]  candGrades) 
     
        int   [][] as = null;
        if (candGrades != null) as = candGrades.clone();
        this.candGrades = as;
        this.candidates = candidates;
        this.grades = grades;
        super();
    }
        
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<kind>");
        sb.print(MajGradingBallotDesign.KIND);
        sb.print("</kind>");
        sb.print("<candCount>");
        sb.print(candidates == null ? 0 : candidates.length);
        sb.print("</candCount>");
        sb.print("<gradeCount>");
        sb.print(grades == null ? 0 : grades.length);
        sb.print("</gradeCount>");

        sb.print("<grades>");
        for (int i = 0; candidates != null && i < candidates.length; i++) {
            sb.print("<candidate>");
            try {
                sb.print("<name>");
                sb.print(candidates[i]);
                sb.print("</name>");
                sb.print("<candidateGrades>");
                for (int j = 0; grades != null && j < grades.length; j++) {
                    sb.print("<grade>");
                    sb.print(grades[j]);
                    sb.print("</grade>");
                    sb.print("<tally>");
                    sb.print(String.valueOf(candGrades[i][j]));
                    sb.print("</tally>");
                }
                sb.print("</candidateGrades>");
            }        
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            sb.print("</candidate>");
        }
        sb.print("</grades>");
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static TallyStateFinal  fromXML (  Reader   r)  throws (IllegalArgumentException , IOException ) {        
        int candCount = Util.readSimpleIntTag(  r, "candCount");
        int gradeCount = Util.readSimpleIntTag(  r, "gradeCount");
        
        String [] candidates = new String[candCount<0?0:candCount];
        String [] grades = new String[gradeCount<0?0:gradeCount];
        int [][] candGrades = new int[candCount<0?0:candCount][];

        Util.swallowTag(  r, "grades");
        for (int i = 0; i < candCount; i++) {
            Util.swallowTag(  r, "candidate");
            try {
                candGrades[i] = new int[gradeCount<0?0:gradeCount];
                candidates[i] = Util.readSimpleTag(  r, "name");
                Util.swallowTag(  r, "candidateGrades");
                for (int j = 0; j < gradeCount; j++) {
                    String gradeName = Util.readSimpleTag(  r, "grade");
                    if (grades[j] == null) grades[j] = gradeName;

                    int tally = Util.readSimpleIntTag(  r, "tally");
                    candGrades[i][j] = tally;
                }
                Util.swallowEndTag(  r, "candidateGrades");
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { }
            Util.swallowEndTag(  r, "candidate");
        }        

        Util.swallowEndTag(  r, "grades");
        MajGradingTallyStateFinal b = new MajGradingTallyStateFinal(  candidates.clone(), grades.clone(), candGrades);

        return b;
     }
}