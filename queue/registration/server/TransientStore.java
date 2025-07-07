/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import civitas.common.*;
import civitas.crypto.*;

import jif.util.*;

/**
 * A temporary implementation of RTStore, which uses in-memory structures only.
 */
public class TransientStore[principal RT] implements RTStore[RT] authority (RT)
{
    private final Map[{RT<-RT},{RT<-RT}]  store; // map from electionID strings to indexes (stored as strings)
    private final Map[{RT<-RT},{RT<-RT}]  cacheStore; // map from electionID strings to TransientStoreCacheHolders
    private final Map[{RT<-RT},{RT<-RT}]  abandonmentStore; // map from electionID strings to abandonment reasons (strings)
    private final Map[{RT<-RT},{RT<-RT}]  lastMessageStore; // map from electionID strings to message ids of last message processed (strings)
    private final Map[{RT<-RT},{RT<-RT}]  voterCapabilitiesStore; // map from electionID strings to (map from voter name to TransientStoreCapabilitiesHolders)
    private final Map[{RT<-RT},{RT<-RT}]  voterEncryptFactorsStore; // map from electionID strings to (map from voter name to TransientStoreEncryptFactorHolders)
    private final Map[{RT<-RT},{RT<-RT}]  voterDetailsStore; // map from electionID strings to (map from voter name to TransientStoreVoterDetailsHolders)
    private final String  cacheRootDir;
    TransientStore{RT<-RT}(String  cacheRootDir) {
        this.cacheRootDir = cacheRootDir;
        store = new HashMap[{RT<-RT},{RT<-RT}]();        
        cacheStore = new HashMap[{RT<-RT},{RT<-RT}]();        
        abandonmentStore = new HashMap[{RT<-RT},{RT<-RT}]();        
        lastMessageStore = new HashMap[{RT<-RT},{RT<-RT}]();        
        voterCapabilitiesStore = new HashMap[{RT<-RT},{RT<-RT}]();        
        voterEncryptFactorsStore = new HashMap[{RT<-RT},{RT<-RT}]();        
        voterDetailsStore = new HashMap[{RT<-RT},{RT<-RT}]();        
    }
    public void storeAcceptance{RT<-RT}(ElectionID{RT<-RT} election){
        if (store == null || election == null) return;
        String e = election.toString();
        if (!store.containsKey(e)) {
            store.put(election.toString(), null);        
        }
    }

    public boolean{RT<-RT} isAcceptedElection{RT<-RT}(ElectionID{RT<-RT} election) {
        if (store == null || election == null) return false;
        String e = election.toString();
        return store.containsKey(e);
    }

    public ElectionCache{RT<-RT} electionCache{RT<-RT}(ElectionID{RT<-RT} election, int{RT<-RT} tellerIndex) {
        if (cacheStore == null || election == null) return null;
        try {
            TransientStoreCacheHolder[RT] h = (TransientStoreCacheHolder[RT])cacheStore.get(election.toString());
            if (h == null) {
                h = new TransientStoreCacheHolder[RT](new FileBasedElectionCache(cacheRootDir, "regTeller"+tellerIndex+election.toString()));
                cacheStore.put(election.toString(), h);
            }
            return h.o;
        }
        catch (ClassCastException imposs) {
            return null;
        }
    }
    
    public void storeIndex{RT<-RT}(ElectionID{RT<-RT} election, int{RT<-RT} tellerIndex){
        if (store == null || election == null) return;
        store.put(election.toString(), new JifString[{RT<-RT}](Integer.toString(tellerIndex)));        
    }
    public int{RT<-RT} retrieveIndex{RT<-RT}(ElectionID{RT<-RT} election) {
        if (store == null || election == null) return -1;
        try {
            JifString[{RT<-RT}] ind = (JifString[{RT<-RT}])store.get(election.toString());
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
    
    public void storeAbandoment{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} reason) {
        JifString[{RT<-RT}] s = new JifString[{RT<-RT}](reason);
        if (abandonmentStore == null || election == null) return;
        abandonmentStore.put(election.toString(), s);
    }
    public boolean{RT<-RT} isAbandonedElection{RT<-RT}(ElectionID{RT<-RT} election) {
        if (abandonmentStore == null || election == null) return false;
        String e = election.toString();
        return abandonmentStore.containsKey(e);
    }
    public boolean{RT<-sup} isAbandonedElection{RT<-sup}(principal sup, ElectionID{RT<-sup} election) 
     
        return endorse(isAbandonedElection(endorse(election, {RT<-RT})), {RT<-sup});
    }
        
    public String{RT<-RT} retrieveAbandonment{RT<-RT}(ElectionID{RT<-RT} election) {
        if (abandonmentStore == null || election == null) return null;
        try {
            JifString[{RT<-RT}] s = (JifString[{RT<-RT}])abandonmentStore.get(election.toString());
            if (s == null) return null;
            return s.s;
        }
        catch (ClassCastException e) {
            return null;
        }
    }
    public String{RT<-sup} retrieveAbandonment{RT<-sup}(principal sup, ElectionID{RT<-sup} election)  
        return endorse(retrieveAbandonment(endorse(election, {RT<-RT})), {RT<-sup});
    }

    public Long{RT<-RT} retrieveLastMessageProcessed{RT<-RT}(ElectionID{RT<-RT} election) {
        if (lastMessageStore == null || election == null) return null;
        try {
            JifString[{RT<-RT}] s = (JifString[{RT<-RT}])lastMessageStore.get(election.toString());
            if (s == null) return null;
            return Long.valueOf(s.s);
        }
        catch (ClassCastException e) {
            return null;
        }        
        catch (NumberFormatException e) {
            return null;
        }        
    }
    public void storeLastMessageProcessed{RT<-RT}(ElectionID{RT<-RT} election, long{RT<-RT} messageID) {
        JifString[{RT<-RT}] s = new JifString[{RT<-RT}]("" + messageID);
        if (lastMessageStore == null || election == null) return;
        lastMessageStore.put(election.toString(), s);        
    }
    public boolean{RT<-RT} voterCapabilitiesGenerated{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        if (voterCapabilitiesStore == null || election == null) return false;
        String e = election.toString();
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterCapabilitiesStore.get(e);
            return m != null && m.containsKey(voterName);
        }
        catch (ClassCastException imposs) {
            return false;
        }
    }
    public VoterCapabilityShares[RT]{RT<-RT} retrieveVoterCapabilityShares{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        if (voterCapabilitiesStore == null || election == null) return null;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterCapabilitiesStore.get(election.toString());
            if (m == null) return null;
            TransientStoreCapabilitiesHolder[RT] h = (TransientStoreCapabilitiesHolder[RT])m.get(voterName);
            if (h == null) return null;
            return h.o;
        }
        catch (ClassCastException imposs) {
            return null;
        }
    }
    public void storeVoterCapabilityShares{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, VoterCapabilityShares[RT]{RT<-RT} capabilities) {
        TransientStoreCapabilitiesHolder[RT] h = new TransientStoreCapabilitiesHolder[RT](capabilities);
        if (voterCapabilitiesStore == null || election == null) return;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterCapabilitiesStore.get(election.toString());
            if (m == null) {
                m = new HashMap[{RT<-RT},{RT<-RT}]();
                voterCapabilitiesStore.put(election.toString(), m);
            }
            
            m.put(voterName, h);
        }
        catch (ClassCastException imposs) { }
    }
    public ElGamalReencryptFactor{RT<-RT;RT->RT}[]{RT<-RT} retrieveVoterEncryptFactors{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        if (voterEncryptFactorsStore == null || election == null) return null;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterEncryptFactorsStore.get(election.toString());
            if (m == null) return null;
            TransientStoreEncryptFactorsHolder[RT] h = (TransientStoreEncryptFactorsHolder[RT])m.get(voterName);
            if (h == null) return null;
            return h.o;
        }
        catch (ClassCastException imposs) {
            return null;
        }
    }
    public void storeVoterEncryptFactors{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, ElGamalReencryptFactor{RT<-RT;RT->RT}[]{RT<-RT} factors) {
        TransientStoreEncryptFactorsHolder[RT] h = new TransientStoreEncryptFactorsHolder[RT](factors);
        if (voterEncryptFactorsStore == null || election == null) return;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterEncryptFactorsStore.get(election.toString());
            if (m == null) {
                m = new HashMap[{RT<-RT},{RT<-RT}]();
                voterEncryptFactorsStore.put(election.toString(), m);
            }
            
            m.put(voterName, h);
        }
        catch (ClassCastException imposs) { }
    }
    
    public VoterDetails{RT<-RT} retrieveVoterDetails{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        if (voterDetailsStore == null || election == null) return null;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterDetailsStore.get(election.toString());
            if (m == null) return null;
            TransientStoreVoterDetailsHolder[RT] h = (TransientStoreVoterDetailsHolder[RT])m.get(voterName);
            if (h == null) return null;
            return h.o;
        }
        catch (ClassCastException imposs) {
            return null;
        }        
    }
    public void storeVoterDetails{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, VoterDetails{RT<-RT} vd) {
        TransientStoreVoterDetailsHolder[RT] h = new TransientStoreVoterDetailsHolder[RT](vd);
        if (voterDetailsStore == null || election == null) return;
        try {
            Map[{RT<-RT},{RT<-RT}] m = (Map[{RT<-RT},{RT<-RT}])voterDetailsStore.get(election.toString());
            if (m == null) {
                m = new HashMap[{RT<-RT},{RT<-RT}]();
                voterDetailsStore.put(election.toString(), m);
            }
            
            m.put(voterName, h);
        }
        catch (ClassCastException imposs) { }        
    }
    public PublicKey{RT<-RT} retrieveVoterPublicKey{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        VoterDetails vd = retrieveVoterDetails(election, voterName);
        if (vd == null) return null;
        return vd.publicKey;
    }
    public ElGamalPublicKey{RT<-RT} retrieveVoterEGPublicKey{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName) {
        VoterDetails vd = retrieveVoterDetails(election, voterName);
        if (vd == null) return null;
        return vd.egPublicKey;
    }
    
    
}

class TransientStoreHolder[principal RT] implements JifObject[{RT<-RT}] {
    public String{RT<-RT} toString() { return ""; }
    public int{RT<-RT} hashCode() { return 0; }
    public boolean{RT<-RT; obj} equals(IDComparable[{RT<-RT}] obj) {
        return obj == this;
    }
    public boolean{RT<-RT; *lbl; lbl; obj} equals(  IDComparable  obj) {
        return obj == this;        
    }    
}

class TransientStoreCacheHolder[principal RT] extends TransientStoreHolder[RT] {
    public final ElectionCache  o;
    TransientStoreCacheHolder(ElectionCache  o) {
        this.o = o;
        super();
    }
}

class TransientStoreCapabilitiesHolder[principal RT] extends TransientStoreHolder[RT] {
    public final VoterCapabilityShares[RT]  o;
    TransientStoreCapabilitiesHolder(VoterCapabilityShares[RT]  o) {
        this.o = o;
        super();
    }
}

class TransientStoreEncryptFactorsHolder[principal RT] extends TransientStoreHolder[RT] {
    public final ElGamalReencryptFactor{RT<-RT;RT->RT}[]  o;
    TransientStoreEncryptFactorsHolder(ElGamalReencryptFactor{RT<-RT;RT->RT}[]  o) {
        this.o = o;
        super();
    }
}


class TransientStoreVoterDetailsHolder[principal RT] extends TransientStoreHolder[RT] {
    public final VoterDetails  o;
    TransientStoreVoterDetailsHolder(VoterDetails  o) {
        this.o = o;
        super();
    }
}
