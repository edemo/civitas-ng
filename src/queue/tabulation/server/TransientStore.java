/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import civitas.common.*;
import civitas.crypto.*;

import jif.util.*;

import java.io.*;

/**
 * A temporary implementation of TTStore, which uses in-memory structures only.
 */
public class TransientStore[principal TT] /*implements TTStore[TT]*/ authority (TT)
{
    private final Map[{TT<-TT},{TT<-TT}]  store; // map from electionID strings to indexes (stored as strings)
    private final Map[{TT<-TT},{TT<-TT}]  cacheStore; // map from electionID strings to TransientStoreCacheHolders
    private final Map[{TT<-TT},{}]  keyShareStore; // map from electionID strings to key shares (stored as strings)
    private final Map[{TT<-TT},{TT<-TT}]  abandonmentStore; // map from electionID strings to abandonment reasons (strings)
    private final Map[{TT<-TT},{TT<-TT}]  voteVoteSharesStore; // map from electionID strings to TransientStoreShareHolder
    private final Map[{TT<-TT},{TT<-TT}]  rollVoteSharesStore; // map from electionID strings to TransientStoreShareHolder
    private final Map[{TT<-TT},{}]  voteMixStore; // map from electionID strings to TransientStoreMixHolder
    private final Map[{TT<-TT},{}]  electoralRollMixStore; // map from electionID strings to TransientStoreMixHolder
    private final Map[{TT<-TT}, {TT<-TT}]  electoralRollTellerMixOKStore; // map from electionID strings to Sets of tellerIndexes that have had their electoral roll mix checked
    private final Map[{TT<-TT}, {TT<-TT}]  voteTellerMixOKStore; // map from electionID strings to Sets of tellerIndexes that have had their vote mix checked
    
    private final String  cacheRootDir;
    TransientStore{TT<-TT}(String  cacheRootDir) {
        this.cacheRootDir = cacheRootDir;
        store = new HashMap[{TT<-TT},{TT<-TT}]();        
        cacheStore = new HashMap[{TT<-TT},{TT<-TT}]();        
        keyShareStore = new HashMap[{TT<-TT},{}]();        
        abandonmentStore = new HashMap[{TT<-TT},{TT<-TT}]();        
        voteVoteSharesStore = new HashMap[{TT<-TT},{TT<-TT}]();        
        rollVoteSharesStore = new HashMap[{TT<-TT},{TT<-TT}]();        
        voteMixStore = new HashMap[{TT<-TT},{}]();        
        electoralRollMixStore = new HashMap[{TT<-TT},{}]();        
        electoralRollTellerMixOKStore = new HashMap[{TT<-TT},{TT<-TT}]();        
        voteTellerMixOKStore = new HashMap[{TT<-TT},{TT<-TT}]();        
    }
    public void storeAcceptance{TT<-TT}(ElectionID{TT<-TT} election){
        if (store == null || election == null) return;
        String e = election.toString();
        if (!store.containsKey(e)) {
            store.put(election.toString(), null);        
        }
    }

    public boolean{TT<-TT} isAcceptedElection{TT<-TT}(ElectionID{TT<-TT} election) {
        if (store == null || election == null) return false;
        String e = election.toString();
        return store.containsKey(e);
    }
    
    public ElectionCache{TT<-TT} electionCache{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} tellerIndex) {
        if (cacheStore == null || election == null) return null;
        try {
            TransientStoreCacheHolder[TT] h = (TransientStoreCacheHolder[TT])cacheStore.get(election.toString());
            if (h == null) {
                h = new TransientStoreCacheHolder[TT](new FileBasedElectionCache(cacheRootDir, "tabTeller"+tellerIndex+election.toString()));
                cacheStore.put(election.toString(), h);
            }
            return h.o;
        }
        catch (ClassCastException imposs) {
            return null;
        }
    }

    public void storeIndex{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} tellerIndex){
        if (store == null || election == null) return;
        store.put(election.toString(), new JifString[{TT<-TT}](Integer.toString(tellerIndex)));        
    }
    public int{TT<-TT} retrieveIndex{TT<-TT}(ElectionID{TT<-TT} election) {
        if (store == null || election == null) return -1;
        try {
            JifString[{TT<-TT}] ind = (JifString[{TT<-TT}])store.get(election.toString());
            if (ind == null) return -1;
            return Integer.parseInt(ind.s);
        }
        catch (ClassCastException e) {
            return -1;
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
    public int{TT<-sup} retrieveIndex{TT<-sup}(principal sup, ElectionID{TT<-sup} election) 
     
        return endorse(retrieveIndex(endorse(election, {TT<-TT})), {TT<-sup});
    }
    
    public void storeKeyShare{TT<-TT}(ElectionID{TT<-TT} election, ElGamalKeyPairShare{TT->TT;TT<-TT} keyshare) {
        TransientStoreElGamalKeyPairShareHolder[TT] h = new TransientStoreElGamalKeyPairShareHolder[TT](keyshare);
        if (keyShareStore == null || election == null) return;
        keyShareStore.put(election.toString(), h);        

    }

    public ElGamalKeyPairShare{TT->TT;TT<-TT} retrieveKeyShare{TT<-TT}(ElectionID{TT<-TT} election) {
        if (keyShareStore == null || election == null) return null;
        try {
            TransientStoreElGamalKeyPairShareHolder[TT] h = (TransientStoreElGamalKeyPairShareHolder[TT])keyShareStore.get(election.toString());
            if (h == null) return null;
            return h.keyPairShare;
        }
        catch (ClassCastException e) {
            return null;
        }
    }
    public ElGamalKeyPairShare{TT->TT;TT<-sup} retrieveKeyShare{TT<-sup}(principal sup, ElectionID{TT<-sup} election)
     
        return endorse(retrieveKeyShare(endorse(election, {TT<-TT})), {TT->TT;TT<-sup});
    }
    

    public void clearVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (voteVoteSharesStore == null || election == null) return;        
        String key = election.toString() + block;
        voteVoteSharesStore.remove(key);        
    }
    
    public void storeVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, PETShare{TT->TT;TT<-TT}[]{TT<-TT} shares) {
        if (voteVoteSharesStore == null || election == null) return;

        TransientStoreShareHolder[TT] h = new TransientStoreShareHolder[TT](shares);
        String key = election.toString() + block;
        voteVoteSharesStore.put(key , h);        
    }
    public PETShare{TT->TT;TT<-TT}[]{TT<-TT} retrieveVoteVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (voteVoteSharesStore == null || election == null) return null;
        try {
            String key = election.toString() + block;
            TransientStoreShareHolder[TT] h = (TransientStoreShareHolder[TT])voteVoteSharesStore.get(key);
            if (h != null) return h.o;
        }
        catch (ClassCastException e) { }
        return null;
    }
    public PETShare{TT->TT;TT<-TT}[]{TT<-sup} retrieveVoteVotePETShares{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block) 
     
        return endorse(retrieveVoteVotePETShares(endorse(election, {TT<-TT}), endorse(block, {TT<-TT})), {TT<-sup});
    }
    
    public void clearRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (rollVoteSharesStore == null || election == null) return;        
        String key = election.toString() + block;
        rollVoteSharesStore.remove(key);        
    }
    public void storeRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, PETShare{TT->TT;TT<-TT}[]{TT<-TT} shares) {
        if (rollVoteSharesStore == null || election == null) return;
        String key = election.toString() + block;

        TransientStoreShareHolder[TT] h = new TransientStoreShareHolder[TT](shares);
        rollVoteSharesStore.put(key, h);        
    }
    public PETShare{TT->TT;TT<-TT}[]{TT<-TT} retrieveRollVotePETShares{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (rollVoteSharesStore == null || election == null) return null;
        try {
            String key = election.toString() + block;
            TransientStoreShareHolder[TT] h = (TransientStoreShareHolder[TT])rollVoteSharesStore.get(key);
            if (h != null) return h.o;
        }
        catch (ClassCastException e) { }
        return null;
    }
    public PETShare{TT->TT;TT<-TT}[]{TT<-sup} retrieveRollVotePETShares{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block)
     
        return endorse(retrieveRollVotePETShares(endorse(election, {TT<-TT}), endorse(block, {TT<-TT})), {TT<-sup});
    }    

    
    public void storeTellerMixOK{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} tellerIndex, boolean{TT<-TT} isVoteMix, int{TT<-TT} block) {
        if (electionID == null) return;
        Map[{TT<-TT},{TT<-TT}] m = null;
        if (isVoteMix) {
            m = electoralRollTellerMixOKStore;
        }
        else {
            m = voteTellerMixOKStore;
        }
        if (m == null) return;

        try {
            String key = electionID.toString() + block;
            Set[{TT<-TT}] s = (Set[{TT<-TT}])m.get(key);
            if (s == null) {
                s = new HashSet[{TT<-TT}]();
                m.put(key, s);
            }

            s.add(new JifString[{TT<-TT}](Integer.toString(tellerIndex)));
        }
        catch (ClassCastException imposs) { }
        catch (IllegalArgumentException imposs) { }
    }
    
    public boolean{TT<-TT} retrieveTellerMixOK{TT<-TT}(ElectionID{TT<-TT} electionID, int{TT<-TT} tellerIndex, boolean{TT<-TT} isVoteMix, int{TT<-TT} block) {
        if (electionID == null) return false;
        Map[{TT<-TT},{TT<-TT}] m = null;
        if (isVoteMix) {
            m = electoralRollTellerMixOKStore;
        }
        else {
            m = voteTellerMixOKStore;
        }
        if (m == null) return false;

        try {
            String key = electionID.toString() + block;
            Set[{TT<-TT}] s = (Set[{TT<-TT}])m.get(key);
            if (s == null) return false;       
            return s.contains(new JifString[{TT<-TT}](Integer.toString(tellerIndex)));
        }
        catch (ClassCastException imposs) { }
        return false;
    }
    public boolean{TT<-sup} retrieveTellerMixOK{TT<-sup}(principal sup, ElectionID{TT<-sup} electionID, int{TT<-sup} tellerIndex, boolean{TT<-sup} isVoteMix, int{TT<-sup} block)
     
        return endorse(retrieveTellerMixOK(endorse(electionID, {TT<-TT}), endorse(tellerIndex, {TT<-TT}), endorse(isVoteMix, {TT<-TT}), endorse(block, {TT<-TT})), {TT<-sup});
    }

    public void clearVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (voteMixStore == null || election == null) return;        
        String key = election.toString() + block;
        voteMixStore.remove(key);        
    }
    public void storeVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, VoteMixInfo[TT]{TT<-TT} leftMixInfo, VoteMixInfo[TT]{TT<-TT} rightMixInfo) {
        if (election == null || voteMixStore == null) return;

        TransientStoreMixHolder[TT] h = new TransientStoreMixHolder[TT](leftMixInfo, rightMixInfo);
        String key = election.toString() + block;
        voteMixStore.put(key, h);        
    }
    public VoteMixInfo[TT]{TT<-TT} retrieveVoteMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, boolean{TT<-TT} rightPerm) {
        if (election == null || voteMixStore == null) return null;

        try {
            String key = election.toString() + block;
            TransientStoreMixHolder[TT] h = (TransientStoreMixHolder[TT])voteMixStore.get(key);
            if (h != null) return (VoteMixInfo[TT])(rightPerm?h.right:h.left);
        }
        catch (ClassCastException e) { }
        return null;
    }
    public VoteMixInfo[TT]{TT<-TT;TT<-sup} retrieveVoteMixInfo{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, boolean{TT<-sup} rightPerm) 
     
        return endorse(retrieveVoteMixInfo(endorse(election, {TT<-TT}), endorse(block, {TT<-TT}), endorse(rightPerm, {TT<-TT})), {TT<-sup});
    }
    

    public void clearElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block) {
        if (electoralRollMixStore == null || election == null) return;        
        String key = election.toString() + block;
        electoralRollMixStore.remove(key);        
    }
    public void storeElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, ElectoralRollMixInfo[TT]{TT<-TT} leftMixInfo, ElectoralRollMixInfo[TT]{TT<-TT} rightMixInfo) {
        if (election == null || electoralRollMixStore == null) return;

        TransientStoreMixHolder[TT] h = new TransientStoreMixHolder[TT](leftMixInfo, rightMixInfo);
        String key = election.toString() + block;
        electoralRollMixStore.put(key, h);        
    }
    public ElectoralRollMixInfo[TT]{TT<-TT} retrieveElectoralRollMixInfo{TT<-TT}(ElectionID{TT<-TT} election, int{TT<-TT} block, boolean{TT<-TT} rightPerm) {
        if (election == null || electoralRollMixStore == null) return null;

        try {
            String key = election.toString() + block;
            TransientStoreMixHolder[TT] h = (TransientStoreMixHolder[TT])electoralRollMixStore.get(key);
            if (h != null) return (ElectoralRollMixInfo[TT])(rightPerm?h.right:h.left);
        }
        catch (ClassCastException e) { }
        return null;
    }
    public ElectoralRollMixInfo[TT]{TT<-TT;TT<-sup} retrieveElectoralRollMixInfo{TT<-sup}(principal sup, ElectionID{TT<-sup} election, int{TT<-sup} block, boolean{TT<-sup} rightPerm)
     
        return endorse(retrieveElectoralRollMixInfo(endorse(election, {TT<-TT}), endorse(block, {TT<-TT}), endorse(rightPerm, {TT<-TT})), {TT<-sup});
    }

    public void storeAbandoment{TT<-TT}(ElectionID{TT<-TT} election, String{TT<-TT} reason) {
        JifString[{TT<-TT}] s = new JifString[{TT<-TT}](reason);
        if (abandonmentStore == null || election == null) return;
        abandonmentStore.put(election.toString(), s);
    }
    public boolean{TT<-TT} isAbandonedElection{TT<-TT}(ElectionID{TT<-TT} election) {
        if (abandonmentStore == null || election == null) return false;
        String e = election.toString();
        return abandonmentStore.containsKey(e);
    }
    public boolean{TT<-sup} isAbandonedElection{TT<-sup}(principal sup, ElectionID{TT<-sup} election) 
     
        return endorse(isAbandonedElection(endorse(election, {TT<-TT})), {TT<-sup});
    }
        
    public String{TT<-TT} retrieveAbandonment{TT<-TT}(ElectionID{TT<-TT} election) {
        if (abandonmentStore == null || election == null) return null;
        try {
            JifString[{TT<-TT}] s = (JifString[{TT<-TT}])abandonmentStore.get(election.toString());
            if (s == null) return null;
            return s.s;
        }
        catch (ClassCastException e) {
            return null;
        }
    }
    public String{TT<-sup} retrieveAbandonment{TT<-sup}(principal sup, ElectionID{TT<-sup} election)  
        return endorse(retrieveAbandonment(endorse(election, {TT<-TT})), {TT<-sup});
    }
        
}

class TransientStoreHolder[principal TT] implements JifObject[{TT<-TT}] {
    public String{TT<-TT} toString() { return ""; }
    public int{TT<-TT} hashCode() { return 0; }
    public boolean{TT<-TT; obj} equals(IDComparable[{TT<-TT}] obj) {
        return obj == this;
    }
    public boolean{TT<-TT; *lbl; lbl; obj} equals(  IDComparable  obj) {
        return obj == this;        
    }    
}

class TransientStoreCacheHolder[principal TT] extends TransientStoreHolder[TT] {
    public final ElectionCache  o;
    TransientStoreCacheHolder(ElectionCache  o) {
        this.o = o;
        super();
    }
}

class TransientStoreShareHolder[principal TT] extends TransientStoreHolder[TT] {
    public final PETShare{TT->TT;TT<-TT}[]  o;
    TransientStoreShareHolder(PETShare{TT->TT;TT<-TT}[]  o) {
        this.o = o;
        super();
    }
}

class TransientStoreMixHolder[principal TT] extends TransientStoreHolder[TT] {
    public final MixInfo[TT]  left;
    public final MixInfo[TT]  right;
    TransientStoreMixHolder(MixInfo[TT]  left, MixInfo[TT]  right) {
        this.left = left;
        this.right = right;
        super();
    }
}
class TransientStoreElGamalKeyPairShareHolder[principal TT] extends TransientStoreHolder[TT] {
    public final ElGamalKeyPairShare{TT->TT;TT<-TT} keyPairShare;

    TransientStoreElGamalKeyPairShareHolder(ElGamalKeyPairShare{TT->TT;TT<-TT} keyPairShare) {
        this.keyPairShare = keyPairShare;
        super();
    }
}

