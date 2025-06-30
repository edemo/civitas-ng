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
 * TallyState for a MajGradingBallotDesign.
 */
public class MajGradingTallyState[label L] extends TallyState[L] {        
    private final int{L}[][]  candGrades;
    public final String   []  candidates;
    public final String   []  grades;
    
    MajGradingTallyState(String   []  candidates, String   []  grades) {
        this.candidates = candidates;
        this.grades = grades;
        int sizeC = candidates==null?0:candidates.length; 
        int sizeG = grades==null?0:grades.length; 
        this.candGrades = new int[sizeC][sizeG];
        super();
    }
    
    void record{L}(int{L} i, int{L} grade) throws (IndexOutOfBoundsException{L}) {
        if (candGrades != null && candidates != null && i >= 0 && i < candidates.length &&
                grades != null && grade >= 0 && grade < grades.length) {
            try {
                int{L}[] cg = candGrades[i];
                if (cg != null) cg[grade]++;
                return;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        throw new IndexOutOfBoundsException();
    }
    
    public TallyStateFinal{L} finalTally{L}() {
        return new MajGradingTallyStateFinal(L, candidates, grades, candGrades);
    }
}