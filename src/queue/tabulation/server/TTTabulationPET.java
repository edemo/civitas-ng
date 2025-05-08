/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.io.*;
import jif.util.*;
import until.util.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.tabulation.client.*;
import civitas.common.*;
import civitas.crypto.*;

/**
 * Utility class to help with the tabulation protocol. 
 * The principal TT should be the tab teller's public key.
 * The principal SUP is the election supervisor.
 * The principal TELLS represents all of the tabulation tellers, i.e., it is a principal that
 * every tabulation teller can act for.
 */
public class TTTabulationPET[principal TT, principal SUP, principal TELLS] {
    private final PrivateKey{TT<-TT} tellerPrivKey;
    private final TTTabulation[TT,SUP,TELLS]  ttt;
    private final TTStore[TT]  ttstore;
    private final BBClientUtil  bb;
    final TTTabulationUtil[TT,SUP,TELLS]  tttUtil;
    private final PrintStream[{*->*}]  debugLog;
    
    
    TTTabulationPET(PrivateKey{TT<-TT} tellerPrivKey,
                    TTTabulation[TT,SUP,TELLS]  ttt,
                    TTStore[TT]  ttstore,
               BBClientUtil  bb,
               PrintStream[{*->*}]  debugLog) {
        this.tellerPrivKey = tellerPrivKey;
        this.ttt = ttt;
        this.ttstore = ttstore;
        this.bb = bb;
        this.tttUtil = new TTTabulationUtil[TT,SUP,TELLS](tellerPrivKey, ttstore, bb);
        this.debugLog = debugLog;
    }
    
    boolean postVotePETCommitment{TT<-SUP}(PETCache{TT<-SUP} cache, final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, 
            int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
    throws (IOException{}) 
     
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;        
        
        PETShare{TT->TT;TT<-TT}[] shares = ttstore.retrieveVoteVotePETShares(SUP, electionID, block, ballotIndex);
        
        if (endorse(shares == null, {TT<-SUP})) {
            // compute the shares
            if (debugLog != null) debugLog.println("  Computing vote pet shares for block " + block + " ballot index " + ballotIndex);
            shares = computeVotePETShares(electionDetails, electionCache, tabTellerSharedKey, block, ballotIndex);
        }
        if (shares == null) return false;

        // compute the commitments
        PETCommitment{}[] commitments = new PETCommitment[shares.length];
        if (debugLog != null) debugLog.println("  Computing vote pet commitments for block " + block + " (" + shares.length + " PETs)");
        computeCommitments(electionDetails.elGamalParameters, commitments, shares);

        // post the commitments
        TabTellerPETShareCommitments ttpsc = new TabTellerPETShareCommitments(tellerIndex, commitments);
        try {
            bb.post(TabTellerPETShareCommitments.META_PREFIX+TTTabulation[TT, SUP, TELLS].blockMeta(TTTabulation[TT, SUP, TELLS].META_VOTES_TO_VOTES, electionDetails, block, ballotIndex, tellerIndex), ttpsc, tellerPrivKey); 
            if (cache != null) {
                cache.setCommitment(TTTabulation[TT, SUP, TELLS].META_VOTES_TO_VOTES, block, ballotIndex, tellerIndex, ttpsc);
            }
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    PETShare{TT->TT;TT<-TT}[]{} computeVotePETShares{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails,
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
    throws (IOException{})  
        if (electionDetails == null || ttt == null || ttstore == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;

        // retrieve the submitted votes
        VerifiableVote{}[] vvs = ttt.retrieveValidVerifiableVotes(electionDetails, electionCache, tabTellerSharedKey, block);
        if (vvs == null) {
            throw new IOException("Cannot retrieve votes. Is the election stopped?");
        }
        
        if (ballotIndex < 0 || ballotIndex >= vvs.length) {
            if (debugLog != null) debugLog.println("ERROR! ballotIndex is " + ballotIndex + " and vvs.length is " + vvs.length);
            return null;
        }
        
        int commitmentCount = 0;
        int numberOfPETs = vvs.length - 1 - ballotIndex;
        
        PETShare{TT->TT}[] shares = new PETShare[numberOfPETs<0 ? 0 : numberOfPETs];
        VerifiableVote vi = null;
        try {
            vi = vvs[ballotIndex];
        }
        catch (ArrayIndexOutOfBoundsException imposs) { }

        for (int j = ballotIndex+1; j < vvs.length; j++) {
            VerifiableVote vj = null;
            try {
                vj = vvs[j];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
            if (endorse(vj == null || vi == null, {TT<-SUP})) continue;
            PETShare share = null;
            try {
                share = CryptoUtil.factory().constructPETShare(electionDetails.elGamalParameters, vi.encCapability, vj.encCapability);
            }
            catch (NullPointerException imposs) { }
            
            if (share != null) {
                int c = commitmentCount++;
                try {
                    shares[c] = share;
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
        return storeVoteVotePETShares(electionID, shares, block, ballotIndex);
    }

    PETShare{TT->TT;TT<-TT}[]{} storeVoteVotePETShares{TT<-SUP}(ElectionID{TT<-SUP} election_, PETShare{TT->TT}[]{TT<-SUP} shares_, int{TT<-SUP} block_, int{TT<-SUP} ballotIndex_) 
     
        ElectionID election = endorse(election_, {TT<-TT});
        PETShare{TT->TT}[] shares = endorse(shares_, {TT<-TT});
        int block = endorse(block_, {TT<-TT});
        int ballotIndex = endorse(ballotIndex_, {TT<-TT});
        if (shares == null || ttstore == null) return null;
        
        PETShare{TT->TT;TT<-TT}[] eshares = new PETShare[shares.length];
        for (int i = 0; i < eshares.length; i++) {
            PETShare s = null;
            try {
                s = shares[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            try {
                eshares[i] = endorse(s, {TT->TT;TT<-TT});
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        ttstore.storeVoteVotePETShares(election, block, ballotIndex, eshares);                
        return eshares;        
    }
    
    
    /**
     * Post the commitment for the PETs between the electoral roll and the capabilities used in the votes 
     */
    boolean{} postRollVotePETCommitment{TT<-SUP}(PETCache{} cache, ElectionCache{TT<-SUP} electionCache, ElectionDetails{TT<-SUP} electionDetails, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
    throws (IOException{})  
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        
        PETShare{TT->TT;TT<-TT}[] shares = ttstore.retrieveRollVotePETShares(SUP, electionID, block, ballotIndex);
        
        if (endorse(shares == null, {TT<-SUP})) {
            // compute the shares
            if (debugLog != null) debugLog.println("  Computing roll-vote pet shares for block " + block + " ballotIndex " + ballotIndex);
            shares = computeRollVotePETShares(electionCache, electionDetails, tellerDetails, block, ballotIndex);
        }
        if (shares == null) return false;
        
        // compute the commitments
        PETCommitment{}[] commitments = new PETCommitment[shares.length];
        if (debugLog != null) debugLog.println("  Computing roll-vote pet commitment for block " + block+ " ballotIndex " + ballotIndex);
        computeCommitments(electionDetails.elGamalParameters, commitments, shares);

        // post the commitments
        TabTellerPETShareCommitments ttpsc = new TabTellerPETShareCommitments(tellerIndex, commitments);
        try {
            bb.post(TabTellerPETShareCommitments.META_PREFIX+TTTabulation[TT, SUP, TELLS].blockMeta(TTTabulation[TT, SUP, TELLS].META_VOTES_TO_ROLL, electionDetails, block, ballotIndex, tellerIndex), ttpsc, tellerPrivKey);
            if (cache != null) {
                cache.setCommitment(TTTabulation[TT, SUP, TELLS].META_VOTES_TO_ROLL, block, ballotIndex, tellerIndex, ttpsc);
            }
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    /**
     * Compute (and store) the roll-vote PET shares.
     */
    PETShare{TT->TT;TT<-TT}[]{} computeRollVotePETShares{TT<-SUP}(ElectionCache{TT<-SUP} electionCache,
                     ElectionDetails{TT<-SUP} electionDetails, 
                     TellerDetails{TT<-SUP} tellerDetails, 
                     int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
    throws (IOException{})  
        if (electionDetails == null || ttstore == null || tttUtil == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        // retrieve the final mixes for the electoral roll and the votes
        CapabilityMix electoralRoll = null;
        VoteMix votes = null;

        int retriesLeft = tttUtil.maxRetries(numTabTellers);
        while ((electoralRoll == null || votes == null) && retriesLeft-- > 0) {
            try {
                if (electoralRoll == null) {
                    electoralRoll = (CapabilityMix)ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, false, block, numTabTellers, true);
                }
                if (votes == null) {
                    votes = (VoteMix)ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, true, block, numTabTellers, true);
                }
            }
            catch (ClassCastException unlikely) { }
            if (retriesLeft > 0 && (electoralRoll == null || votes == null)) {
                // sleep for a bit
                if (debugLog != null) debugLog.println("Sleeping for both mixes for the roll vote PET shares for block " + block + ". Retries left = " + retriesLeft);
                jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
            }
        }
        if (retriesLeft < 0 || electoralRoll == null || votes == null) return null;
        
        if (ballotIndex < 0 || ballotIndex >= votes.size()) return null;
        
        int commitmentCount = 0;
        int numberOfPETs = electoralRoll.size();
        
        PETShare{TT->TT}[] shares = new PETShare[numberOfPETs<0 ? 0 : numberOfPETs];
        Vote vi = null;
        try {
            vi = (Vote)votes.get(ballotIndex);
        }
        catch (IndexOutOfBoundsException imposs) { }
        catch (ClassCastException imposs) { }

        for (int j = 0; j < electoralRoll.size(); j++) {
            ElGamalCiphertext cj = null;
            try {
                cj = (ElGamalCiphertext)electoralRoll.get(j);    
            }
            catch (IndexOutOfBoundsException imposs) { }
            catch (ClassCastException imposs) { }

            if (cj == null || vi == null) continue;
            PETShare share = null;
            try {
                share = CryptoUtil.factory().constructPETShare(electionDetails.elGamalParameters, vi.encCapability, cj);
            }
            catch (NullPointerException imposs) { }

            if (share != null) {
                int c = commitmentCount++;
                try {
                    shares[c] = share;
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }                            
            }
        }            
        return storeRollVotePETShares(electionID, block, ballotIndex, shares);
    }

    PETShare{TT->TT;TT<-TT}[]{} storeRollVotePETShares{}(ElectionID{} election_, int{} block_, int{} ballotIndex_, PETShare{TT->TT}[]{} shares_) 
     
        ElectionID election = endorse(election_, {TT<-TT});
        PETShare{TT->TT}[] shares = endorse(shares_, {TT<-TT});
        int block = endorse(block_, {TT<-TT});
        int ballotIndex = endorse(ballotIndex_, {TT<-TT});
        if (shares == null || ttstore == null) return null;
        
        PETShare{TT->TT;TT<-TT}[] eshares = new PETShare[shares.length];
        for (int i = 0; i < eshares.length; i++) {
            PETShare s = null;
            try {
                s = shares[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            try {
                eshares[i] = endorse(s, {TT->TT;TT<-TT});
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        ttstore.storeRollVotePETShares(election, block, ballotIndex, eshares);                
        return eshares;        
    }

    void computeCommitments{}(ElGamalParameters{} params_,
            PETCommitment{}[]{} commitments_, PETShare{TT->TT;TT<-TT}[]{} shares_) 
             
        PETCommitment{}[] commitments = endorse(commitments_, {TT<-TT});
        ElGamalParameters params = endorse(params_, {TT<-TT});
        PETShare{TT->TT;TT<-TT}[] shares = endorse(shares_, {TT<-TT});

        if (shares == null || commitments == null) return;
        for (int i = 0; i < shares.length; i++) {
            try {
                PETShare share = shares[i];
                PETCommitment commitment = null;
                if (share != null) { 
                    commitment = share.commitment(params);
                }
                commitments[i] = declassify(commitment, {TT->TT;TT<-TT} to  {}); // declassification is because we are computing a commitment, i.e., WHAT information
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
    }

    
    boolean postPETDecommitment{TT<-SUP}(PETCache{TT<-SUP} cache, 
            ElectionDetails{TT<-SUP} electionDetails, 
            TellerDetails{TT<-SUP} tellerDetails, 
            String{TT<-SUP} meta, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex,
            int{TT<-SUP} tellerIndex,
            PETShare{TT->TT;TT<-TT}[]{TT<-SUP} shares)
    throws (IOException{})  
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;

        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        String metaSuffix = TTTabulation[TT, SUP, TELLS].blockMeta(meta, electionDetails, block, ballotIndex, tellerIndex);

        // wait for all the PET commits from the other servers
        boolean allCommitments = false;
        try {            
            int retriesLeft = tttUtil.maxRetries(numTabTellers);
            while (!allCommitments && retriesLeft-- > 0) {
                allCommitments = hasAllTTPETShareCommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails);
                if (retriesLeft > 0 && !allCommitments) {
                    // sleep for a bit
                    if (debugLog != null) debugLog.println("Sleeping for " + meta + " PET commitment for block " + block + " ballot " + ballotIndex +". Retries left = " + retriesLeft);
                    jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                }
            }
            if (retriesLeft < 0 || !allCommitments) return false;

            // post the decommits
            if (shares == null) {
                throw new IOException("Do not have the PET shares");
            }
            PETDecommitment{}[] decommitments = new PETDecommitment[shares.length];
            ElGamalProofDiscLogEquality{}[] proofs = new ElGamalProofDiscLogEquality[shares.length];
            if (debugLog != null) debugLog.println("  Declassifying PET shares for block " + block + " (" + shares.length+ " shares)");
            for (int i = 0; i < shares.length; i++) {
                declassifyPETShare(electionDetails.elGamalParameters, shares, i, decommitments, proofs);
            }
            TabTellerPETShareDecommitments ttpsd = new TabTellerPETShareDecommitments(tellerIndex, decommitments, proofs);
            if (debugLog != null) debugLog.println("  posting PET decommitments for " + meta + " for block " + block);
            bb.post(TabTellerPETShareDecommitments.META_PREFIX+metaSuffix, ttpsd, tellerPrivKey);
            if (cache != null) {
                cache.setDecommitment(meta, block, ballotIndex, tellerIndex, ttpsd);
            }
            if (debugLog != null) debugLog.println("  done posting PET decommitments for " + meta + " for block " + block + " ballot " + ballotIndex);
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }
    }
    void declassifyPETShare{}(ElGamalParameters{} params_, 
            PETShare{TT->TT;TT<-TT}[]{} shares_, int{} i_, 
            PETDecommitment{}[]{} decommitments_, ElGamalProofDiscLogEquality{}[]{} proofs_) 
             
        PETShare{TT->TT;TT<-TT}[] shares = endorse(shares_, {TT<-TT});
        int i = endorse(i_, {TT<-TT});
        PETDecommitment{}[] decommitments = endorse(decommitments_, {TT<-TT});
        ElGamalParameters params = endorse(params_, {TT<-TT});
        ElGamalProofDiscLogEquality{}[] proofs = endorse(proofs_, {TT<-TT});
        if (shares != null && decommitments != null && proofs != null) {
            PETShare share = null;
            try {
                share = shares[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
            PETDecommitment decom_ = null;
            if (share != null) decom_ = share.decommitment(params);
            PETDecommitment decom = declassify(endorse(decom_, {TT<-TT;TT->TT}), {TT<-TT}); // declassification is ok because we are releasing the decommitment. WHEN and WHAT

            try {
                decommitments[i] = decom;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }            
            
            try {
                if (decom != null) proofs[i] = decom.proof();
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
        }
    }

    boolean postPETDecryptShare{TT<-SUP}(PETCache{TT<-SUP} cache, 
            ElectionDetails{TT<-SUP} electionDetails, 
            TellerDetails{TT<-SUP} tellerDetails, 
            String{TT<-SUP} meta, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex,
            int{TT<-SUP} tellerIndex,
            PETShare{TT->TT;TT<-TT}[]{TT<-SUP} shares)
    throws (IOException{})  
        if (electionDetails == null || bb == null || ttstore == null || ttt == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;
        
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] cprime = cache==null?null:cache.getCiphertexts(meta, block, ballotIndex, TT, SUP, TELLS);
        if (cprime == null) {
            // wait for all the PET decommits from the other servers
            boolean{TT<-SUP} allDecommitments = false;
            int{TT<-SUP} retriesLeft = tttUtil.maxRetries(numTabTellers);
            while (!allDecommitments && retriesLeft-- > 0) {
                allDecommitments = hasAllTTPETShareDecommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, shares); 
                if (retriesLeft > 0 && !allDecommitments) {
                    // sleep for a bit
                    if (debugLog != null) debugLog.println("Sleeping for " + meta + " PET decommitment for block " + block + " ballot " + ballotIndex + ". Retries left = " + retriesLeft);
                    jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                }
            }
            if (retriesLeft < 0 || !allDecommitments) return false;

            try {
                if (debugLog != null) debugLog.println("Decommitments to ciphertext for " + meta + " for block " + block + " ballot " + ballotIndex);
                cprime = decommitmentsToCiphertexts(cache, 
                                                    electionDetails, 
                                                    tellerDetails, 
                                                    meta, 
                                                    block,
                                                    ballotIndex,
                                                    shares,
                                                    electionDetails.elGamalParameters);
            }
            catch (CryptoException e) {
                // a crypto operation went awry, i.e., a decommit disagreed 
                tttUtil.abandonElection(electionID,
                                "Unable to produce decryption shares: " + meta +": " + e.getMessage(), 
                                tellerIndex);
                return false;
            }
        }


        // compute the encryption of each PET by combining the relevant parts of the decommitment
        ElGamalKeyPairShare keyShare = ttstore.retrieveKeyShare(SUP, electionID);
        // perform distributed decryption
        if (debugLog != null) debugLog.println("Shares for msg for " + meta + " for block " + block + " ballot " + ballotIndex);
        TabTellerDistributedDecryptions decryptShares = ttt.sharesForMsg(new label {TT<-SUP;TT<-TELLS}, cprime, keyShare, tellerIndex);
        try {
            if (debugLog != null) debugLog.println("  posting dist decryptions for " + meta + " for block " + block + " ballot " + ballotIndex);
            bb.post(TabTellerDistributedDecryptions.META_PREFIX+TTTabulation[TT, SUP, TELLS].blockMeta(meta, electionDetails, block, ballotIndex, tellerIndex), decryptShares, tellerPrivKey);
            if (cache != null) {
                cache.setDecryptShare(meta, block, ballotIndex, tellerIndex, decryptShares);
            }
            if (debugLog != null) debugLog.println("  done posting dist decryptions for " + meta + " for block " + block + " ballot " + ballotIndex);
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
        catch (NullPointerException e) {
            throw new IOException("Unable to produce decryption shares: " + meta);
        }
    }
        
    private void verifyPETDecommitments{}(PETCache{} cache, 
            ElectionDetails{} electionDetails_, 
            TellerDetails{} tellerDetails_, 
            String{} meta_, 
            int{} block_, 
            int{} ballotIndex_,
            TabTellerPETShareDecommitments{} decs_, 
            PETShare{TT->TT;TT<-TT}[]{} shares_,
            int{} tellerIndex_) 
    throws (IOException{})  
        ElectionDetails electionDetails = endorse(electionDetails_, {TT<-TT}); 
        TellerDetails tellerDetails = endorse(tellerDetails_, {TT<-TT});
        String meta = endorse(meta_, {TT<-TT});
        int block = endorse(block_, {TT<-TT});
        int ballotIndex = endorse(ballotIndex_, {TT<-TT});
        TabTellerPETShareDecommitments decs = endorse(decs_, {TT<-TT}); 
        PETShare{TT->TT;TT<-TT}[] shares = endorse(shares_, {TT<-TT});
        int tellerIndex = endorse(tellerIndex_, {TT<-TT});
        
        if (decs == null || electionDetails == null || tttUtil == null) return;
        String abandonElectionReason = null;
        ElectionID electionID = electionDetails.electionID;
        
        TabTellerPETShareCommitments coms_ = null;
        IOException ioex = null;
        try {
            coms_ = retrieveTTPETShareCommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, tellerIndex);
        }
        catch (IOException e) {
            ioex = e;
        }
        TabTellerPETShareCommitments coms = endorse(coms_, {TT<-TT});
               
        if (coms == null) {
           throw new IOException(meta + " PET commitments not retrievable");
        }

        int numberOfPETs = (shares == null ? 0 : shares.length);
        
        try {
            if (decs.decommitments != null && decs.proofs != null && coms.commitments != null) {                    
                if (decs.decommitments.length != numberOfPETs || decs.proofs.length != numberOfPETs) {
                    // bad lengths
                    abandonElectionReason = "The number of decommitments and/or proofs of teller " + tellerIndex + " are incorrect.";
                }
                else if (coms.commitments.length != numberOfPETs) {
                    // bad lengths
                    abandonElectionReason = "The number of " + meta + " commitments of teller " + tellerIndex + " are incorrect.";
                }
                else {
                    for (int j = 0; abandonElectionReason == null && j < decs.decommitments.length; j++) {
                        PETShare share = shares==null?null:shares[j];
                        PETDecommitment decom = decs.decommitments[j];
                        ElGamalProofDiscLogEquality prf = decs.proofs[j];
                        PETCommitment com = coms.commitments[j];
                        
                        if (decom == null || share == null || !decom.verify(com, electionDetails.elGamalParameters, share.ciphertext1(), share.ciphertext2())) { 
                            // Decommitment doesn't match commitment
                            abandonElectionReason = meta + " decommitment " + j + " of teller " + tellerIndex + " does not agree with the commitment.";
                            break;
                        }                            
                    }
                }
            }
            else {
                abandonElectionReason = "Null commitments or decommitments from teller " + tellerIndex;                
            }
        }
        catch (ArrayIndexOutOfBoundsException imposs) {
            abandonElectionReason = "An impossible array index out of bounds...";                
        }

        String reason = null;
        reason = declassify(endorse(abandonElectionReason, {TT<-TT;TT->TT}), {TT<-TT});
        if (reason != null) {            
            // Record that the election has been abandoned
            try {
                tttUtil.abandonElection(electionID, reason, tellerIndex);
            }
            catch (IOException e) {
                ioex = e;
            }
        }        
        IOException eioex = endorse(ioex, {TT<-TT});
        if (eioex != null) throw eioex;
    }
    
    /**
     * Convert an array of decommitments into an array of ciphertexts.
     */
    ElGamalCiphertext{TT<-SUP;TT<-TELLS}[]{TT<-SUP} 
    decommitmentsToCiphertexts{TT<-SUP}(PETCache{TT<-SUP} cache,
            ElectionDetails{TT<-SUP} electionDetails,
            TellerDetails{TT<-SUP} tellerDetails,
            String{TT<-SUP} meta, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex,
            PETShare{TT<-TT;TT->TT}[]{TT<-SUP} shares,
            ElGamalParameters{TT<-SUP} elGamalParameters):{TT<-SUP}
    throws CryptoException, IOException
     
        if (cache != null) {
            ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] c = cache.getCiphertexts(meta, block, ballotIndex, TT, SUP, TELLS);
            if (c != null) return c;
        }
        if (debugLog != null) debugLog.println("  retrieve all tt pet share decommitments for block " + block + " ballot " + ballotIndex);
        TabTellerPETShareDecommitments{TT<-SUP;TT<-TELLS}[] allDecs = 
            retrieveAllTTPETShareDecommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, shares);

        if (allDecs == null) return null;
        // We need to have at least one element in the array, 
        // i.e., there must be at least one tabulation teller.
        if (allDecs.length < 1) return null;

        int length_ = 0;
        try {
            TabTellerPETShareDecommitments ttdecs = allDecs[0];
            if (ttdecs != null && ttdecs.decommitments != null) {
                length_ = ttdecs.decommitments.length;
            }
        }
        catch (ArrayIndexOutOfBoundsException{} imposs) { }
        int length = endorse(length_, {TT<-SUP});

        
        if (debugLog != null) debugLog.println("  starting to combine all tt pet share decommitments for block " + block);
        ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] ret = new ElGamalCiphertext[length];
        PETDecommitment{TT<-SUP;TT<-TELLS}[] decs = new PETDecommitment[allDecs.length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < allDecs.length; j++) {
                PETDecommitment petd = null;
                try {
                    decs[j] = null; 
                    TabTellerPETShareDecommitments ttdecs = allDecs[j];
                    petd = ttdecs.decommitments[i];
                }
                catch (NullPointerException ignore) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                try {
                    decs[j] = endorse(petd, {TT<-SUP});
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            CryptoException cex = null;
            try {
                ret[i] = null; 
                ret[i] = CryptoUtil.factory().combinePETShareDecommitments(new label {TT<-SUP;TT<-TELLS}, decs, elGamalParameters);
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }            
            catch (NullPointerException imposs) { }
            catch (CryptoException ex) {
                // the verification of the decommits failed.
                cex = ex;
            }
            CryptoException ecex = endorse(cex, {TT<-SUP});
            if (ecex != null) throw ecex;
        }
        if (cache != null) {
            cache.setCiphertexts(meta, block, ballotIndex, TT, SUP, TELLS, ret);
        }        
        return endorse(ret, {TT<-SUP});
    }
    
    /**
     * returns all of the posted TTPETShareCommitments, or null if not all of them have been posted yet.
     */
    boolean{TT<-SUP} hasAllTTPETShareCommitments{TT<-SUP}(PETCache{TT<-SUP} cache, 
            String{TT<-SUP} meta, 
            ElectionDetails{TT<-SUP} electionDetails, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex,
            TellerDetails{TT<-SUP} tellerDetails) 
    throws (IOException{TT<-SUP})  
        // go to the bulletin board, and try to retreive all tab teller PET share decommitments
        if (electionDetails == null || bb == null) return false;
        ElectionID electionID = electionDetails.electionID; 
        if (electionID == null || cache == null) return false;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        IOException ioex = null;
        for (int i = 0; i < numTabTellers; i++) {                    
            boolean has_ = false;
            try {
                has_ = hasTTPETShareCommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, i+1);
            }
            catch (IOException ex) {
                ioex = ex;
            }

            IOException eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            boolean has = endorse(has_, {TT<-SUP});
            if (!has) return false;                        
        }
        return true;
    }

    boolean{} hasTTPETShareCommitments{}(PETCache{TT<-SUP} cache, 
            String{TT<-SUP} meta,
            ElectionDetails{TT<-SUP} electionDetails,
            int{TT<-SUP} block,
            int{TT<-SUP} ballotIndex,
            TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex)
    throws (IOException{}) {
        if (electionDetails == null || bb == null) return false;
        ElectionID electionID = electionDetails.electionID;            
        if (electionID == null) return false;

        if (cache != null && cache.hasCommitment(meta, block, ballotIndex, tellerIndex)) {
            return true;
        }
        return retrieveTTPETShareCommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, tellerIndex) != null;
    }
    /**
     * returns the posted TTPETShareCommitments for tab teller tellerIndex, or null if they have not been posted yet.
     */
    TabTellerPETShareCommitments{} retrieveTTPETShareCommitments{}(PETCache{} cache, 
            String{} meta,
            ElectionDetails{} electionDetails,
            int{} block,
            int{} ballotIndex,
            TellerDetails{} tellerDetails, int{} tellerIndex)
    throws (IOException{}) {
        // go to the bulletin board, and try to retreive the PET share commitments for tab teller tellerIndex
        try {
            if (electionDetails == null || bb == null) return null;
            ElectionID electionID = electionDetails.electionID;            
            if (electionID == null) return null;

            if (cache != null && cache.hasCommitment(meta, block, ballotIndex, tellerIndex)) {
                return cache.getCommitment(meta, block, ballotIndex, tellerIndex);
            }
            String metaSuffix = TTTabulation[TT, SUP, TELLS].blockMeta(meta, electionDetails, block, ballotIndex, tellerIndex);
            List[{}] results = bb.retrieveParams(XMLDeserializers.TabTellerPETShareCommitments(), TabTellerPETShareCommitments.META_PREFIX+metaSuffix, null, null);
            if (results == null) return null;
            
            // parse and verify the posts. 
            for (int i = 0; i < results.size(); i++) {
                BBPost post = null;
                try {
                    post = (BBPost)results.get(i);
                }
                catch (ClassCastException imposs) { }
                catch (IndexOutOfBoundsException imposs) { }
                if (post == null) continue;
                // parse and validate
                try {
                    TabTellerPETShareCommitments c = (TabTellerPETShareCommitments)post.msg;
                    // check the signature of c
                    if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
                        if (cache != null) {
                            cache.setCommitment(meta, block, ballotIndex, tellerIndex, c);
                        }
                        return c;
                    }
                }
                catch (ClassCastException ignore) { } 
            }
            return null;  
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }        
    }
    
    boolean{TT<-SUP} hasAllTTPETShareDecommitments{TT<-SUP}(
            PETCache{TT<-SUP} cache, String{TT<-SUP} meta, 
            ElectionDetails{TT<-SUP} electionDetails, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex, 
            TellerDetails{TT<-SUP} tellerDetails,
            PETShare{TT->TT;TT<-TT}[]{TT<-SUP} shares) 
    throws (IOException{TT<-SUP})  
        // go to the bulletin board, and try to retreive all tab teller PET share decommitments
        if (electionDetails == null || bb == null || cache == null) return false;
        ElectionID electionID = electionDetails.electionID; 
        if (electionID == null) return false;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        IOException ioex = null;
        for (int i = 0; i < numTabTellers; i++) {
            boolean has_ = false;
            try {
                has_ = hasTTPETShareDecommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, i+1, shares);
            }
            catch (IOException ex) {
                ioex = ex;
            }

            IOException eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            boolean has = endorse(has_, {TT<-SUP});
            if (!has) return false;                        
        }
        return true;
    }

    TabTellerPETShareDecommitments{TT<-SUP;TT<-TELLS}[]{TT<-SUP} retrieveAllTTPETShareDecommitments{TT<-SUP}(
            PETCache{TT<-SUP} cache, 
            String{TT<-SUP} meta, 
            ElectionDetails{TT<-SUP} electionDetails, 
            int{TT<-SUP} block, 
            int{TT<-SUP} ballotIndex, 
            TellerDetails{TT<-SUP} tellerDetails,
            PETShare{TT->TT;TT<-TT}[]{TT<-SUP} shares) 
    throws (IOException{TT<-SUP})  
        // go to the bulletin board, and try to retreive all tab teller PET share decommitments
        if (electionDetails == null || bb == null || cache == null) return null;
        ElectionID electionID = electionDetails.electionID; 
        if (electionID == null) return null;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        if (!hasAllTTPETShareDecommitments(cache, 
                                           meta, 
                                           electionDetails, 
                                           block, 
                                           ballotIndex, 
                                           tellerDetails, shares)) {
            return null;
        }
        IOException ioex = null;
                
        TabTellerPETShareDecommitments{TT<-SUP;TT<-TELLS}[] ret = new TabTellerPETShareDecommitments[numTabTellers<0?0:numTabTellers];
        for (int i = 0; i < numTabTellers; i++) {
            TabTellerPETShareDecommitments d_ = null;
            // retrieve the decommitment
            try {
                d_ = retrieveTTPETShareDecommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, i+1, shares);
            }
            catch (IOException ex) {
                ioex = ex;
            }

            IOException eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            TabTellerPETShareDecommitments d = endorse(d_, {TT<-SUP});

            if (d == null) return null;
            try {                
                ret[i] = d;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        return ret;

    }

    boolean{} hasTTPETShareDecommitments{}(
            PETCache{} cache,
            String{} meta,            
            ElectionDetails{TT<-SUP} electionDetails,
            int{TT<-SUP} block,
            int{TT<-SUP} ballotIndex,
            TellerDetails{TT<-SUP} tellerDetails, 
            int{TT<-SUP} tellerIndex,
            PETShare{TT->TT;TT<-TT}[]{TT<-SUP} shares) throws (IOException{})  
        if (electionDetails == null || bb == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;
        if (cache != null && cache.hasDecommitment(meta, block, ballotIndex, tellerIndex)) {
            return true;
        }
        return retrieveTTPETShareDecommitments(cache, meta, electionDetails, block, ballotIndex, tellerDetails, tellerIndex, shares) != null;
    }
    /**
     * returns the posted retrieveTTPETShareDecommitments for tab teller tellerIndex, or null if they have not been posted yet.
     */
    TabTellerPETShareDecommitments{} retrieveTTPETShareDecommitments{}(
            PETCache{} cache,
            String{} meta,            
            ElectionDetails{} electionDetails,
            int{} block,
            int{} ballotIndex,
            TellerDetails{} tellerDetails, 
            int{} tellerIndex,
            PETShare{TT->TT;TT<-TT}[]{} shares) throws (IOException{})  
        // go to the bulletin board, and try to retreive PET share decommitments for tab teller tellerIndex
        try {
            if (electionDetails == null || bb == null) return null;
            ElectionID electionID = electionDetails.electionID;
            if (electionID == null) return null;
            if (cache != null && cache.hasDecommitment(meta, block, ballotIndex, tellerIndex)) {
                return cache.getDecommitment(meta, block, ballotIndex, tellerIndex);
            }
            String metaSuffix = TTTabulation[TT, SUP, TELLS].blockMeta(meta, electionDetails, block, ballotIndex, tellerIndex);
            List[{}] results = bb.retrieveParams(XMLDeserializers.TabTellerPETShareDecommitments(), TabTellerPETShareDecommitments.META_PREFIX+metaSuffix, null, null);
            if (results == null) return null;
            
            // parse and verify the posts.             
            for (int i = 0; i < results.size(); i++) {
                BBPost post = null;
                try {
                    post = (BBPost)results.get(i);
                }
                catch (ClassCastException imposs) { }
                catch (IndexOutOfBoundsException imposs) { }
                if (post == null) continue;
                // parse and validate
                try {
                    TabTellerPETShareDecommitments c = (TabTellerPETShareDecommitments)post.msg;
                    // check the signature of c
                    if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
                        
                        // verify the single decommitment
                        if (debugLog != null) debugLog.println("Verifying PET decommitments for " + meta + " for block " + block + " ballot " + ballotIndex + " teller " + tellerIndex);
                        verifyPETDecommitments(cache, electionDetails, tellerDetails, meta, block, ballotIndex, c, shares, tellerIndex);
                        
                        if (cache != null) {
                            cache.setDecommitment(meta, block, ballotIndex, tellerIndex, c);
                        }
                        return c;
                    }
                }
                catch (ClassCastException ignore) {  }                 
            }
            return null;  
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }        
    }

   
}