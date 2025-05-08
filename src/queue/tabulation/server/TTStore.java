/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import civitas.common.*;
import civitas.crypto.*;

/**
 * This interface is for the persistent storage for a tabulation teller. TT is the
 * (public key of the) tabulation teller, and SUP is the (public key of the) 
 * election supervisor.
 */
public interface TTStore[principal TT] {
    /**
     * Store the fact that this teller has agreed to participate in an
     * election
     */
    public void storeAcceptance{TT<-TT}(ElectionID{TT<-TT} election);

    /**
     * Did the tabulation teller previously agree to participate in the
     * election?
     */
    public boolean{TT<-TT} isAcceptedElection{TT<-TT}(ElectionID{TT<-TT} election);

    public ElectionCache{TT<-TT} electionCache{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} tellerIndex);

    /**
     * Store what the teller's index is in the order of vote mixes.
     */
    public void storeIndex{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} tellerIndex);
    
    /**
     * Retrieve what the teller's index is in the order of vote mixes. Return
     * -1 if no index recorded.
     */
    public int{TT<-TT} retrieveIndex{TT<-TT}(ElectionID{TT<-TT} election);
    
    public int{TT<-sup} retrieveIndex{TT<-sup}(principal sup, ElectionID{TT<-sup} election);
    
    /**
     * Store the tabulation teller's share of the key.
     */
    public void storeKeyShare{TT<-TT}(ElectionID{TT<-TT} election, ElGamalKeyPairShare{TT->TT;TT<-TT} keyshare);

    /**
     * Retrieve the tabulation teller's share of the key.
     */
    public ElGamalKeyPairShare{TT->TT;TT<-TT} retrieveKeyShare{TT<-TT}(ElectionID{TT<-TT} election);
    public ElGamalKeyPairShare{TT->TT;TT<-sup} retrieveKeyShare{TT<-sup}(principal sup, ElectionID{TT<-sup} election);
    
    public void storeVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex, PETShare{TT->TT;TT<-TT}[]{TT<-TT} shares);
    public PETShare{TT->TT;TT<-TT}[]{TT<-TT} retrieveVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex);
    public PETShare{TT->TT;TT<-TT}[]{TT<-sup} retrieveVoteVotePETShares{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, int{TT<-sup} ballotIndex);
    public void clearVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex) where caller(TT);
    
    public void storeRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex, PETShare{TT->TT;TT<-TT}[]{TT<-TT} shares);
    public PETShare{TT->TT;TT<-TT}[]{TT<-TT} retrieveRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex);
    public PETShare{TT->TT;TT<-TT}[]{TT<-sup} retrieveRollVotePETShares{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, int{TT<-sup} ballotIndex);
    public void clearRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, int{TT<-TT} ballotIndex) where caller(TT);
    
    public void storeVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} block, VoteMixInfo[TT]{TT<-TT} leftMixInfo, VoteMixInfo[TT]{TT<-TT} rightMixInfo);
    public VoteMixInfo[TT]{TT<-TT} retrieveVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, boolean{TT<-TT} rightPerm);
    public VoteMixInfo[TT]{TT<-TT;TT<-sup} retrieveVoteMixInfo{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, boolean{TT<-sup} rightPerm);
    public void clearVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) where caller(TT);

    public void storeElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} block, ElectoralRollMixInfo[TT]{TT<-TT} leftMixInfo, ElectoralRollMixInfo[TT]{TT<-TT} rightMixInfo);
    public ElectoralRollMixInfo[TT]{TT<-TT} retrieveElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, boolean{TT<-TT} rightPerm);
    public ElectoralRollMixInfo[TT]{TT<-TT;TT<-sup} retrieveElectoralRollMixInfo{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, boolean{TT<-sup} rightPerm);
    public void clearElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) where caller(TT);
    
    // store that the teller indicated by tellerIndex in the election electionID performed the
    // mix correctly.
    public void storeTellerMixOK{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} tellerIndex, boolean{TT<-TT} isVoteMix, int{TT<-TT} block);
    public boolean{TT<-TT} retrieveTellerMixOK{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} tellerIndex, boolean{TT<-TT} isVoteMix, int{TT<-TT} block);
    public boolean{TT<-sup} retrieveTellerMixOK{TT<-sup}(principal sup, ElectionID{TT<-sup} electionID, int{TT<-sup} tellerIndex, boolean{TT<-sup} isVoteMix, int{TT<-sup} block);
    
    public void storeAbandoment{TT<-TT}(ElectionID{TT<-TT} election, String{TT<-TT} reason);
    public boolean{TT<-TT} isAbandonedElection{TT<-TT}(ElectionID{TT<-TT} election);
    public boolean{TT<-sup} isAbandonedElection{TT<-sup}(principal sup, ElectionID{TT<-sup} election);
    public String{TT<-TT} retrieveAbandonment{TT<-TT}(ElectionID{TT<-TT} election);
    public String{TT<-sup} retrieveAbandonment{TT<-sup}(principal sup, ElectionID{TT<-sup} election);    
}