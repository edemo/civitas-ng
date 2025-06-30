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
public class TTTabulation[principal TT, principal SUP, principal TELLS] {
    private final PrivateKey{TT<-TT} tellerPrivKey;
    private final TTStore[TT]  ttstore;
    private final BBClientUtil  bb;
    private final TTTabulationMixes[TT,SUP,TELLS]  tttm;
    private final TTTabulationPET[TT,SUP,TELLS]  tttpet;
    final TTTabulationUtil[TT,SUP,TELLS]  tttUtil;
    private final TTVotes[TT,SUP,TELLS]  ttVotes;
    private final PrintStream[{*->*}]  debugLog;
    
    static final String  META_VOTES_TO_VOTES = "votes-votes";
    static final String  META_VOTES_TO_ROLL = "votes-roll";
    static final String  META_FINAL_VOTE_DECRYPTION = "finalVotes";

    TTTabulation(PrivateKey{TT<-TT} tellerPrivKey,
               TTStore[TT]  ttstore,
               BBClientUtil  bb,
               PrintStream[{*->*}]  debugLog) {
        this.tellerPrivKey = tellerPrivKey;
        this.ttstore = ttstore;
        this.bb = bb;
        this.tttpet = new TTTabulationPET[TT,SUP,TELLS](tellerPrivKey, this, ttstore, bb, debugLog);
        this.tttm = new TTTabulationMixes[TT,SUP,TELLS](tellerPrivKey, ttstore, bb, this, tttpet, debugLog);

        this.tttUtil = new TTTabulationUtil[TT,SUP,TELLS](tellerPrivKey, ttstore, bb);
        this.ttVotes = new TTVotes[TT,SUP,TELLS](tellerPrivKey, ttstore, bb, tttUtil);
        this.debugLog = debugLog;
    }
    
    
    void tabulate{TT<-TT meet TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails):{TT<-SUP} 
    throws IOException  
        if (ttstore == null || electionDetails == null || bb == null 
                || tttm == null || tttpet == null || tttUtil == null || ttVotes == null) return;
        if (debugLog != null) debugLog.println("tabulate called");
        final BallotDesign ballotDesign = electionDetails.ballotDesign;
        if (ballotDesign == null) return;

        final ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return;
        int tellerIndex = ttstore.retrieveIndex(SUP, electionID);
        ElectionCache electionCache = tttUtil.electionCache(electionID, tellerIndex);
                
        // Check if we have abandoned this election.
        if (ttstore.isAbandonedElection(SUP, electionID)) {
            throw new IOException(ttstore.retrieveAbandonment(SUP, electionID));
        }

        IOException ioex = null;
        IOException eioex = null;
        boolean failed = false;
        boolean efailed = false;

        // check election status.
        int electionStatus_ = -1;
        try {
            electionStatus_ = ElectionUtil.retrieveElectionStatus(TT, electionID, electionCache);
        }
        catch (IOException e) {
            ioex = e;
        }
        int electionStatus = endorse(electionStatus_, {TT<-SUP});
        
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        
        TellerDetails tellerDetails_ = null;
        if (electionStatus == ElectionUtil.STATUS_ABANDONED) {
            try {
                ElectionAbandonment ea = ElectionUtil.retrieveAbandonmentReason(electionID, electionCache); 
                if (ea != null) {
                    throw new IOException(ea.reason);                            
                }
            }
            catch (IOException e) {
                ioex = e;
            }
        }
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;

        if (electionStatus != ElectionUtil.STATUS_STOPPED) {
            // election not ready for tabulation.
            if (debugLog != null) debugLog.println("Exiting because status is not stopped");
            return;
        }
        
        TellerDetails tellerDetails = endorse(tellerDetails_, {TT<-SUP});

        if (tellerDetails == null) {
            try {
                tellerDetails_ = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
            }
            catch (IOException e) {
                ioex = e;
            }         
            tellerDetails = endorse(tellerDetails_, {TT<-SUP});
        }
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
         
        
        ElGamalPublicKey tabTellerSharedKey_ = null;
        try {
            tabTellerSharedKey_ = ElectionUtil.retrieveTabTellerSharedPublicKey(electionDetails, electionCache);
        }
        catch (IOException e) {
            ioex = e;
        }
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        ElGamalPublicKey tabTellerSharedKey = endorse(tabTellerSharedKey_, {TT<-SUP});

        /*
         * Summary of the tabulation process:
         * 0. for each voter block
         *      make sure all tab tellers agree on which votes to tabulate 
         * 1. Plaintext Equivalence Test of the votes to the votes
         *      Prevents same capability being used for multiple votes
         *      for each block:
         *         a. post commitment
         *         b. post decommitment once all tellers posted commitments
         *         c. post distributed decryption once all tellers posted decommitments
         * 2. Mix votes
         * 3. Mix electoral roll 
         * 4. Post Mix hash revelations
         * 5. Post Mix revelations
         * 6. Check other tellers mix revelations
         * 7. Plaintext Equivalence test between the (mixed) votes and the (mixed) electoral roll
         *      Determines which votes use legitamate capabilities
         *      for each block:
         *         a. post commitment
         *         b. post decommitment once all tellers posted commitments
         *         c. post distributed decryption once all tellers posted decommitments
         */
        
        // we need to perform PET of the votes to the votes.
        int numberBlocks_ = 0;
        int numberVoterBlocks_ = 0;
        try {
            numberVoterBlocks_ = ElectionUtil.numberVoterBlocks(electionDetails, electionCache);
            numberBlocks_ = ElectionUtil.numberBlocks(electionDetails, electionCache);
            if (numberBlocks_  <= 0) {
                throw new IOException("Could not retrieve number of blocks, or voter blocks.");                            
            }
        }
        catch (IOException e) {
            ioex = e;
        }
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        
        int numberBlocks = endorse(numberBlocks_, {TT<-SUP});
        int numberVoterBlocks = endorse(numberVoterBlocks_, {TT<-SUP});
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);        
        
        // for each voter block, check that each tt agrees on what the voter submissions are.
        for (int voterBlock = 0; voterBlock < numberVoterBlocks; voterBlock++) {
            // has this teller posted what it believes the votes for this block are?
            boolean postedVoterBlockContents_ = false;
            try {
                postedVoterBlockContents_ = (ttVotes.retrieveVoterBlockContents(electionDetails, electionCache, voterBlock, tellerIndex) != null);
            }
            catch (IOException e) {
                ioex = e;
            }   
            boolean postedVoterBlockContents = endorse(postedVoterBlockContents_, {TT<-SUP});
            eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            if (!postedVoterBlockContents) {
                // need to post the voter block contents
                try {
                    List[{}] voterSubmissions = ttVotes.retrieveVoterSubmissions(electionDetails, electionCache, tabTellerSharedKey, voterBlock);
                    // compute the hash                
                    String hash = null;
                    try {
                        MessageDigest[{}] md = CryptoUtil.factory().messageDigest(new label {});
                        if (voterSubmissions != null) {
                            Iterator[{}] iter = voterSubmissions.iterator();
                            while (iter != null && iter.hasNext()) {
                                try {
                                    VoterSubmission vs = (VoterSubmission)iter.next();
                                    if (vs != null) {
                                        StringWriter[{}] sb = new StringWriter[{}]();
                                        vs.toXML(new label {}, new PrintWriter[{}](sb));
                                        md.update(sb.toString());
                                    }
                                }
                                catch (ClassCastException imposs) { }
                                catch (NoSuchElementException imposs) { }
                            }
                        }
                        hash = CryptoUtil.factory().constBytesToBase64(new label {}, md.digest());
                    }
                    catch (NullPointerException imposs) { }                

                    // post the hash
                    TabTellerVoterBlockContents mybc = new TabTellerVoterBlockContents(tellerIndex, voterBlock, hash);
                    try {
                        bb.post(TabTellerVoterBlockContents.meta(tellerIndex, voterBlock), mybc, tellerPrivKey);
                    }
                    catch (IllegalArgumentException e) {
                        throw new IOException(e.getMessage());
                    }


                    // broadcast a hint to all other tellers
                    tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
            }
        }
        
        // now make sure that all the other TTs agree.
        for (int voterBlock = 0; voterBlock < numberVoterBlocks; voterBlock++) {
            TabTellerVoterBlockContents mybc = null;
            try {
                mybc = ttVotes.retrieveVoterBlockContents(electionDetails, electionCache, voterBlock, tellerIndex);
            }
            catch (IOException e) {
                ioex = e;
            }
            eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            
            for (int t = 0; t < numTabTellers; t++) {
                int checkTellerIndex = t+1;
                if (checkTellerIndex == tellerIndex) continue;
                
                TabTellerVoterBlockContents bc = null;
                int retriesLeft = tttUtil.maxRetries(numTabTellers);
                try {
                    while (bc == null && retriesLeft-- > 0) {
                        bc = ttVotes.retrieveVoterBlockContents(electionDetails, electionCache, voterBlock, checkTellerIndex);

                        if (retriesLeft > 0 && bc == null) {
                            // sleep for a bit
                            if (debugLog != null) debugLog.println("Sleeping for TabTellerVoterBlockContents for voter block " + voterBlock + ". Retries left = " + retriesLeft);
                            jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                        }
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                
                if (endorse(retriesLeft < 0 || bc == null, {TT<-SUP})) {
                    if (debugLog != null) debugLog.println("Exiting because timed out for tab teller voter block contents");
                    return;
                }

                try {
                    if (mybc == null || bc == null || mybc.hash == null || !mybc.hash.equals(bc.hash)) {
                        // it's bad, we need to abandon the election.
                        tttUtil.abandonElection(electionID,
                                                "Tabulation tellers disagree on the set of votes to tabulate for voter block " + voterBlock, 
                                                tellerIndex);
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                
            }
        }
        
        PETCache votePETCache = null;
        if (electionCache != null) {
            votePETCache = electionCache.newPETCache(META_VOTES_TO_VOTES, numberBlocks, numTabTellers);
        }

        for (int block = 0; block < numberBlocks; block++) {                
            // how many ballots in this block?
            int blockBallotSize = retrieveValidVerifiableVoteSize(electionDetails, electionCache, tabTellerSharedKey, block);
            
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // Has this teller posted the commitments for the vote-vote PETs?
                boolean postedVotePETCommitment_ = false;
                try {
                    postedVotePETCommitment_ = tttpet.hasTTPETShareCommitments(votePETCache, 
                                                                               META_VOTES_TO_VOTES, 
                                                                               electionDetails, 
                                                                               block,
                                                                               ballotIndex,
                                                                               tellerDetails, 
                                                                               tellerIndex);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVotePETCommitment = endorse(postedVotePETCommitment_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVotePETCommitment) {
                    // the teller hasn't posted commitments for the PETs. We will now do so.
                    try {
                        if (debugLog != null) debugLog.println("Posting vote-vote pet commit for block " + block + " ballot " + ballotIndex);
                        failed = (!tttpet.postVotePETCommitment(votePETCache, electionDetails, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, block, ballotIndex));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post vote PET commit");
                    return;
                }
            }
        }
        
        for (int block = 0; block < numberBlocks; block++) {                
            int blockBallotSize = retrieveValidVerifiableVoteSize(electionDetails, electionCache, tabTellerSharedKey, block);
            
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // this teller has posted its commitments for the PETs. Has it posted its decommitment?
                boolean postedVotePETDecommitment_ = false;
                PETShare{TT->TT;TT<-TT}[] shares = ttstore.retrieveVoteVotePETShares(SUP, electionID, block, ballotIndex);
                try {            
                    postedVotePETDecommitment_ = tttpet.hasTTPETShareDecommitments(votePETCache, META_VOTES_TO_VOTES, electionDetails, block, ballotIndex, tellerDetails, tellerIndex, shares);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVotePETDecommitment = endorse(postedVotePETDecommitment_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVotePETDecommitment) {
                    // this teller hasn't posted its decommitment
                    try {
                        if (debugLog != null) debugLog.println("Posting vote-vote pet decommit for block " + block + " ballot " + ballotIndex);                    
                        failed = (!tttpet.postPETDecommitment(votePETCache, electionDetails, tellerDetails, META_VOTES_TO_VOTES, block, ballotIndex, tellerIndex, shares));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;            
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post vote PET decommit");
                    return;
                }
            }

        }
        
        for (int block = 0; block < numberBlocks; block++) {                
            int blockBallotSize = retrieveValidVerifiableVoteSize(electionDetails, electionCache, tabTellerSharedKey, block);
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // this teller has posted its decommitment. Has it posted its share of the distributed decryption?        
                boolean postedVotePETDecrypt_ = false;
                try {
                    postedVotePETDecrypt_ = hasTTDecryptShares(votePETCache, META_VOTES_TO_VOTES, electionDetails, block, ballotIndex, tellerDetails, tellerIndex);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVotePETDecrypt = endorse(postedVotePETDecrypt_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVotePETDecrypt) {
                    // this teller hasn't posted it's decrypt shares yet.
                    try {
                        if (debugLog != null) debugLog.println("Posting vote-vote pet decrypt for block " + block + " ballot " + ballotIndex);
                        failed = (!tttpet.postPETDecryptShare(votePETCache, 
                                                              electionDetails, 
                                                              tellerDetails, 
                                                              META_VOTES_TO_VOTES, 
                                                              block, ballotIndex, 
                                                              tellerIndex,
                                                              ttstore.retrieveVoteVotePETShares(SUP, electionID, block, ballotIndex)));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post vote PET decrypt share");
                    return;
                }
                // at this point the vote to vote PETs have been done. We can clear the vote-vote PETs 
                tttUtil.clearVoteVotePETShares(electionID, block, ballotIndex);
            }
        }
        
        
        // retrieve the condition holder
        final ConditionHolder[{TT<-TT}] allMixesDone = findMixesDoneConditionHolder(electionDetails, numberBlocks);
        
        // Check if mixing has been done or needs to be done
        boolean isVoteMixesAllDone_ = false;
        try {
            try {
                isVoteMixesAllDone_ = tttm.checkAndMix(electionDetails, votePETCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, true, false, numberBlocks, allMixesDone);
            }
            catch (IllegalArgumentException e) { 
                throw new IOException(e.getMessage());
            }               
            catch (CryptoException e) {
                // Abandon the election
                tttUtil.abandonElection(electionID,
                                "Cannot perform vote mix: " + e.getMessage(), 
                                tellerIndex);
            }               
        }
        catch (IOException e) {
            ioex = e;
        }   
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        boolean isVoteMixesAllDone = endorse(isVoteMixesAllDone_, {TT<-SUP});

        // mix electoral roll
        boolean electoralMixesAllDone_ = false;
        try {
            try {
                electoralMixesAllDone_ = tttm.checkAndMix(electionDetails, votePETCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, false, true, numberBlocks, allMixesDone);
            }
            catch (IllegalArgumentException e) { 
                throw new IOException(e.getMessage());
            }               
            catch (CryptoException e) {
                // Abandon the election
                tttUtil.abandonElection(electionID,
                                "Cannot perform electoral roll mix: " + e.getMessage(), 
                                tellerIndex);
            }               
        }
        catch (IOException e) {
            ioex = e;
        }   
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        boolean electoralMixesAllDone = endorse(electoralMixesAllDone_, {TT<-SUP});
        
        if (!isVoteMixesAllDone || !electoralMixesAllDone) {
            // the mixes haven't been completed yet. There
            // is nothing more to do at the moment. We will be informed
            // by the previous teller when there is something to do.
            if (debugLog != null) debugLog.println("Mixes not yet done, exiting");
            return;
        }
        
        // set the condition!
        setAllMixesDone(allMixesDone);
        
        if (debugLog != null) debugLog.println("All mixes done");
        // Has this teller posted its release of the mix hashes yet?
        for (int{TT<-SUP} k = 0; k < 2; k++) {
            boolean isVoteMix = (k==0);
            for (int block = 0; block < numberBlocks; block++) {                
                boolean postedMixHashRevelation_ = false;
                try {
                    postedMixHashRevelation_ = tttm.hasTTMixHashRevelation(electionCache, electionDetails, tellerDetails, tellerIndex, isVoteMix, block);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedMixHashRevelation = endorse(postedMixHashRevelation_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;

                if (!postedMixHashRevelation) {
                    // the teller hasn't posted the relase for the mixes yet. We will now do so.
                    // this teller hasn't posted it's decrypt shares yet.
                    try {
                        if (debugLog != null) debugLog.println("Posting reveal mix hash " + (isVoteMix?"vote":"er") + " mix, block " + block);
                        failed = (!tttm.postRevealMixHash(electionDetails, tellerDetails, tellerIndex, isVoteMix, block));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;

                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post reveal mix hash");
                    return;
                }
            }
        }

        // Has this teller posted its revelation of the mixes yet?
        for (int k = 0; k < 2; k++) {
            boolean isVoteMix = (k==0);

            for (int block = 0; block < numberBlocks; block++) {                
                boolean postedMixRevelation_ = false;
                try {
                    postedMixRevelation_ = (tttm.retrieveTTMixRevelation(electionDetails, tellerDetails, tellerIndex, isVoteMix, block) != null);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedMixRevelation = endorse(postedMixRevelation_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;

                if (!postedMixRevelation) {
                    // the teller hasn't posted the relase for the mixes yet. We will now do so.
                    // this teller hasn't posted it's decrypt shares yet.
                    try {
                        if (debugLog != null) debugLog.println("Posting revelation " + (isVoteMix?"vote":"er") + " mix, block " + block);
                        failed = (!tttm.postRevealMix(electionDetails, electionCache, tellerDetails, tellerIndex, isVoteMix, block));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;

                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post hash revelation");
                    return;
                }
            }
        }
        
        // go through each of the tellers, and check their revelations.
        boolean allMixesChecked = false;
        try {
            if (debugLog != null) debugLog.println("Checking all revelations");
            allMixesChecked = tttm.checkAllMixRevelations(votePETCache, electionDetails, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, numberBlocks);
        }
        catch (IOException e) {
            ioex = e;
        }                
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        if (!endorse(allMixesChecked, {TT<-SUP})) {
            // mixes aren't ready yet.
            if (debugLog != null) debugLog.println("Exiting because the mix revelations are not yet ready to be checked.");
            return;
        }
        
        // at this point, we have checked all of the teller's mix revelations, and decided they passed muster.
        // We no longer need to store the mix info 
        for (int block = 0; block < numberBlocks; block++) {                        
            tttUtil.clearVoteMixInfo(electionID, block);
            tttUtil.clearElectoralRollMixInfo(electionID, block);
        }
        
        // we now need to perform PET on the electoral roll and the votes.
        PETCache voteRollPETCache = null;
        if (electionCache != null) {
            voteRollPETCache = electionCache.newPETCache(META_VOTES_TO_ROLL, numberBlocks, numTabTellers);
        }
        for (int block = 0; block < numberBlocks; block++) {                
            int blockBallotSize = tttm.retrieveInitialMixSize(electionDetails, votePETCache, electionCache, tabTellerSharedKey, true, block, tellerDetails, tellerIndex);
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // Has this teller posted the commitments for the roll-vote PETs?
                boolean postedVoteRollPETCommitment_ = false;
                try {
                    postedVoteRollPETCommitment_ = tttpet.hasTTPETShareCommitments(voteRollPETCache, META_VOTES_TO_ROLL, electionDetails, block, ballotIndex, tellerDetails, tellerIndex);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVoteRollPETCommitment = endorse(postedVoteRollPETCommitment_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVoteRollPETCommitment) {
                    // the teller hasn't posted commitments for the PETs. We will now do so.
                    try {
                        if (debugLog != null) debugLog.println("Posting roll-vote pet commit for block " + block + " ballot " + ballotIndex);
                        failed = (!tttpet.postRollVotePETCommitment(voteRollPETCache, electionCache, electionDetails, tellerDetails, tellerIndex, block, ballotIndex));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post roll-vote pet commit.");
                    return;
                }
            }

        }

        for (int block = 0; block < numberBlocks; block++) {                
            int blockBallotSize = tttm.retrieveInitialMixSize(electionDetails, votePETCache, electionCache, tabTellerSharedKey, true, block, tellerDetails, tellerIndex);
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // this teller has posted its commitments for the PETs. Has it posted its decommitment?
                boolean postedVoteRollPETDecommitment_ = false;
                PETShare{TT->TT;TT<-TT}[] shares = ttstore.retrieveRollVotePETShares(SUP, electionID, block, ballotIndex);
                try {            
                    postedVoteRollPETDecommitment_ = tttpet.hasTTPETShareDecommitments(voteRollPETCache, 
                                                                                       META_VOTES_TO_ROLL, 
                                                                                       electionDetails, 
                                                                                       block,
                                                                                       ballotIndex,
                                                                                       tellerDetails, 
                                                                                       tellerIndex,
                                                                                       shares);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVoteRollPETDecommitment = endorse(postedVoteRollPETDecommitment_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVoteRollPETDecommitment) {
                    // this teller hasn't posted its decommitment
                    try {
                        if (debugLog != null) debugLog.println("Posting roll-vote pet decommit for block " + block + " ballot " + ballotIndex);
                        failed = (!tttpet.postPETDecommitment(voteRollPETCache, 
                                                              electionDetails, 
                                                              tellerDetails, 
                                                              META_VOTES_TO_ROLL, 
                                                              block,
                                                              ballotIndex,
                                                              tellerIndex,
                                                              shares));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;            
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post roll-vote pet decommit.");
                    return;
                }
            }
        }
        
        for (int block = 0; block < numberBlocks; block++) {                
            int blockBallotSize = tttm.retrieveInitialMixSize(electionDetails, votePETCache, electionCache, tabTellerSharedKey, true, block, tellerDetails, tellerIndex);
            for (int ballotIndex = 0; ballotIndex < blockBallotSize; ballotIndex++) {
                // this teller has posted its decommitment. Has it posted its share of the distributed decryption?
                boolean postedVoteRollPETDecrypt_ = false;
                try {
                    postedVoteRollPETDecrypt_ = hasTTDecryptShares(voteRollPETCache, META_VOTES_TO_ROLL, electionDetails, block, ballotIndex, tellerDetails, tellerIndex);
                }
                catch (IOException e) {
                    ioex = e;
                }   
                boolean postedVoteRollPETDecrypt = endorse(postedVoteRollPETDecrypt_, {TT<-SUP});
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                if (!postedVoteRollPETDecrypt) {
                    // this teller hasn't posted it's decrypt shares yet.
                    try {
                        if (debugLog != null) debugLog.println("Posting roll-vote pet decrypt for block " + block + " ballot " + ballotIndex);
                        failed = (!tttpet.postPETDecryptShare(voteRollPETCache, 
                                                              electionDetails, 
                                                              tellerDetails, 
                                                              META_VOTES_TO_ROLL, 
                                                              block, 
                                                              ballotIndex,
                                                              tellerIndex,
                                                              ttstore.retrieveRollVotePETShares(SUP, electionID, block, ballotIndex)));
                    }
                    catch (IOException e) {
                        ioex = e;
                    }                
                }
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
    
                efailed = endorse(failed, {TT<-SUP});
                if (efailed) {
                    if (debugLog != null) debugLog.println("Exiting because failed to post roll-vote pet decrypt share.");
                    return;
                }
                // We no longer need to store the PET info
                tttUtil.clearRollVotePETShares(electionID, block, ballotIndex);
            }
        }        

                 
        DistDecryptCache finalVoteCache = null;
        if (electionCache != null) {
            finalVoteCache = electionCache.newDistDecryptCache(META_FINAL_VOTE_DECRYPTION, numberBlocks, numTabTellers);
        }
        for (int block = 0; block < numberBlocks; block++) {
            boolean postedFinalVotePETDecrypt_ = false;
            try {
                postedFinalVotePETDecrypt_ = hasTTDecryptShares(finalVoteCache, META_FINAL_VOTE_DECRYPTION, electionDetails, block, 0, tellerDetails, tellerIndex);
            }
            catch (IOException e) {
                ioex = e;
            }   
            boolean postedFinalVotePETDecrypt = endorse(postedFinalVotePETDecrypt_, {TT<-SUP});
            eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            if (!postedFinalVotePETDecrypt) {
                // this teller hasn't posted its decrypt shares yet.
                try {
                    try {
                        if (debugLog != null) debugLog.println("Posting final vote decrypt for block " + block);
                        failed = (!postDecryptVotes(votePETCache, 
                                                    voteRollPETCache, 
                                                    finalVoteCache, 
                                                    electionDetails, 
                                                    electionCache, 
                                                    tabTellerSharedKey, 
                                                    tellerDetails, 
                                                    tellerIndex, 
                                                    block));
                    }
                    catch (CryptoException e) {
                        // Abandon the election
                        tttUtil.abandonElection(electionID,
                                        "Cannot post final decrypt votes: " + e.getMessage(), 
                                        tellerIndex);            
                    }               
                }
                catch (IOException e) {
                    if (debugLog != null) debugLog.println("Error posting final vote decrypt for block " + block + " " + e.getMessage());
                    ioex = e;
                }                
            }
            eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            if (debugLog != null) debugLog.println("Done posting final vote decrypt for block " + block );

            efailed = endorse(failed, {TT<-SUP});
            if (efailed) {
                if (debugLog != null) debugLog.println("Exiting because failed to post vote decrypt.");
                return;
            }
        }
        
        
        // that's it! Tabulation is done.
        if (debugLog != null) debugLog.println("posting election results...");
        boolean postedElectionResults_ = false;
        try {
            postedElectionResults_ = (ElectionUtil.retrieveTTElectionResults(electionID, tellerDetails, tellerIndex) != null);
        }
        catch (IOException e) {
            ioex = e;
        }   
        boolean postedElectionResults = endorse(postedElectionResults_, {TT<-SUP});
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        if (!postedElectionResults) {
            // this teller hasn't posted its election results
            try {
                try {
                    failed = (!postElectionResults(electionDetails, voteRollPETCache, finalVoteCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, numberBlocks));
                }
                catch (CryptoException e) {
                    // Abandon the election
                    tttUtil.abandonElection(electionID,
                                    "Cannot post final election results: " + e.getMessage(), 
                                    tellerIndex);            
                }               
                
            }
            catch (IOException e) {
                ioex = e;
            }                
        }
        eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;

        efailed = endorse(failed, {TT<-SUP});
        if (efailed) {
            if (debugLog != null) debugLog.println("Exiting because failed to post election results.");
            return;
        }
    }
    
    private void setAllMixesDone{TT<-SUP}(final ConditionHolder[{TT<-TT}]{TT<-SUP} allMixesDone_) 
     
        ConditionHolder[{TT<-TT}] allMixesDone = endorse(allMixesDone_, {TT<-TT});
        if (allMixesDone != null) {
            allMixesDone.c = true;
        }
        
    }
    private ConditionHolder[{TT<-TT}]{TT<-SUP} findMixesDoneConditionHolder{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, int{TT<-SUP} numberBlocks) {
        if (electionDetails == null || bb == null || ttstore == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;        

        // look for a mixinfo
        MixInfo[TT] mi = null;
        for (int block = 0; block < numberBlocks; block++) {
            mi = ttstore.retrieveVoteMixInfo(SUP, electionID, block, true);

            if (mi ==null) {
                mi = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, true);            
            }
        
            if (mi != null) {
                return mi.allMixesDone;
            }
        }

        // no mix infos yet
        return new ConditionHolder[{TT<-TT}]();
    }

    
    /**
     * Post the decryption of the final votes.
     */
    private boolean postDecryptVotes{TT<-SUP}(PETCache{TT<-SUP} votePETCache,
            PETCache{TT<-SUP} votesToRollCache,
            DistDecryptCache{TT<-SUP} finalVoteCache,
            ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey,
            TellerDetails{TT<-SUP} tellerDetails, 
            int{TT<-SUP} tellerIndex, int{TT<-SUP} block)
    throws (IOException{TT<-SUP}, CryptoException{TT<-SUP})  
        if (electionDetails == null || bb == null || ttstore == null || tttpet == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;

        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        ElGamalKeyPairShare keyShare = ttstore.retrieveKeyShare(SUP, electionID);
        
        // now get the final (encrypted) votes
        Vote{TT<-SUP;TT<-TELLS}[] finalVotes = null; 
        Vote{TT<-SUP;TT<-TELLS}[] finalVotes_ = null; 
        IOException ioex = null;
        try {
            try {
                if (debugLog != null) debugLog.println("  retrieve final votes to post decrypt votes for block " + block);
                finalVotes_ = retrieveFinalVotes(electionDetails, votesToRollCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, block);
                if (debugLog != null) debugLog.println("  finished retrieve final votes to post decrypt votes for block " + block);
            }
            catch (CryptoException e) {
                tttUtil.abandonElection(electionID,
                                "Unable to post decryption shares for votes: " + e.getMessage(), 
                                tellerIndex);
            }
        }
        catch (IOException e) {
            ioex = e;
        }
        IOException eioex = endorse(ioex, {TT<-SUP});
        if (eioex != null) throw eioex;
        finalVotes = endorse(finalVotes_, {TT<-SUP});
        if (endorse(finalVotes == null, {TT<-SUP})) return false;
        
        
        // post a decryption of the final votes.
        ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] cprime = tttUtil.votesToCiphertexts(finalVotes);
        finalVotes = null; // clear this pointer to allow garbage collection
        TabTellerDistributedDecryptions decryptShares = sharesForMsg(new label {TT<-SUP;TT<-TELLS}, cprime, keyShare, tellerIndex);

        try {
            try {
                if (debugLog != null) debugLog.println("  posting final votes decryptions! for block " + block);
                bb.post(TabTellerDistributedDecryptions.META_PREFIX+blockMeta(META_FINAL_VOTE_DECRYPTION, electionDetails, block, 0, tellerIndex), decryptShares, tellerPrivKey);
                if (finalVoteCache != null) {
                    finalVoteCache.setDecryptShare(META_FINAL_VOTE_DECRYPTION, block, 0, tellerIndex, decryptShares);
                }
                if (debugLog != null) debugLog.println("  done posting final votes decryptions! for block " + block);
                // broadcast a hint to all other tellers
                tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            }
            catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage());
            }
            catch (NullPointerException e) {
                throw new IOException("Unable to produce decryption shares");
            }
        }
        catch (IOException e) {
            ioex = e;
        }

        if (eioex != null) throw eioex;
        return true;
    }
    
    static String blockMeta(String m, ElectionDetails electionDetails, int block, int ballotIndex, int tellerIndex) {
        if (electionDetails == null) return null;
        return m + ":" + electionDetails.blockName(block) + ":" + ballotIndex + ":" + tellerIndex;
    }
       
    /**
     * Post the election results
     */
    private boolean postElectionResults{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            PETCache{TT<-SUP} cacheVTR,
            DistDecryptCache{TT<-SUP} finalVoteCache, 
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey,
            TellerDetails{TT<-SUP} tellerDetails, 
            int{TT<-SUP} tellerIndex, 
            int{TT<-SUP} numberBlocks):{} 
    throws IOException, CryptoException  
        if (electionDetails == null || bb == null || ttstore == null || tttpet == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;
        BallotDesign ballotDesign = electionDetails.ballotDesign;
        if (ballotDesign == null) return false;
        
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        //TallyState[{}] state = endorse(ballotDesign.newTallyState(new label {}), {} to {TT<-SUP});
        TallyState[{}] state = ballotDesign.newTallyState(new label {}, new label {TT<-SUP});
        if (state == null) {
//            abandonElection(electionID,
//                            "Internal error, unable to tally results",
//                            tellerIndex);    
             return false;
        }

        // loop over all the blocks
        for (int{TT<-SUP} block = 0; block < numberBlocks; block++) {
    
            // now get the plaintexts for the final votes
            IOException ioex = null;
            Vote{TT<-SUP;TT<-TELLS}[] finalVotes_ = null; 
            try {
                try {
                    if (debugLog != null) debugLog.println("Getting final votes block " + block);
                    finalVotes_ = retrieveFinalVotes(electionDetails, cacheVTR, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, block);
                }
                catch (CryptoException e) {
                    tttUtil.abandonElection(electionID,
                                    "Unable to retrieve final votes: " + e.getMessage(), 
                                    tellerIndex);
                }
            }
            catch (IOException e) {
                ioex = e;
            }
            IOException eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
            Vote{TT<-SUP;TT<-TELLS}[] finalVotes = endorse(finalVotes_, {TT<-SUP});
            if (finalVotes == null) {
                throw new IOException("Unable to retrieve final votes");
            }
    
            if (debugLog != null) debugLog.println("votesToCiphertexts " + block);
            ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] allCiphertexts = tttUtil.votesToCiphertexts(finalVotes); 
            if (debugLog != null) debugLog.println("done votesToCiphertexts " + block);

            // wait for all the tellers to post their shares of the vote decryption.
            ElGamalMsg{TT<-SUP; TT<-TELLS}[]{TT<-SUP} voteDecrypts = null;
            int retriesLeft = tttUtil.maxRetries(numTabTellers);
            while (voteDecrypts == null && retriesLeft-- > 0) {
                ElGamalMsg{TT<-SUP; TT<-TELLS}[] voteDecrypts_ = null;
                try {
                    try {
                        voteDecrypts_ = retrieveTTDecrypts(finalVoteCache, 
                                                           META_FINAL_VOTE_DECRYPTION,
                                                           block,
                                                           0, 
                                                          tabTellerSharedKey,
                                                          electionDetails,
                                                          electionCache,
                                                          tellerDetails,
                                                          tellerIndex,
                                                          allCiphertexts);
                    }
                    catch (CryptoException e) {
                        // failed crypto operation.
                        tttUtil.abandonElection(electionID,
                                        "Unable to retrieve decryption shares for votes: " + e.getMessage(), 
                                        tellerIndex);
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }   
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;

                voteDecrypts = endorse(voteDecrypts_, {TT<-SUP});

                if (retriesLeft > 0 && voteDecrypts == null) {
                    // sleep for a bit
                    if (debugLog != null) debugLog.println("Sleeping for final votes decrypts for block " + block + ". Retries left = " + retriesLeft);
                    jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                }
            }
            if (retriesLeft < 0 || voteDecrypts == null) return false;
            
            if (debugLog != null) debugLog.println("got all final vote decrypt for block " + block);

            try {
                try {
                    if (finalVotes.length != voteDecrypts.length) {
                        tttUtil.abandonElection(electionID,
                                        "Cannot compute election results: the number of votes (" + finalVotes.length +
                                        ") and the number of decryptions for those votes (" + voteDecrypts.length +
                                        ") disagree",
                                        tellerIndex);    
                        //return false;
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }   
                eioex = endorse(ioex, {TT<-SUP});
                if (eioex != null) throw eioex;
                    
                if (debugLog != null) debugLog.println("Tallying final votes block " + block);
                for (int i = 0; finalVotes != null && i < finalVotes.length; i++) {
                    try {
                        Vote v = finalVotes[i];
                        ElGamalMsg m = voteDecrypts[i];
                        // m is the decryption of v.encChoice
    
                        if (v != null) {
                            try {
                                ballotDesign.tally(new label {}, electionDetails.baseContext(electionDetails.voterBlockForBlock(block)), m, v.context, state, electionDetails.elGamalParameters);
                            }
                            catch (IllegalArgumentException ignore) { }
                        }                          
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }
                }
            }
            catch (IOException e) {
                // needs to endorse the exception, to avoid tainting the block variable.
                ioex = e;
            }
            eioex = endorse(ioex, {TT<-SUP});
            if (eioex != null) throw eioex;
        }

        if (debugLog != null) debugLog.println("Posting election results");
        ElectionResults results = new ElectionResults(ElectionResults.computerForTeller(tellerIndex), state.finalTally());
        try {
            bb.post(ElectionResults.metaForTeller(tellerIndex), results, tellerPrivKey);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
        if (debugLog != null) debugLog.println("Done posting election results");
        return true;
    }    

    VerifiableVote{}[]{TT<-SUP} retrieveValidVerifiableVotes{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, int{TT<-SUP} block) 
    throws (IOException{TT<-SUP})  
        if (ttVotes == null) return null;
        return ttVotes.retrieveValidVerifiableVotes(electionDetails, electionCache, tabTellerSharedKey, block);
    }

    int{TT<-TT} retrieveValidVerifiableVoteSize{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, int{TT<-SUP} block):{TT<-TT} 
    throws IOException  
        int ret = -1;
        IOException ioex = null;
        if (ttVotes != null) {
            try {
                ret = ttVotes.retrieveValidVerifiableVoteSize(electionDetails, electionCache, tabTellerSharedKey, block);
            }
            catch (IOException e) {
                ioex = e;
            }
        }
        IOException eioex = endorse(ioex, {TT<-TT});
        if (eioex != null) throw eioex;
        return endorse(ret, {TT<-TT});
    }

    /**
     * Retrieve from the bulletin board the final array of votes for an election,
     * i.e., the votes that contain capabilities that match one in the electoral roll.
     */
    private Vote{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} retrieveFinalVotes{TT<-SUP;TT<-TELLS}(final ElectionDetails{TT<-SUP;TT<-TELLS} electionDetails, 
            PETCache{TT<-SUP;TT<-TELLS} votesToRollCache,
            ElectionCache{TT<-SUP;TT<-TELLS} electionCache,
            ElGamalPublicKey{TT<-SUP;TT<-TELLS} tabTellerSharedKey,
            TellerDetails{TT<-SUP;TT<-TELLS} tellerDetails, 
            int{TT<-SUP;TT<-TELLS} tellerIndex, int{TT<-SUP;TT<-TELLS} block):{TT<-SUP;TT<-TELLS} 
    throws IOException, CryptoException 
     
        if (electionDetails == null || tttUtil == null || votesToRollCache == null) return null;
        BallotDesign ballotDesign = electionDetails.ballotDesign;
        if (ballotDesign == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;
        
//        if (electionCache != null) {
//            if (electionCache.hasFinalVotes(block)) {
//                return electionCache.getFinalVotes(block);
//            }
//        }

        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        
        
        // read in the final vote mix
        IOException ioex = null;
        VoteMix votes_ = null;
        try {
            if (debugLog != null) debugLog.println("  retrieving last vote mix for final votes");
            votes_ = (VoteMix)ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, true, block, numTabTellers, true);
        }
        catch (ClassCastException unlikely) { }
        catch (IOException e) { 
            ioex = e;
        }
        VoteMix votes = endorse(votes_, {TT<-SUP;TT<-TELLS});
        IOException eioex = endorse(ioex, {TT<-SUP;TT<-TELLS});
        if (eioex != null) throw eioex;
        
        if (votes == null) {
            if (debugLog != null) debugLog.println("  could not retrieve last vote mix for final votes");
            return null;
        }
                
        int votesSize = endorse(votes.size(), {TT<-SUP;TT<-TELLS});
        
        Vote{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} validVotes = new Vote[votesSize<0?0:votesSize]; 
        int{TT<-SUP;TT<-TELLS} count = 0;

        for (int ballotIndex = 0; ballotIndex < votesSize; ballotIndex++) {
            Vote vi = null; 
            try {
                vi = (Vote)votes.get(ballotIndex);
            }
            catch (IndexOutOfBoundsException imposs) { }
            catch (ClassCastException imposs) { }

            // Check the PETs that compare the vote to capabilities in the electoral roll
            // get the decryptions of the PETs.
            if (debugLog != null) debugLog.println("  retrieving TT decrypts for votes-roll PET for final votes for ballot " + ballotIndex);

            ElGamalMsg{TT<-SUP;TT<-TELLS}[] petDecrypts = null;
            int retriesLeft = tttUtil.maxRetries(numTabTellers);
            while (petDecrypts == null && retriesLeft-- > 0) {
                try {
                    try {
                        petDecrypts = retrieveTTDecrypts(votesToRollCache, 
                                                         META_VOTES_TO_ROLL,
                                                         block,
                                                         ballotIndex,
                                                         tabTellerSharedKey,
                                                         electionDetails,
                                                         electionCache,
                                                         tellerDetails,
                                                         tellerIndex,
                                                         votesToRollCache.getCiphertexts(META_VOTES_TO_ROLL, block, ballotIndex, TT, SUP, TELLS));
                    }
                    catch (CryptoException e) {
                        // failed crypto operation.
                        tttUtil.abandonElection(electionID,
                                        "Unable to retrieve decryption shares for votes to roll PET: " + e.getMessage(), 
                                        tellerIndex);
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }   
                eioex = endorse(ioex, {TT<-SUP; TT<-TELLS});
                if (eioex != null) throw eioex;


                if (retriesLeft > 0 && petDecrypts == null) {
                    // sleep for a bit
                    if (debugLog != null) debugLog.println("Sleeping for votes to roll PET decrypts for block " + block + " ballot " + ballotIndex + ". Retries left = " + retriesLeft);
                    jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                }
            }
            if (retriesLeft < 0 || petDecrypts == null) return null;
            if (petDecrypts == null) {
                // can't retrieve them.
                if (debugLog != null) debugLog.println("  could not retrieve TT decrypts for votes-roll PET for final votes for ballot " + ballotIndex);
                continue;
            }
            if (debugLog != null) debugLog.println("  done retrieving TT decrypts for votes-roll PET for final votes for ballot " + ballotIndex);

            for (int j = 0; j < petDecrypts.length; j++) {
                try {
                    if (CryptoUtil.factory().petResult(petDecrypts[j])) {
                        // the vote is valid! It matches a capability in the electoral roll!
                        validVotes[count++] = endorse(vi, {TT<-SUP;TT<-TELLS});
                        break;
                    }
                }
                catch (NullPointerException imposs) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }

            }
        }
        
        
        if (count != validVotes.length) {
        
            // didn't use the whole array. Shrink it down.
            Vote{TT<-SUP;TT<-TELLS}[] n = new Vote[count];
            for (int i = 0; i < count; i++) {
                try {
                    n[i] = validVotes[i];
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }

            }
            validVotes = n;
        }
        
//        if (electionCache != null) {
//            if (electionCache.hasFinalVotes(block)) {
//                return electionCache.getFinalVotes(block);
//            }
//        }
        return validVotes;

    }
    
    
    
    /**
     * Convert an array of decommitments into an array of messages.
     */
    private ElGamalMsg{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} 
    decryptionSharesToMsgs{TT<-SUP;TT<-TELLS}(ElectionDetails{TT<-SUP;TT<-TELLS} electionDetails,
               ElectionCache{TT<-SUP;TT<-TELLS} electionCache,
               TellerDetails{TT<-SUP;TT<-TELLS} tellerDetails,
               int{TT<-SUP;TT<-TELLS} tellerIndex,
               ElGamalPublicKey{TT<-SUP;TT<-TELLS} tabTellerSharedKey, 
               ElGamalCiphertext{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} allCiphertexts, 
               TabTellerDistributedDecryptions{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} allDecs):{TT<-SUP;TT<-TELLS} 
    throws CryptoException   
        if (allDecs == null) return null;
        // We need to have at least one element in the array, 
        // i.e., there must be at least one tabulation teller.
        if (allDecs.length < 1) return null;

        int length = 0;
        try {
            TabTellerDistributedDecryptions ttdecs = allDecs[0];
            if (ttdecs != null && ttdecs.decrypts != null) {
                length = ttdecs.decrypts.length;
            }
        }
        catch (ArrayIndexOutOfBoundsException imposs) { }

        ElGamalMsg{TT<-SUP;TT<-TELLS}[] ret = new ElGamalMsg[length];
        ElGamalDecryptionShare{TT<-SUP;TT<-TELLS}[] decs = new ElGamalDecryptionShare[allDecs.length];        
        for (int{TT<-SUP;TT<-TELLS} i = 0; i < length; i++) {
            
            for (int{TT<-SUP;TT<-TELLS} j = 0; j < allDecs.length; j++) {
                try {
                    // check that the decryption shares from teller ttdecs.tellerIndex verify
                    decs[j] = null; 
                    TabTellerDistributedDecryptions ttdecs = allDecs[j];
                    decs[j] = ttdecs.decrypts[i];
                    if (ttdecs.tellerIndex == tellerIndex) {
                        // no need to check our own decryptions
                        continue;
                    }

                    // verify the decryption
                    ElGamalKeyShare keyShare_ = null;
                    try {
                        if (debugLog != null) debugLog.println("start retrieveTabTellerSharedPublicKey");
                        keyShare_ = ElectionUtil.retrieveTabTellerSharedPublicKey(electionDetails.electionID, electionCache, ttdecs.tellerIndex); 
                    }
                    catch (NullPointerException ignore) { }
                    catch (IOException{} ignore) {
                        if (debugLog != null) debugLog.println("Error!!! 1 " + ignore.getMessage());
                        // whoops, can't retrieve the key share. For the moment, just fail. Should maybe try recovery...
                    }
                    ElGamalKeyShare keyShare = endorse(keyShare_, {TT<-SUP;TT<-TELLS});

                    if (debugLog != null) debugLog.println("start verify");
                    if (keyShare == null || !decs[j].verify(allCiphertexts[i], keyShare.pubKey())) {
                        // abandon the election
                        if (debugLog != null) debugLog.println("decryption  share of teller " + (j+1) + " fails verification");
                        throw new CryptoException("Decryption share of teller " + (j+1) + " fails verification");
                    }
                    if (debugLog != null) debugLog.println("done verify");
                }
                catch (NullPointerException ignore) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            
            try {
                ret[i] = null; 
                if (debugLog != null) debugLog.println("start combine decrypt share");
                ret[i] = CryptoUtil.factory().combineDecryptionShares(new label {TT<-SUP;TT<-TELLS}, allCiphertexts[i], decs, electionDetails.elGamalParameters);
                if (debugLog != null) debugLog.println("stop combine decrypt share");
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }      
            catch (NullPointerException imposs) { }
            catch (CryptoException ex) {
                // the verification of the shares failed. 
                throw ex;
            }
        }
        return ret;
    }
        

    /**
     * Create shares and proofs for the messages contained in m
     */
    TabTellerDistributedDecryptions{} sharesForMsg{}(  ElGamalCiphertext []{} m_, ElGamalKeyPairShare{TT->TT} keyShare_, int{} tellerIndex_) 
     
        ElGamalCiphertext [] m = endorse(m_, {TT<-TT});
        ElGamalKeyPairShare keyShare = endorse(keyShare_, {TT<-TT;TT->TT});
        int tellerIndex = endorse(tellerIndex_, {TT<-TT});
        if (m == null) return null;
        ElGamalDecryptionShare{TT<-TT}[] decrypts = new ElGamalDecryptionShare[m.length];
        ElGamalProofDiscLogEquality{TT<-TT}[] proofs = new ElGamalProofDiscLogEquality[m.length];
        
        for (int i = 0; i < m.length; i++) {
            ElGamalCiphertext mi_ = null;
            try {
                mi_ = m[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { }
            ElGamalCiphertext mi = endorse(mi_, {TT<-TT});

            if (mi == null) {
                // there was some failed crypto for this ciphertext.
                // do not put anything in the arrays.
            }
            else {
                try {
                    ElGamalDecryptionShare s = CryptoUtil.factory().constructDecryptionShare(new label {TT->TT;TT<-TT}, new label {TT<-TT}, mi, keyShare);
                    try {
                        decrypts[i] = declassify(s, {TT->TT;TT<-TT} to {TT<-TT}); // declassify the decryption, i.e. WHAT
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }

                    try {
                        proofs[i] = decrypts[i].getProof();
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }
                }
                catch (NullPointerException imposs) { }

            }
        }
        
        return new TabTellerDistributedDecryptions(new label {TT<-TT}, tellerIndex, decrypts, proofs);
    }

    ElGamalMsg{TT<-SUP;TT<-TELLS}[]{TT<-SUP; TT<-TELLS} retrieveTTDecrypts{TT<-SUP; TT<-TELLS}(
                           DistDecryptCache{TT<-SUP; TT<-TELLS} cache,
                           String{TT<-SUP; TT<-TELLS} meta, 
                           int{TT<-SUP; TT<-TELLS} block, 
                           int{TT<-SUP; TT<-TELLS} ballotIndex, 
                           ElGamalPublicKey{TT<-SUP; TT<-TELLS} tabTellerSharedKey,
                           ElectionDetails{TT<-SUP; TT<-TELLS} electionDetails, 
                           ElectionCache{TT<-SUP; TT<-TELLS} electionCache,
                           TellerDetails{TT<-SUP; TT<-TELLS} tellerDetails,
                           int{TT<-SUP;TT<-TELLS} tellerIndex,
                           ElGamalCiphertext{TT<-SUP;TT<-TELLS}[]{TT<-SUP; TT<-TELLS} allCiphertexts):{TT<-SUP;TT<-TELLS} 
    throws IOException, CryptoException 
    where caller(TT)
    {
        // go to the bulletin board, and try to retreive all tab teller decryption shares
        if (electionDetails == null || bb == null || cache == null) return null;
        ElectionID electionID = electionDetails.electionID;
        
        if (debugLog != null) debugLog.println("retrieve tt decrypts for " + meta + " block " + block + " ballot " + ballotIndex);
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        TabTellerDistributedDecryptions{TT<-SUP;TT<-TELLS}[] allDecryptShares = new TabTellerDistributedDecryptions[numTabTellers<0?0:numTabTellers];
        for (int i = 0; i < numTabTellers; i++) {
            TabTellerDistributedDecryptions c_ = null;
            IOException ioex = null;
            // retrieve the decryptShare
            try {
                c_ = retrieveTTDecryptShares(cache, meta, electionDetails, block, ballotIndex, tellerDetails, i+1);
            }
            catch (IOException ex) {
                if (debugLog != null) debugLog.println("Error 2!: " + ex.getMessage() + " meta " + meta + " block " + block);
                ioex = ex;
            }
            
            IOException eioex = endorse(ioex, {TT<-SUP;TT<-TELLS});
            if (eioex != null) throw eioex;
            
            TabTellerDistributedDecryptions c = endorse(c_, {TT<-SUP;TT<-TELLS});

            if (c == null) return null;
            try {                
                allDecryptShares[i] = c;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }

        ElGamalMsg{TT<-SUP;TT<-TELLS}[] ret = null;
        CryptoException cex = null;
        try {
            if (debugLog != null) debugLog.println("decryption shares to msgs for " + meta + " block " + block + " ballot " + ballotIndex);
            ret = decryptionSharesToMsgs(electionDetails, electionCache, tellerDetails, tellerIndex, tabTellerSharedKey, allCiphertexts, allDecryptShares);
            if (debugLog != null) debugLog.println("done decryption shares to msgs for " + meta + " block " + block + " ballot " + ballotIndex);
        }
        catch (CryptoException e) { 
            cex = e;
        }
        CryptoException ecex = endorse(cex, {TT<-SUP; TT<-TELLS});
        if (ecex != null) throw ecex;                                         
        return ret;
    }

    private boolean{} hasTTDecryptShares{}(DistDecryptCache{} cache,
            String{} meta, 
            ElectionDetails{} electionDetails, 
            int{} block,
            int{} ballotIndex,
            TellerDetails{} tellerDetails, 
            int{} tellerIndex) throws (IOException{}) {
        if (electionDetails == null || bb == null) return false;
        if (cache != null && cache.hasDecryptShare(meta, block, ballotIndex, tellerIndex)) {
            return true;
        }
        return retrieveTTDecryptShares(cache, meta, electionDetails, block, ballotIndex, tellerDetails, tellerIndex) != null;
    }
            
    private TabTellerDistributedDecryptions{} retrieveTTDecryptShares{}(DistDecryptCache{} cache,
            String{} meta, 
            ElectionDetails{} electionDetails, 
            int{} block, 
            int{} ballotIndex, 
            TellerDetails{} tellerDetails, 
            int{} tellerIndex) throws (IOException{}) {
        // go to the bulletin board, and try to retreive all tab teller decryption shares
        try {
            if (electionDetails == null || bb == null) return null;
            if (cache != null && cache.hasDecryptShare(meta, block, ballotIndex, tellerIndex)) {
                return cache.getDecryptShare(meta, block, ballotIndex, tellerIndex);
            }
            
            String metaSuffix = blockMeta(meta, electionDetails, block, ballotIndex, tellerIndex);
            List[{}] results = bb.retrieveParams(XMLDeserializers.TabTellerDistributedDecryptions(), TabTellerDistributedDecryptions.META_PREFIX+metaSuffix, null, null);
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
                    TabTellerDistributedDecryptions c = (TabTellerDistributedDecryptions)post.msg;
                    // check the signature of c
                    if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
                        if (cache != null) {
                            cache.setDecryptShare(meta, block, ballotIndex, tellerIndex, c);
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
