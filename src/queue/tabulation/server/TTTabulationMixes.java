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
 * Utility class to help with the mixing portion of the tabulation protocol. 
 * The principal TT should be the tab teller's public key.
 * The principal SUP is the election supervisor.
 * The principal TELLS represents all of the tabulation tellers, i.e., it is a principal that
 * every tabulation teller can act for.
 */
public class TTTabulationMixes[principal TT, principal SUP, principal TELLS] {
    private final PrivateKey{TT<-TT} tellerPrivKey;
    private final TTStore[TT]  ttstore;
    private final BBClientUtil  bb;
    private final TTTabulation[TT,SUP,TELLS]  ttt;
    private final TTTabulationPET[TT,SUP,TELLS]  tttpet;
    private final TTTabulationUtil[TT,SUP,TELLS]  tttUtil;
    private final PrintStream[{*->*}]  debugLog;
    
    TTTabulationMixes(PrivateKey{TT<-TT} tellerPrivKey,
                 TTStore[TT]  ttstore,
                 BBClientUtil  bb,
                 TTTabulation[TT,SUP,TELLS]  ttt,
                 TTTabulationPET[TT,SUP,TELLS]  tttpet,
                 PrintStream[{*->*}]  debugLog) {
          this.tellerPrivKey = tellerPrivKey;
          this.ttstore = ttstore;
          this.bb = bb;
          this.ttt = ttt;
          this.tttpet = tttpet;
          this.tttUtil =  new TTTabulationUtil[TT,SUP,TELLS](tellerPrivKey, ttstore, bb);
          this.debugLog = debugLog;
      }

    /**
     * Checks whether the votes/electoral roll need to be mixed. Returns true if all the mixes have been performed.
     */
    boolean checkAndMix{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            PETCache{TT<-SUP} voteVoteCache,
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, TellerDetails{TT<-SUP} tellerDetails, 
            int{TT<-SUP} tellerIndex, boolean{TT<-SUP} isVoteMix, boolean{} notifyNext, int{TT<-SUP} numberBlocks, final ConditionHolder[{TT<-TT}]{} allMixesDone) 
    throws (IOException{}, IllegalArgumentException{}, CryptoException{}) 
     
        if (electionDetails == null || ttt == null) return false;
        int num = tellerMixNumber(electionDetails.electionID);
        if (num <= 0) return false;
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        boolean allMixblocksDone = true;
        boolean performedMix = false;
        // check and perform the mix for each block.
        for (int block = 0; block < numberBlocks; block++) {                
            IOException ioex = null;
            CryptoException cex = null;
            IllegalArgumentException iaex = null;
            try {
                int mixesPerformed = mixesPerformed(electionCache, electionDetails, isVoteMix, block, tellerDetails); 

                if (num-1 == mixesPerformed) {
                    // need to do the mix! e.g., we are mix number 3, and 2 mixes have been performed so far.

                    if (debugLog != null) debugLog.println("Performing " + (isVoteMix?"vote":"er") + " mix, block " + block);
                    if (mix(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, isVoteMix, block, num, allMixesDone)) {
                        // the mix was performed
                        mixesPerformed++;
                        performedMix = true;
                    }
                    else {
                        
                    }
                }
                allMixblocksDone = allMixblocksDone && (mixesPerformed == numTabTellers);
            }
            catch (IOException e) {
                ioex = e;
            }
            catch (CryptoException e) {
                cex = e;
            }
            catch (IllegalArgumentException e) {
                iaex = e;
            }
            IOException eexio = endorse(ioex, {TT<-SUP});
            IllegalArgumentException eexia = endorse(iaex, {TT<-SUP});
            CryptoException eexc = endorse(cex, {TT<-SUP});

            if (eexio != null) throw eexio;
            if (eexia != null) throw eexia;
            if (eexc != null) throw eexc;
        }
        

        if (notifyNext && performedMix) {
            TTClientUtil ttcu = new TTClientUtil();
            if (numTabTellers > 0 && num < numTabTellers) {
                // there is a next teller to inform
                // make a best effort to inform them
                if (debugLog != null) debugLog.println("Notifying next teller for " + (isVoteMix?"vote":"er") + " mix");
                try {
                    Host t = tellerDetails.tabulationTellers[num];
                    // t is the next teller
                    ttcu.tabulate(t, electionDetails.electionID);
                }
                catch (NullPointerException giveup) { }
                catch (ArrayIndexOutOfBoundsException giveup) { }
            }
            else if (num == numTabTellers) {
                // we are the last teller to perform the mix. Notify all other tellers
                if (debugLog != null) debugLog.println("Notifying all tellers for " + (isVoteMix?"vote":"er") + " mix");
                for (int i = 0; i < num - 1; i++) {
                    try {
                        Host t = tellerDetails.tabulationTellers[i];
                        ttcu.tabulate(t, electionDetails.electionID);
                    }
                    catch (NullPointerException giveup) { }
                    catch (ArrayIndexOutOfBoundsException giveup) { }
                }
            }
        }
        // return true if each tabulation teller has performed a mix
        return allMixblocksDone; 
    }
    
    /**
     * Perform a mix on the votes or the electoral roll. Return whether a mix was performed. 
     * Precondition is that if mixNumber is greater than 1, then all mixes up to but not 
     * including mixNumber have been performed.
     */
    private boolean mix{}(final ElectionDetails{} electionDetails_, 
            PETCache{} voteVoteCache_,
            ElectionCache{} electionCache_,
            ElGamalPublicKey{} tabTellerSharedKey_, TellerDetails{} tellerDetails_, int{} tellerIndex_, boolean{} isVoteMix_, int{} block_, int{} mixNumber_, final ConditionHolder[{TT<-TT}]{} allMixesDone_):{}
    throws IOException, IllegalArgumentException, CryptoException 
      // endorse to SUP even though it depends on the actions of other tellers. Maybe should have a principal representing the other tellers?
        final ElectionDetails electionDetails = endorse(electionDetails_, {TT<-SUP});
        ElectionCache electionCache = endorse(electionCache_, {TT<-SUP});
        TellerDetails tellerDetails = endorse(tellerDetails_, {TT<-SUP});
        boolean isVoteMix = endorse(isVoteMix_, {TT<-SUP});
        int block = endorse(block_, {TT<-SUP});
        int mixNumber = endorse(mixNumber_, {TT<-SUP});
        int tellerIndex = endorse(tellerIndex_, {TT<-SUP});
        final ConditionHolder[{TT<-TT}] allMixesDone = endorse(allMixesDone_, {TT<-SUP});
        PETCache voteVoteCache = endorse(voteVoteCache_, {TT<-SUP});
        
        if (electionDetails == null || bb == null || ttstore == null || ttt == null || ttt.tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;
        
        CryptoFactory cryptoFactory = CryptoUtil.factory();
        if (cryptoFactory == null) return false;
        
        Mix prevMix = null;
        IOException exio = null;
        IllegalArgumentException exia = null;
        CryptoException exc = null;

        ElGamalPublicKey tabTellerSharedKey = endorse(tabTellerSharedKey_, {TT<-SUP});
        IOException eexio = endorse(exio, {TT<-SUP});
        if (eexio != null) throw eexio;
        if (tabTellerSharedKey == null) return false;

        
        try {
            if (SUP equiv electionDetails.supervisor) {
                if (mixNumber == 1) {
                    // this is the first mix. 
                    prevMix = retrieveInitialMix(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, isVoteMix, block, tellerDetails, tellerIndex);
                }
                else {
                    // get the results of mix n-1
                    try {
                        prevMix = ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, isVoteMix, block, mixNumber-1, true);
                    }
                    catch (IOException e) {
                        exio = e;
                    }                        
                    eexio = endorse(exio, {TT<-SUP});
                    if (eexio != null) throw eexio;
                }
                int initialMixSize = retrieveInitialMixSize(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, isVoteMix, block, tellerDetails, tellerIndex); 

                if (prevMix == null) {
                    throw new IOException("Cannot retrieve results of mix " + (mixNumber-1));
                }
                if (initialMixSize < 0) {
                    throw new IOException("Cannot retrieve results of initial mix");
                }

                if (prevMix.size() != initialMixSize) {
                    // the previous mix is bad! Abandon the election.
                    ttt.tttUtil.abandonElection(electionID, "Previous mix is not the same size as " +
                            "the initial mix. isVoteMix==" + isVoteMix + ", block==" + block +". Initial mix is size " + initialMixSize + 
                            " and previous mix is size " + prevMix.size(), mixNumber);
                }
            }
        }
        catch (IOException e) {
            exio = e;
        }
        catch (IllegalArgumentException e) {
            exia = e;
        }
        catch (CryptoException e) {
            exc = e;
        }
         
        eexio = endorse(exio, {TT<-SUP});
        IllegalArgumentException eexia = endorse(exia, {TT<-SUP});
        CryptoException eexc = endorse(exc, {TT<-SUP});

        if (eexio != null) throw eexio;
        if (eexia != null) throw eexia;
        if (eexc != null) throw eexc;
        
        // pc is {}, since the abandonElection will have exited the method.        
        MixInfo[TT] leftMixInfo = null; 
        MixInfo[TT] rightMixInfo = null;
        
        // check if we've already decided on permutations for these mixes.
        if (isVoteMix) {
            leftMixInfo = ttstore.retrieveVoteMixInfo(SUP, electionID, block, false);
            rightMixInfo = ttstore.retrieveVoteMixInfo(SUP, electionID, block, true);
        }
        else {
            leftMixInfo = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, false);
            rightMixInfo = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, true);
        }        
        
        boolean mustStore = (leftMixInfo == null || rightMixInfo == null);
        if (mustStore) {
            leftMixInfo = generateMixInfo(prevMix, tabTellerSharedKey.getParams(), isVoteMix, block, false, electionDetails.nonceLength, allMixesDone);
            rightMixInfo = generateMixInfo(prevMix, tabTellerSharedKey.getParams(), isVoteMix, block, true, electionDetails.nonceLength, allMixesDone);
        }
        if (leftMixInfo == null || rightMixInfo == null) return false;
        
        // mix
        Mix midMix; 
        Mix newMix;
        if (isVoteMix) {
            midMix = new VoteMix(mixNumber);
//            endorse({TT<-TT}) {
//                newMix = new VoteMix(mixNumber, cryptoFactory.messageDigest(new label {TT->TT;TT<-TT}, new label {TT<-TT;TT<-SUP}, rightMixInfo.mixNonce, true));
//            }
        }
        else {
            midMix = new CapabilityMix(mixNumber);
//            endorse({TT<-TT}) {
//                newMix = new CapabilityMix(mixNumber, cryptoFactory.messageDigest(new label {TT->TT;TT<-TT}, new label {TT<-TT;TT<-SUP}, rightMixInfo.mixNonce, true));
//            }
        }
        newMix = createNewMix(isVoteMix, block, mixNumber, rightMixInfo.mixNonce);
        if (newMix == null) return false; // impossible

        leftMixInfo.permute(prevMix, midMix, tabTellerSharedKey);
        rightMixInfo.permute(midMix, newMix, tabTellerSharedKey);

        if (mustStore) {
            if (isVoteMix) {
                VoteMixInfo[TT] lvmi = null;
                VoteMixInfo[TT] rvmi = null;
                try {
                    lvmi = (VoteMixInfo[TT])leftMixInfo;
                    rvmi = (VoteMixInfo[TT])rightMixInfo;
                }
                catch (ClassCastException imposs) { }

                storeVoteMixInfo(electionID, block, lvmi, rvmi);
            }
            else {
                ElectoralRollMixInfo[TT] lermi = null;
                ElectoralRollMixInfo[TT] rermi = null;
                try {
                    lermi = (ElectoralRollMixInfo[TT])leftMixInfo;
                    rermi = (ElectoralRollMixInfo[TT])rightMixInfo;
                }
                catch (ClassCastException imposs) { }

                storeElectoralRollMixInfo(electionID, block, lermi, rermi);
            }
        }
        
        // output the results
        String leftMeta = isVoteMix?VoteMix.meta(electionDetails, block, mixNumber, false):CapabilityMix.meta(electionDetails, block, mixNumber, false);
        String rightMeta = isVoteMix?VoteMix.meta(electionDetails, block, mixNumber, true):CapabilityMix.meta(electionDetails, block, mixNumber, true);
        
        bb.post(leftMeta, midMix, tellerPrivKey);
        bb.post(rightMeta, newMix, tellerPrivKey);

        // broadcast a hint to all other tellers
        ttt.tttUtil.broadcast(electionID, tellerDetails, mixNumber);
        
        return true;
    }
    private void storeVoteMixInfo{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block, VoteMixInfo[TT]{TT<-SUP} leftMixInfo, VoteMixInfo[TT]{TT<-SUP} rightMixInfo) 
     
        if (ttstore == null) return;
        ttstore.storeVoteMixInfo(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}), endorse(leftMixInfo, {TT<-TT}), endorse(rightMixInfo, {TT<-TT}));        
    }
    private void storeElectoralRollMixInfo{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block, ElectoralRollMixInfo[TT]{TT<-SUP} leftMixInfo, ElectoralRollMixInfo[TT]{TT<-SUP} rightMixInfo) 
     
        if (ttstore == null) return;
        ttstore.storeElectoralRollMixInfo(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}), endorse(leftMixInfo, {TT<-TT}), endorse(rightMixInfo, {TT<-TT}));        
    }
    private Mix{TT<-SUP} createNewMix{TT<-SUP}(boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block, int{TT<-SUP} mixNumber, byte{TT->TT;TT<-TT} []{TT<-SUP} mixNonce) {
        try {
            if (isVoteMix) {
                return new VoteMix(mixNumber, CryptoUtil.factory().messageDigest(new label {TT->TT;TT<-TT}, new label {TT<-TT;TT<-SUP}, mixNonce, true));
            }
            else {
                return new CapabilityMix(mixNumber, CryptoUtil.factory().messageDigest(new label {TT->TT;TT<-TT}, new label {TT<-TT;TT<-SUP}, mixNonce, true));
            }
        }
        catch (NullPointerException imposs) { }
        return null;
    }
    
    private MixInfo[TT]{TT<-TT} generateMixInfo{}(Mix{} prevMix_, ElGamalParameters{} params_, boolean{} isVoteMix_, int{} block_, boolean{} isRightMix_, int{} nonceLength_, final ConditionHolder[{TT<-TT}]{} allMixesDone_) 
      
        Mix prevMix = endorse(prevMix_, {TT<-TT});
        ElGamalParameters params = endorse(params_, {TT<-TT});
        boolean isVoteMix = endorse(isVoteMix_, {TT<-TT});
        int block = endorse(block_, {TT<-TT});
        boolean isRightMix = endorse(isRightMix_, {TT<-TT});
        int nonceLength = endorse(nonceLength_, {TT<-TT});
        final ConditionHolder[{TT<-TT}] allMixesDone = endorse(allMixesDone_, {TT<-TT});

        if (prevMix == null || params == null) return null;
        int{TT<-TT;TT->TT} [] perm = null;
        int size = endorse(prevMix.size(), {TT<-TT});
        try {
            perm = CryptoUtil.factory().createPermutation(new label {TT<-TT;TT->TT}, size);
        }
        catch (NullPointerException imposs) { }

        // create the factors
        size = size < 0 ? 0 : size;
        ElGamalReencryptFactor{TT<-TT;TT->TT}[] factors = new ElGamalReencryptFactor[size];
        for (int i = 0; i < size; i++) {
            try {
                factors[i] = CryptoUtil.factory().generateElGamalReencryptFactor(params);
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { }
        }
        
        // pick a nonce if needed
        byte{TT<-TT;TT->TT allMixesDone.c->_ } [] nonce = null; 
        if (isRightMix) {
            try {
                nonce = CryptoUtil.factory().freshNonce(new label {TT<-TT;TT->TT allMixesDone.c->_}, nonceLength);
            }
            catch (NullPointerException imposs) { }
        }
        
        // create the nonces that are used to commit to the permutation
        byte{TT<-TT;TT->TT}[][] commitments = new byte[size][];
        for (int i = 0; i < commitments.length; i++) {
            try{
                commitments[i] = CryptoUtil.factory().freshNonce(new label {TT<-TT;TT->TT}, nonceLength).clone();
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
            catch (NullPointerException imposs) { }
        }
        
        if (isVoteMix) {
            // create the factors
            ElGamalReencryptFactor{TT<-TT;TT->TT}[] choiceFactors = new ElGamalReencryptFactor[size];
            for (int i = 0; i < size; i++) {
                try {
                    choiceFactors[i] = CryptoUtil.factory().generateElGamalReencryptFactor(params);
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                
                catch (NullPointerException imposs) { }
            }
            return new VoteMixInfo[TT](allMixesDone, perm, choiceFactors.clone(), factors.clone(), commitments.clone(), nonce);
        }
        else {
            return new ElectoralRollMixInfo[TT](allMixesDone, perm, factors.clone(), commitments.clone(), nonce);            
        }
    }
    
    int{TT<-SUP} retrieveInitialMixSize{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, PETCache{TT<-SUP} voteVoteCache, ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex):{TT<-SUP} throws IOException 
     
        int size = -1;
        if (electionCache != null) {
            if (isVoteMix) {
                size = electionCache.getInitialVoteMixSize(block);
            }
            else {
                size = electionCache.getInitialCapabilityMixSize(block);                
            }
        }
        try {
            if (endorse(size, {TT<-SUP}) < 0) {
                Mix m = retrieveInitialMix(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, isVoteMix, block, tellerDetails, tellerIndex);
                size = m == null ? -1 : m.size(); 
            }
        }
        catch (IllegalArgumentException e) {
        }
        catch (CryptoException e) {
        }
        catch (IOException e) {
        }
        return endorse(size, {TT<-SUP}); 
    }
    /**
     * Retrieve the initial vote mix from the bulletin board. That is, get the decryptions of the PETs of pairwise comparisons of
     * capabilities, and use them to filter the votes.
     */
    Mix{TT<-SUP} retrieveInitialMix{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, PETCache{TT<-SUP} voteVoteCache, ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex):{TT<-SUP} 
    throws IOException, IllegalArgumentException, CryptoException 
     
        if (ttt == null || tttUtil == null) {
            return null;
        }
        Mix initialMix = null;
        int{TT<-SUP} numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);

        int{TT<-SUP} retriesLeft = tttUtil.maxRetries(numTabTellers);        
        while (endorse(initialMix == null, {TT<-SUP}) && retriesLeft-- > 0) {
            CryptoException exc = null;
            IllegalArgumentException exia = null;
            IOException exio = null;
            
            if (isVoteMix) {
                try {
                    initialMix = retrieveInitialVoteMix(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, tellerDetails, tellerIndex, block);
                }
                catch (IOException e) { exio = e; }
                catch (IllegalArgumentException e) { exia = e; }
                catch (CryptoException e) { exc = e; }
            }
            else {
                try {
                    initialMix = retrieveInitialCapabilityMix(electionCache, electionDetails, tellerDetails, block);            
                }
                catch (CryptoException e) { exc = e; }
                catch (IOException e) { exio = e; }
                catch (IllegalArgumentException e) { exia = e; }
            }
            
            CryptoException eexc = endorse(exc, {TT<-SUP});
            IllegalArgumentException eexia = endorse(exia, {TT<-SUP});
            IOException eexio = endorse(exio, {TT<-SUP});
            if (eexc != null) throw eexc;
            if (eexia != null) throw eexia;
            if (eexio != null) throw eexio;
            
            if (retriesLeft > 0 && initialMix == null) {
                // sleep for a bit
                if (debugLog != null) debugLog.println("Sleeping for initial mix for block " + block + ". Retries left = " + retriesLeft);
                jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
            }
        }
        return endorse(initialMix, {TT<-SUP});
    }
    /**
     * Retrieve the initial vote mix from the bulletin board. That is, get the decryptions of the PETs of pairwise comparisons of
     * capabilities, and use them to filter the votes.
     */
    private VoteMix{TT<-SUP} retrieveInitialVoteMix{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            PETCache{TT<-SUP} voteVoteCache,
            ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, int{TT<-SUP} block):{} 
    throws IOException, IllegalArgumentException, CryptoException
             
        if (electionDetails == null || ttt == null || tttpet == null || ttstore == null) {
            return null;
        }
        ElectionID electionID = electionDetails.electionID;
        
        if (electionCache != null) {
            VoteMix vm = electionCache.getInitialVoteMix(block);
            if (vm != null) return vm;
        }
        
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        
        // retrieve the valid submitted votes.
        VerifiableVote{}[] vvs = ttt.retrieveValidVerifiableVotes(electionDetails, electionCache, tabTellerSharedKey, block);
        if (vvs == null) return null;
        
        boolean{TT<-SUP; TT<-TELLS}[] voteGood = new boolean[vvs.length];
        for (int i = 0; i < vvs.length; i++) { 
            try {
                voteGood[i] = true;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        
        
        // for each ballot, go through and get the pets for that ballot
        for (int ballotIndex = 0; ballotIndex < vvs.length - 1; ballotIndex++) {
            try {        
                if (!endorse(voteGood[ballotIndex], {TT<-SUP})) {
                    // we already know this ballot is no good
                    continue;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        
            // get all the ciphertexts

        
            ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] allCiphertexts = null;
            if (voteVoteCache != null) {
                allCiphertexts = voteVoteCache.getCiphertexts(TTTabulation[TT,SUP,TELLS].META_VOTES_TO_VOTES, block, ballotIndex, TT, SUP, TELLS);
            }
            if (allCiphertexts == null) {
                allCiphertexts = tttpet.decommitmentsToCiphertexts(voteVoteCache, 
                                                                   electionDetails, 
                                                                   tellerDetails, 
                                                                   TTTabulation[TT,SUP,TELLS].META_VOTES_TO_VOTES, 
                                                                   block,
                                                                   ballotIndex, 
                                                                   ttstore.retrieveVoteVotePETShares(SUP, electionID, block, ballotIndex),
                                                                   electionDetails.elGamalParameters);
            }
        
            // get all distributed decryption shares
            ElGamalMsg{TT<-SUP;TT<-TELLS}[] petDecrypts_ = null;
            IOException ioex = null;
            CryptoException cex = null;
            try {
                petDecrypts_ = ttt.retrieveTTDecrypts(voteVoteCache,
                                                      TTTabulation[TT,SUP,TELLS].META_VOTES_TO_VOTES, 
                                                      block,
                                                      ballotIndex, 
                                                      tabTellerSharedKey,
                                                      electionDetails, 
                                                      electionCache,
                                                      tellerDetails,
                                                      tellerIndex,
                                                      allCiphertexts);            
            }
            catch (IOException e) {
                ioex = e;
            }
            catch (CryptoException e) {
                cex = e;
            }
            IOException eioex = endorse(ioex, {TT<-SUP});
            CryptoException ecex = endorse(cex, {TT<-SUP});
            if (eioex != null) throw eioex;
            if (ecex != null) throw ecex;
            
            ElGamalMsg{TT<-SUP;TT<-TELLS}[] petDecrypts = endorse(petDecrypts_, {TT<-SUP});
            if (petDecrypts == null) return null;
        

            int numberOfPETs = vvs.length - 1 - ballotIndex;
            
            if (petDecrypts.length != numberOfPETs) {
                throw new IllegalArgumentException("Incorrect length for the PET Decrypts: expected " + numberOfPETs + " got " + petDecrypts.length );
            }
            
            for (int j = 0; j < numberOfPETs; j++) {
                // compare votes i and j
                try {
                    if (CryptoUtil.factory().petResult(petDecrypts[j])) {
                        // Ballots ballotIndex and (ballotIndex+1+j) use the same capability!
                        // TODO: We could implement different duplicate-capability policies.
                        // E.g., throw out both, throw out most recent, throw out least recent.
                        // Here we throw out both.
                        voteGood[ballotIndex] = false;
                        voteGood[ballotIndex+1+j] = false;
                    }
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }                
                catch (NullPointerException imposs) { }                
            }
        }

        // now go through and only take the good votes.
        VoteMix ret = new VoteMix(0);
        for (int i = 0; i < vvs.length; i++) {
            try {
                final VerifiableVote vv = vvs[i];
                if (voteGood[i] && vv != null && vv.encChoice != null) {
                    ret.addVote(new Vote(vv.context, vv.encChoice.getCiphertext(), vv.encCapability));
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        
        if (electionCache != null) {
            electionCache.setInitialVoteMix(block, ret);
        }


        return ret;

    }
    
    
    /**
     * Retrieve the initial capability mix from the bulletin board. That is, get the electoral roll, and get all of the encrypted capabilities.
     */
    private CapabilityMix{TT<-SUP} retrieveInitialCapabilityMix{}(ElectionCache{TT<-SUP} electionCache, ElectionDetails{TT<-SUP} electionDetails, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} block):{} 
    throws IOException, IllegalArgumentException, CryptoException {
        if (electionDetails == null || electionDetails.ballotDesign == null) {
            return null;
        }
        ElectionID electionID = electionDetails.electionID;

        if (electionCache != null) {
            CapabilityMix cm = electionCache.getInitialCapabilityMix(block);
            if (cm != null) return cm;
        }
        
        // get the electoral roll.
        ElectoralRollCapabilities roll = ElectionUtil.retrieveElectoralRollCapabilities(electionCache, electionDetails, tellerDetails, block);
        CapabilityMix mix = new CapabilityMix(0);
        if (roll == null) return mix;
        VoterEncCapabilities{}[] voters = roll.roll;
        if (voters == null) return mix;
        
        Set[{}] processedVoters = new HashSet[{}]();

        for (int i = 0; i < voters.length; i++) {
            try {
                VoterEncCapabilities vd = voters[i];
                if (vd == null) continue;
                if (!electionDetails.inBlock(vd, block)) {
                    // vd has no votes in this block.
                    continue;
                }
                ElGamalCiphertext{}[] ecs = vd.encCapabilities;
                if (ecs == null) continue;
                
                // have we already processed a VoterEncCapabilities for this voter?
                if (processedVoters.contains(vd.name)) continue;
                try {
                    processedVoters.add(vd.name);
                }
                catch (ClassCastException imposs) { }
                
                for (int j = 0; j < ecs.length; j++) {
                    try {
                        ElGamalCiphertext ec = ecs[j];
                        if (ec == null) continue;
                        // is ec in the block?
                        if (electionDetails.inBlock(vd, electionDetails.ballotDesign.nthContext(j), block)) {
                            mix.addEncCapability(ec);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        if (electionCache != null) {
            electionCache.setInitialCapabilityMix(block, mix);
        }

        return mix;
    }
    /**
     * Return which mix this teller is meant to perform, i.e., 
     * '1' does the first mix, followed by '2', etc.
     * -1 returned if this teller does not participate in the election. 
     */
    private int{TT<-SUP} tellerMixNumber{TT<-SUP}(ElectionID{TT<-SUP} electionID) {
        if (ttstore == null) return -1;
        return ttstore.retrieveIndex(SUP, electionID);
    }
    
    /**
     * What is last mix number that has been performed? -1 indicates an error condition,
     * 0 indicates that no mixes have been performed yet.
     */
    private int{TT<-TELLS} mixesPerformed{}(ElectionCache{TT<-SUP} electionCache, ElectionDetails{TT<-SUP} electionDetails, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block, TellerDetails{TT<-SUP} details):{} 
    throws IOException   
        if (electionDetails == null) return -1;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return -1;        
        if (details == null|| details.tabulationTellers == null) return -1;
        
        // go through the numbers, until we get a vote mix
        // that has not been completed.
        for (int i = 0; i < details.tabulationTellers.length; i++) {
            if (ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, details, isVoteMix, block, i+1, true) == null) return i;
        }
        // all are completed!
        return details.tabulationTellers.length;
    }
    
    boolean checkAllMixRevelations{TT<-SUP}(PETCache{TT<-SUP} voteVoteCache, final ElectionDetails{TT<-SUP} electionDetails,
            ElectionCache{TT<-SUP} electionCache,
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, int{TT<-SUP} numberBlocks):{TT<-SUP;TT<-TELLS} 
    
    throws IOException  
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;        

        IOException ioex = null;
        IOException eioex = null;
        
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);        
        for (int k = 0; k < 2; k++) {
            boolean isVoteMix = (k==0);
            for (int block = 0; block < numberBlocks; block++) {                
                Mix prevMix = null;
                String abandonmentReason = null;
                try {
                    prevMix = retrieveInitialMix(electionDetails, voteVoteCache, electionCache, tabTellerSharedKey, isVoteMix, block, tellerDetails, tellerIndex);
                }
                catch (IllegalArgumentException e) {
                    abandonmentReason = e.getMessage();
                }
                catch (CryptoException e) {
                    abandonmentReason = e.getMessage();
                }

                if (abandonmentReason != null) {
                    String reason =  "Could not verify";
                    reason += isVoteMix?" vote":" electoral roll";
                    reason += " mix revelations. "; 
                    reason += abandonmentReason;
                    try {
                        tttUtil.abandonElection(electionID, reason, tellerIndex); 
                    }
                    catch (IOException e) {
                        ioex = e;
                    }
                    eioex = endorse(ioex, {TT<-SUP});
                    if (eioex != null) throw eioex;
                    return false; // UNREACHABLE, abandonElection throws an exception.
                }

                for (int ti = 1; ti <= numTabTellers && prevMix != null; ti++) {
                    if (!ttstore.retrieveTellerMixOK(SUP, electionID, ti, isVoteMix, block)) {
                        if (ti == tellerIndex) {
                            // no need to check our own mix
                            tttUtil.storeTellerMixOK(electionID, ti, isVoteMix, block);                            
                        }
                        // check that ti's mix is ok.
                        // retrieve ti's mix
                        Mix leftMix_ = null;
                        try {
                            leftMix_ = ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, isVoteMix, block, ti, false);  
                        }
                        catch (IOException e) {
                            ioex = e;
                        }
                        eioex = endorse(ioex, {TT<-SUP});
                        if (eioex != null) throw eioex;

                        Mix rightMix_ = null;
                        try {
                            rightMix_ = ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, isVoteMix, block, ti, true);  
                        }
                        catch (IOException e) {
                            ioex = e;
                        }
                        eioex = endorse(ioex, {TT<-SUP});
                        if (eioex != null) throw eioex;

                        Mix leftMix = endorse(leftMix_, {TT<-SUP});
                        Mix rightMix = endorse(rightMix_, {TT<-SUP});

                        // retrieve revelation
                        MixRevelation revelation = null;

                        int retriesLeft = tttUtil.maxRetries(numTabTellers);
                        while (revelation == null && retriesLeft-- > 0) {
                            MixRevelation revelation_ = null;
                            try {
                                revelation_ = retrieveTTMixRevelation(electionDetails, tellerDetails, ti, isVoteMix, block);
                            }
                            catch (IOException e) {
                                ioex = e;
                            }
                            eioex = endorse(ioex, {TT<-SUP});
                            if (eioex != null) throw eioex;

                            revelation = endorse(revelation_, {TT<-SUP});

                            if (retriesLeft > 0 && revelation == null) {
                                // sleep for a bit
                                if (debugLog != null) debugLog.println("Sleeping for mix revelation for block " + block + ". Retries left = " + retriesLeft);
                                jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                            }
                        }
                        if (revelation == null) {
                            // revelation not available yet
                            return false;
                        }

                        if (prevMix == null) {
                            // give up.
                            String reason =  "Could not verify";
                            reason += isVoteMix?"vote":"electoral roll";
                            reason += " mix revelations.";
                            reason += " Could not find suitable previous mix.";
                            try {
                                tttUtil.abandonElection(electionID, reason, tellerIndex); 
                            }
                            catch (IOException e) {
                                ioex = e;
                            }
                            eioex = endorse(ioex, {TT<-SUP});
                            if (eioex != null) throw eioex;
                            return false; // UNREACHABLE, abandonElection throws an exception.
                        }    

                        // check revelation against mix
                        boolean{}[] revDirections_ = null;
                        try {
                            revDirections_ = retrieveRevelationDirections(electionDetails, electionCache, tellerDetails, ti, isVoteMix, block, prevMix.size());
                        }
                        catch (IOException e) {
                            ioex = e;
                        }
                        eioex = endorse(ioex, {TT<-SUP});
                        if (eioex != null) throw eioex;
                        boolean{}[] revDirections = endorse(revDirections_, {TT<-SUP});

                        boolean verifiedMix = endorse(revelation.verify(tabTellerSharedKey, prevMix, leftMix, rightMix, revDirections), {TT<-SUP});
                        if (verifiedMix) {
                            // the mix is ok.
                            tttUtil.storeTellerMixOK(electionID, ti, isVoteMix, block);
                        }
                        else {
                            // the revelation does not agree with the mix posted
                            // abandon the election
                            String reason =  "Teller " + ti;
                            reason += isVoteMix?"vote":"electoral roll";
                            reason += " mix revelation does not pass verification.";
                            try {
                                tttUtil.abandonElection(electionID, reason, tellerIndex); 
                            }
                            catch (IOException e) {
                                ioex = e;
                            }
                            eioex = endorse(ioex, {TT<-SUP});
                            if (eioex != null) throw eioex;
                            return false; // UNREACHABLE, abandonElection throws an exception.
                        }
                        prevMix = rightMix;
                    }
                }
            }
        }        
        return true;
    }
        
    /**
     * Post the revelation of the mix hash
     */
    boolean postRevealMixHash{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block) 
    throws (IOException{}) 
     
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;        

        // get the mix info
        MixInfo[TT] rightMixInfo = null;
        if (isVoteMix) {
            rightMixInfo = ttstore.retrieveVoteMixInfo(SUP, electionID, block, true);
        }
        else {
            rightMixInfo = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, true);            
        }
        
        if (rightMixInfo == null) {
            // This is very bad, since the right mix info 
            // should always be available at this point. Could do something
            // more drastic, like abandon the election.
            return false; 
        }
        
        // declassify the nonces
        byte{} [] nonce = null;
        
        try {
            nonce = declassifyMixHashRevelation(rightMixInfo); 
        }
        catch (UnsatisfiedConditionException e) {
            // couldn't declassify the mix hash revelation
            return false;
        }

        // post the hash revelation        
        MixHashRevelation revelation = new MixHashRevelation(tellerIndex, nonce);
        try {
            bb.post(MixHashRevelation.meta(electionDetails, isVoteMix, block, tellerIndex), revelation, tellerPrivKey);                
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    private byte{} []{} declassifyMixHashRevelation{}(MixInfo[TT]{} mixInfo_):{}
    throws UnsatisfiedConditionException
     
        final MixInfo[TT] mixInfo = endorse(mixInfo_, {TT<-TT});
        if (mixInfo == null) return null;
        byte{TT->TT mixInfo.allMixesDone.c->_;TT<-TT} [] mixNonce = mixInfo.mixNonce;
        if (mixNonce == null || mixInfo.allMixesDone == null) return null;
        
        byte{}[] n = new byte[mixNonce.length];
        for (int i = 0; i < n.length; i++) {
            byte{TT->TT mixInfo.allMixesDone.c->_;TT<-TT} b = 0;
            try {
                b = mixNonce[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            try {
                n[i] = declassify(b, {TT<-TT;TT->TT mixInfo.allMixesDone.c->_} to {} using mixInfo.allMixesDone.c); // declassification due to WHEN
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        return n.clone();
    }
    
    /**
     * Post the revelation of the mix info
     */
    boolean postRevealMix{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache,
            TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block) 
    throws (IOException{}) 
     
        if (electionDetails == null || bb == null || ttstore == null || tttUtil == null) return false;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return false;        
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        
        // wait until all mix hash revelations are revealed
        for (int i = 0; i < numTabTellers; i++) {
            int retriesLeft = tttUtil.maxRetries(numTabTellers);
            Object tiHashRevelation = null;
            IOException ioex = null;
            IOException eioex = null;
            while (eioex == null && endorse(tiHashRevelation == null && retriesLeft-- > 0, {TT<-SUP})) {
                try {
                    tiHashRevelation = retrieveTTMixHashRevelation(electionCache, electionDetails, tellerDetails, i+1, isVoteMix, block);
                }
                catch (IOException e) { ioex = e; }
                eioex = endorse(ioex, {TT<-SUP});

                if (eioex == null && retriesLeft > 0 && tiHashRevelation == null) {
                    // sleep for a bit
                    if (debugLog != null) debugLog.println("Sleeping for mix hash revelation for block " + block + ". Retries left = " + retriesLeft);
                    jif.runtime.Runtime[null].sleep(tttUtil.sleepTime(numTabTellers));
                }
            }

            if (eioex != null) throw eioex;
            if (endorse(retriesLeft < 0 || tiHashRevelation == null, {TT<-SUP})) return false;
        }
        
        // get the mix info
        MixInfo[TT] leftMixInfo = null;
        MixInfo[TT] rightMixInfo = null;
        if (isVoteMix) {
            leftMixInfo = ttstore.retrieveVoteMixInfo(SUP, electionID, block, false);
            rightMixInfo = ttstore.retrieveVoteMixInfo(SUP, electionID, block, true);
        }
        else {
            leftMixInfo = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, false);
            rightMixInfo = ttstore.retrieveElectoralRollMixInfo(SUP, electionID, block, true);            
        }
        
        if (leftMixInfo == null || rightMixInfo == null) {
            // This is very bad, since the mix info 
            // should always be available at this point. Could do something
            // more drastic, like abandon the election.
            return false; 
        }
        
        // get the revelation directions. 
        boolean{}[] revelationDirections = retrieveRevelationDirections(electionDetails, electionCache, tellerDetails, tellerIndex, isVoteMix, block, leftMixInfo.length());
        if (revelationDirections == null) return false;

        MixElementRevelation{}[] revelations = new MixElementRevelation[revelationDirections.length]; 
        constructMixRevelations(revelations, revelationDirections, leftMixInfo, rightMixInfo);
    
        // post the revelation
        MixRevelation revelation = new MixRevelation(tellerIndex, revelationDirections, revelations);
        try {
            bb.post(MixRevelation.meta(electionDetails, isVoteMix, block, tellerIndex), revelation, tellerPrivKey);                
            // broadcast a hint to all other tellers
            tttUtil.broadcast(electionID, tellerDetails, tellerIndex);
            return true;
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    private void constructMixRevelations{}(MixElementRevelation{}[]{} revelations_, 
                                         boolean{}[]{} revelationDirections_,
                                         MixInfo[TT]{} leftMixInfo_,
                                         MixInfo[TT]{} rightMixInfo_)  
        MixElementRevelation{}[] revelations = endorse(revelations_, {TT<-TT});
        boolean{}[] revelationDirections = endorse(revelationDirections_, {TT<-TT});
        MixInfo[TT] leftMixInfo = endorse(leftMixInfo_, {TT<-TT});
        MixInfo[TT] rightMixInfo = endorse(rightMixInfo_, {TT<-TT});
        if (revelations == null || revelationDirections==null || leftMixInfo == null || rightMixInfo == null) return;
        for (int i = 0; i < revelations.length; i++) {
            try {
                boolean d = endorse(revelationDirections[i], {TT<-TT}); 
                if (d) {
                    // take from the right
                    revelations[i] = rightMixInfo.getRevelation(i, false);
                }
                else {
                    // take from the left
                    revelations[i] = leftMixInfo.getRevelation(i, true);
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
        }        
    }

    /**
     * Retrieve the revelation directions that the teller tellerIndex is meant to follow, i.e., 
     * returns the boolean array that indicates whether the teller should reveal the left or the right
     * mapping for the mix.
     */
    private boolean{}[] retrieveRevelationDirections{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
                          ElectionCache{TT<-SUP} electionCache,
                          TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, 
                          boolean{} isVoteMix, int{} block, int{} length) 
    throws (IOException{}) 
     
        if (electionDetails == null || bb == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;
        
        // get the mix hash revelation for every teller
        MessageDigest[{}]{TT<-SUP} md = null; 
        try {
            md = CryptoUtil.factory().messageDigest(new label {});
        }
        catch (NullPointerException imposs) { }
        
        if (md == null) {
            throw new IOException("Unable to obtain a message digest.");
        }
        
        IOException ioe = null; 
        IOException eioe = null; 
        int numTabTellers = TTTabulationUtil[TT,SUP,TELLS].numTabTellers(tellerDetails);
        for (int i = 0; i < numTabTellers; i++) {
            int ti = i+1;
            MixHashRevelation mhr_ = null; 
            try {
                mhr_ = retrieveTTMixHashRevelation(electionCache, electionDetails, tellerDetails, ti, isVoteMix, block);
            }
            catch (IOException e) {
                ioe = e;
            }
            eioe = endorse(ioe, {TT<-SUP}); 
            MixHashRevelation mhr = endorse(mhr_, {TT<-SUP});
            if (eioe != null) throw eioe;
            if (mhr == null) {
                // Should be impossible, as this method should only be called after
                // we have confirmed that all mix hash revelations have been posted.
                throw new IOException("Missing mix hash revelation for teller " + ti);
            }
            byte{} [] tiNonce = null;
            try {
                tiNonce = mhr.mixNonce;
            }
            catch (NullPointerException imposs) { }
            
            // now get the hash
            Mix rightMix = null;
            try {
                rightMix = ElectionUtil.retrieveMix(TT, TELLS, electionCache, electionDetails, tellerDetails, isVoteMix, block, ti, true);
            }
            catch (IOException e) { 
                ioe = e;
            }
            eioe = endorse(ioe, {TT<-SUP}); 
            if (eioe != null) throw eioe;
            
            if (rightMix == null) {
                ioe = new IOException("Missing mix for teller " + ti);
            }
            eioe = endorse(ioe, {TT<-SUP}); 
            if (eioe != null) throw eioe;
            
            try {
                byte{} [] tiNonceHash = rightMix.mixNonceHash;
                try {
                    if (!Util.equals(new label {}, tiNonceHash, CryptoUtil.factory().messageDigest(new label {}, tiNonce, true), true)) {
                        // abandon election
                        String mixType = isVoteMix?" vote":" electoral roll";
                        tttUtil.abandonElection(electionID,
                                        "Teller " + ti + " hash of " + mixType + " mix nonce does not agree with commitment.", 
                                        tellerIndex);

                    }
                }
                catch (IOException e) { 
                    ioe = e;
                }
            }
            catch (NullPointerException imposs) { }

            eioe = endorse(ioe, {TT<-SUP}); 
            if (eioe != null) throw eioe;
            
            // combine the nonce with the others
            md.update(tiNonce, true);
        }
                                                
        try {
            md.update(tellerIndex);

            byte{} [] Qi = md.digest();

            // now use reveal(Qi, j) to get the array of revelation directions
            boolean{}[] revDirections = new boolean[length<0?0:length];
            for (int j = 0; j < length; j++) {
                try {
                    revDirections[j] = revDirection(Qi, j);
                }
                catch (NullPointerException imposs) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            return revDirections;
        }
        catch (NullPointerException imposs) { }
        return null;
    }
    
    private boolean{_:_;Qi;j} revDirection{}(byte{}  [] Qi, int j) {
        if (Qi == null) return false;
        try {
            byte b = Qi[j/8];
            return (b&(1<<(j%8))) != 0;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false; // pad with zeros...
        }
    }
    
    boolean{} hasTTMixHashRevelation{}(ElectionCache{TT<-SUP} electionCache, 
            ElectionDetails{TT<-SUP} electionDetails, 
            TellerDetails{TT<-SUP} tellerDetails, 
            int{TT<-SUP} tellerIndex, 
            boolean{} isVoteMix, 
            int{} block) 
    throws (IOException{}) {
        // go to the bulletin board, and try to retreive the appropriate mix hash revelation
        if (electionCache != null && electionCache.hasTTMixHashRevelation(tellerIndex, isVoteMix, block)) {
            return true;
        }
        return retrieveTTMixHashRevelation(electionCache, electionDetails, tellerDetails, tellerIndex, isVoteMix, block) != null;
    }
    /**
     * Retrieve a teller's mix hash revelation
     */
    MixHashRevelation{} retrieveTTMixHashRevelation{}(ElectionCache{} electionCache, 
            ElectionDetails{} electionDetails, 
            TellerDetails{} tellerDetails, 
            int{} tellerIndex, 
            boolean{} isVoteMix, 
            int{} block) 
    throws (IOException{}) {
        // go to the bulletin board, and try to retreive the appropriate mix hash revelation
        if (electionCache != null) {
            MixHashRevelation mhr = electionCache.getTTMixHashRevelation(tellerIndex, isVoteMix, block);
            if (mhr != null) return mhr;
        }
        try {
            if (electionDetails == null) return null;
            ElectionID electionID = electionDetails.electionID;
            if (electionID == null || bb == null) return null;

            List[{}] results = bb.retrieveParams(XMLDeserializers.MixHashRevelation(), MixHashRevelation.meta(electionDetails, isVoteMix, block, tellerIndex), null, null);
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
                    MixHashRevelation c = (MixHashRevelation)post.msg;
                    // check the signature of c
                    if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
                        if (electionCache != null) {
                            electionCache.setTTMixHashRevelation(tellerIndex, isVoteMix, block, c);
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

    /**
     * Retrieve a teller's mix revelation
     */
    MixRevelation{} retrieveTTMixRevelation{}(final ElectionDetails{TT<-SUP} electionDetails, TellerDetails{TT<-SUP} tellerDetails, int{TT<-SUP} tellerIndex, boolean{} isVoteMix, int{} block) 
    throws (IOException{}) {
        // go to the bulletin board, and try to retreive the appropriate mix revelation
        try {
            if (electionDetails == null || bb == null) return null;

            List[{}] results = bb.retrieveParams(XMLDeserializers.MixRevelation(), MixRevelation.meta(electionDetails, isVoteMix, block, tellerIndex), null, null);
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
                    MixRevelation c = (MixRevelation)post.msg;
                    // check the signature of c
                    if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
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