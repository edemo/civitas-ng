/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import civitas.crypto.*;
import jif.util.*;
import java.io.*;

/**
 * This class is the ballot design of a majority grading race. In a majority 
 * grading race, there
 * is a slate of candidates, and the voter assigns each candidate a grade. 
 * MajGradingBallotDesign indicates the slate of candidates, and the
 * grades voter's can assign to candidates.
 * 
 */
public class MajGradingBallotDesign extends BallotDesign {
    public static final String  KIND = "majGrad";
    private static final String{*!:} CONTEXT_SUFFIX = "majGrad:";
    
    public final String   []  candidates;
    public final String   []  grades;
    
    public MajGradingBallotDesign (  String []  candidates, String []  grades)  
        String   []  cs;
        String   []  gs;
        if (candidates != null) {
            cs = candidates.clone();
        }
        else {
            cs = new String[0];
        }
        this.candidates = cs;
        if (grades != null) {
            gs = grades.clone();
        }
        else {
            gs = new String[0];
        }
        this.grades = gs;
        super();
    }

    public int  numberOfCandidates() {
        if (candidates == null) return 0;
        return candidates.length;
    }
    public int  numberOfGrades() {
        if (grades == null) return 0;
        return grades.length;
    }
    
    /**
     * Return the index of the candidate cand, -1 if cand is not a candidate.
     */
    public int{cand} indexOfCandidate(String cand) {
        if (candidates == null) return -1;
        for (int i = 0; i < candidates.length; i++) {
            try {
                String cD = candidates[i];
                if (cD == cand || (cD != null && cD.equalsIgnoreCase(cand))) {
                    return i;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }        
        return -1;
    }

    public void checkBallot (  Ballot   b) throws (IllegalArgumentException ) {
        if (!(b instanceof MajGradingBallot )) {
            throw new IllegalArgumentException("Ballot is not a majority grading ballot.");
        }
        
        MajGradingBallot  ab = (MajGradingBallot ) b;
        String [] abCandidates = ab.candidates;
        int [] abGrades = ab.grades;
        
        if (abCandidates == null || abGrades == null || candidates == null) {
            throw new IllegalArgumentException("Missing slate.");
        }
        
        if (abCandidates.length != candidates.length || abGrades.length != candidates.length) {
            throw new IllegalArgumentException("The ballot's candidates do not match the ballot design's candidates.");            
        }
        
        for (int i = 0; i < candidates.length; i++) {
            try {
                String cD = candidates[i];
                String cB = abCandidates[i];
                if (cD != cB && (cD == null || !cD.equalsIgnoreCase(cB))) {
                    throw new IllegalArgumentException("The ballot's candidates do not match the ballot design's candidates.");                            
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
    }
    
    /*
     * There is one vote produced for each candidate. Each choice is the grade
     * given to the candidate
     */
    public int  votesProducedPerBallot() {
        return numberOfCandidates();
    }
    public int  maxPossibleChoices() {       
        return this.grades==null?0:this.grades.length;
    }
    

    public VoterSubmission  decompose (  Ballot   ballot,
            int  voterBlock,
            ElGamalPublicKey  key, 
            ElGamalCiphertext  []  ciphertexts,
            String  context, 
            Map[ lbl]  capabilities) throws (IllegalArgumentException ) 
            {

        if (!(ballot instanceof MajGradingBallot )) {
            throw new IllegalArgumentException("Incorrect kind of ballot.");
        }
        MajGradingBallot  ab = (MajGradingBallot )ballot;
        int [] grades = ab.grades;
        if (candidates == null || grades == null || candidates.length != grades.length) {
            throw new IllegalArgumentException("Invalid ballot.");            
        }
        if (key == null) {
            throw new IllegalArgumentException("No key supplied");            
        }
        
        // add one vote for each candidate.
        VerifiableVote [] votes = new VerifiableVote[candidates.length];
        int L = this.grades==null?0:this.grades.length;
        for (int i = 0; i < candidates.length; i++) {
            ElGamal1OfLReencryption encChoice = null;
            ElGamalReencryptFactor encChoiceFactor = null;
            try {
                int choice = grades[i];
                encChoiceFactor = CryptoUtil.factory().generateElGamalReencryptFactor(key.getParams());
                encChoice = CryptoUtil.factory().elGamal1OfLReencrypt(  key, ciphertexts, L, choice, encChoiceFactor);
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }

            String desiredContext = context+CONTEXT_SUFFIX+i;
            VoteCapability c = null;
            try {
                c = (VoteCapability)(((JifObjectWrapper )capabilities.get(desiredContext)).obj);
            }
            catch (NullPointerException e) {
                throw new IllegalArgumentException("Not enough capabilities supplied");
            }
            catch (ClassCastException e) {
                throw new IllegalArgumentException("Not enough capabilities supplied");
            }
            if (c == null) {
                throw new IllegalArgumentException("No capability supplied for context " + desiredContext);                
            }
        
            ElGamalReencryptFactor encCapFactor = null;
            ElGamalCiphertext encCap = null;
            ProofVote proofVote = null;
            try {
                encCapFactor = CryptoUtil.factory().generateElGamalReencryptFactor(key.getParams());
                encCap = CryptoUtil.factory().elGamalEncrypt(key, c, encCapFactor);
                proofVote = CryptoUtil.factory().constructProofVote(key.getParams(), encCap, encChoice, desiredContext, encCapFactor, encChoiceFactor);
            }
            catch (NullPointerException imposs) { }

            try {
                VerifiableVote v = new VerifiableVote(desiredContext, 
                                                      encChoice, 
                                                      encCap,
                                                      proofVote);

                votes[i] = v; // RIGHT HERE THERE SHOULD BE A DECLASSIFICATION!
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }        
        VoterSubmission vs = new VoterSubmission(  voterBlock, votes);
        return vs;
    }    
    public void checkVoterSubmission(VoterSubmission vs, int startIndex, String context, CiphertextList ciphertexts, ElGamalPublicKey pubKey) 
    throws (IllegalArgumentException{this;vs;startIndex;context;ciphertexts;pubKey}) {
        if (candidates == null) {
            throw new IllegalArgumentException("Invalid ballot.");            
        }
        if (vs == null) {            
            throw new IllegalArgumentException("Invalid voter submission.");            
        }
        if (pubKey == null) {
            throw new IllegalArgumentException("Invalid public Key");                        
        }
        try {
            for (int i = 0; i < votesProducedPerBallot(); i++) {
                VerifiableVote vv = vs.votes[startIndex + i];
                if (vv == null) {            
                    throw new IllegalArgumentException("Invalid verifiable vote.");            
                }
                String vvcontext = vv.context;
                String desiredContext = (context==null?"":context) + CONTEXT_SUFFIX + i;
                if (!desiredContext.equals(vvcontext)) {
                    throw new IllegalArgumentException("Vote did not have correct context");                
                }

                if (!vv.verify(pubKey, ciphertexts, maxPossibleChoices())) {
                    throw new IllegalArgumentException("Vote choice does not pass verification.");                
                }
            }
            return;
        }
        catch (NullPointerException e) { }
        catch (ArrayIndexOutOfBoundsException e) { }
        throw new IllegalArgumentException("Inappropriate vote for approval ballot");
    }
    public void contextsNeeded (  List   l, String  context) throws (IllegalArgumentException ) {
        if (l == null) return;
        if (candidates == null) {
            throw new IllegalArgumentException("Invalid ballot.");            
        }
        for (int i = 0; i < votesProducedPerBallot(); i++) {
            try {
                l.add((context==null?"":context) + CONTEXT_SUFFIX + i);
            }
            catch (ClassCastException unlikely) { }
        }
    }

    public String nthContext(int n) {
        if (n >= 0 && n < votesProducedPerBallot()) return CONTEXT_SUFFIX + n;
        return null;
    }    
    
    public void tally (  String  ctxt, ElGamalMsg  m, String  c, TallyState   s, ElGamalParameters  params) throws (IllegalArgumentException ) {
        if (!(s instanceof MajGradingTallyState )) {
            throw new IllegalArgumentException("Incorrect tally state");
        }
        MajGradingTallyState  ats = (MajGradingTallyState )s;
        
        // interpret the context and the message
        String desiredContext = (ctxt==null?"":ctxt) + CONTEXT_SUFFIX;
        if (c == null || desiredContext == null || !c.startsWith(desiredContext)) {
            throw new IllegalArgumentException("Incorrect context");
        }        
        
        if (m == null) {
            throw new IllegalArgumentException("Null message");
        }
        try {
            String cand = c.substring(desiredContext.length());
            ats.record(Integer.parseInt(cand), CryptoUtil.factory().elGamal1OfLValue(m, maxPossibleChoices(), params));
        }
        catch (CryptoException e) {
            throw new IllegalArgumentException("Invalid vote value");
        }
        catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid index");
        }
        catch (NullPointerException imposs) {
            throw new IllegalArgumentException("impossible!");
        }
        
    }
    
    public TallyState {*lbl2} newTallyState{*lbl2}(label{*lbl2}   label{*lbl2} lbl2)   
        return new MajGradingTallyState (this.candidates, this.grades);
    }

    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        sb.print("<kind>");
        sb.print(MajGradingBallotDesign.KIND);
        sb.print("</kind>");
        if (this.candidates != null) {
            sb.print("<size>");
            sb.print(this.candidates.length);
            sb.print("</size>");
        }
        else {
            sb.print("<size>0</size>");            
        }
        sb.print("<candidates>");
        try {
            for (int i = 0; i < candidates.length; i++) {
                sb.print("<candidate>");
                Util.escapeString(candidates[i],   sb);
                sb.print("</candidate>");
            }        
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        sb.print("</candidates>");
        sb.print("<grades>");
        try {
            for (int i = 0; i < grades.length; i++) {
                sb.print("<grade>");
                Util.escapeString(grades[i],   sb);
                sb.print("</grade>");
            }        
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        sb.print("</grades>");
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static BallotDesign  fromXML (  Reader   r)  throws (IllegalArgumentException , IOException ) {
        int size = Util.readSimpleIntTag(  r, "size");
        Util.swallowTag(  r, "candidates");
        String [] n = new String[size<0?0:size];

        for (int i = 0; i < size; i++) {
            try {
                n[i] = Util.unescapeString(Util.readSimpleTag(  r, "candidate"));
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }        
        Util.swallowEndTag(  r, "candidates");
        
        Util.swallowTag(  r, "grades");
        String [] g = new String[size<0?0:size];

        for (int i = 0; i < size; i++) {
            try {
                g[i] = Util.unescapeString(Util.readSimpleTag(  r, "grade"));
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }        
        Util.swallowEndTag(  r, "grades");
        
        MajGradingBallotDesign b = new MajGradingBallotDesign(  n, g);
        return b;
    }   
}