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

class TTTabulationUtil[principal TT, principal SUP, principal TELLS] {
    private final PrivateKey{TT<-TT} tellerPrivKey;
    private final TTStore[TT]  ttstore;
    private final BBClientUtil  bb;
    
    TTTabulationUtil(PrivateKey{TT<-TT} tellerPrivKey, TTStore[TT]  ttstore, BBClientUtil  bb) {
        this.tellerPrivKey = tellerPrivKey;
        this.ttstore = ttstore;
        this.bb = bb;
    }

    /**
     * How many times should a tab teller retry retrieving information that it needs before giving up?
     */
    static final int maxRetries(int numTabTellers) {
        // number should be a multiple of numTabTellers to allow for each
        // tab teller to perform a round of computation
        return 60 * 100 * numTabTellers; // 100 hours times the number of tab tellers
    }
    
    /**
     * How many seconds should a tab teller wait between retries of
     * retrieving information that it needs? 
     */
    static final int sleepTime(int numTabTellers) {
        return 60;
    }
    
    static int numTabTellers(TellerDetails tellerDetails) {
        if (tellerDetails != null && tellerDetails.tabulationTellers != null) {
            return tellerDetails.tabulationTellers.length;
        }
        return 0;
    }
    
    /**
     * broadcast to other tabulation tellers
     */ 
    static void broadcast{}(final ElectionDetails{} electionDetails, ElectionCache{} electionCache, int{} tellerIndex) {
        if (electionDetails == null) return;
        try {
            TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
            ElectionID electionID = electionDetails.electionID;
            final principal p = (Principal)null;
            TTTabulationUtil[p,p,p].broadcast(electionID, tellerDetails, tellerIndex);
        }
        catch (IOException ignore) { }
    }
    /**
     * broadcast to other tabulation tellers
     */ 
    static void broadcast{}(ElectionID{} electionID, TellerDetails{} tellerDetails, int{} tellerIndex) {
        if (tellerDetails == null || tellerDetails.tabulationTellers == null) return;        
        TTClientUtil ttcu = new TTClientUtil();
        for (int i = 0; i < tellerDetails.tabulationTellers.length; i++) {
            if (i+1 != tellerIndex) {
                try {
                    Host h = tellerDetails.tabulationTellers[i];
                    ttcu.tabulate(h, electionID);
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                catch (IOException ignore) { }
                catch (IllegalArgumentException ignore) { }
            }
        }
    }

    ElectionCache{TT<-TT} electionCache{}(ElectionID{} id_, int{} tellerIndex_) 
     
        ElectionID id = endorse(id_, {TT<-TT});
        int tellerIndex = endorse(tellerIndex_, {TT<-TT});
        if (ttstore == null || id == null) return null;

        return ttstore.electionCache(id, tellerIndex);
    }
    
    void clearVoteVotePETShares{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
     
        if (ttstore == null) return;
        ttstore.clearVoteVotePETShares(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}), endorse(ballotIndex, {TT<-TT}));        
    }
    void clearRollVotePETShares{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block, int{TT<-SUP} ballotIndex) 
     
        if (ttstore == null) return;
        ttstore.clearRollVotePETShares(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}), endorse(ballotIndex, {TT<-TT}));        
    }
    void clearVoteMixInfo{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block) 
     
        if (ttstore == null) return;
        ttstore.clearVoteMixInfo(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}));        
    }
    void clearElectoralRollMixInfo{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} block) 
     
        if (ttstore == null) return;
        ttstore.clearElectoralRollMixInfo(endorse(electionID, {TT<-TT}), endorse(block, {TT<-TT}));        
    }
    
    public void storeTellerMixOK{TT<-SUP}(ElectionID{TT<-SUP} electionID, int{TT<-SUP} tellerIndex, boolean{TT<-SUP} isVoteMix, int{TT<-SUP} block) 
     
        if (ttstore == null) return;
        ttstore.storeTellerMixOK(endorse(electionID, {TT<-TT}), endorse(tellerIndex, {TT<-TT}), endorse(isVoteMix, {TT<-TT}), endorse(block, {TT<-TT}));        
    }

    /**
     * Convert an array of votes into an array of ciphertexts.
     */
    ElGamalCiphertext{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} votesToCiphertexts{TT<-SUP;TT<-TELLS}(Vote{TT<-SUP;TT<-TELLS}[]{TT<-SUP;TT<-TELLS} allVotes) {
        if (allVotes == null) return null;
        
        ElGamalCiphertext{TT<-SUP;TT<-TELLS}[] ret = new ElGamalCiphertext[allVotes.length];
        for (int i = 0; i < allVotes.length; i++) {
            try {
                Vote v = allVotes[i]; 
                if (v != null) {
                    ret[i] = v.encChoice;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
        }
        return ret;
    }

    void abandonElection{}(ElectionID{} electionID, String{} abandonElectionReason, int{} tellerIndex):{} 
    throws IOException  
        abandonElection(tellerPrivKey, ttstore, bb, 
                        endorse(electionID, {TT<-SUP}), 
                        endorse(abandonElectionReason, {TT<-TT}),
                        endorse(tellerIndex, {TT<-SUP}));
    }
    /**
     * Record that an election has been abandoned. Always throws the exception.
     * TODO: Should probably change the exception from an IOException to something else.
     */
     static void abandonElection{TT<-TT}(PrivateKey{TT<-TT} tellerPrivKey,
             TTStore[TT]{TT<-SUP} ttstore_,
             BBClientUtil{TT<-SUP} bb_,
             ElectionID{TT<-SUP} electionID_, String{TT<-TT} abandonElectionReason, int{TT<-SUP} tellerIndex_):{} 
     throws IOException  
         TTStore[TT] ttstore = endorse(ttstore_, {TT<-TT});
         BBClientUtil bb = endorse(bb_, {TT<-TT});
         ElectionID electionID = endorse(electionID_, {TT<-TT});
         int tellerIndex = endorse(tellerIndex_, {TT<-TT}); 

         
         if (ttstore == null || electionID == null || bb == null) {
            throw new IOException("invalid election id");
        }
        // Record that the election has been abandoned
        ttstore.storeAbandoment(electionID, abandonElectionReason);
        // post abandonment reason to the BB
        try {
            bb.post(ElectionAbandonment.META, 
                    new ElectionAbandonment(tellerIndex, 
                                            true, 
                                            abandonElectionReason), 
                    tellerPrivKey);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
        
        
        throw new IOException(abandonElectionReason);             
        
    }
    
    
}