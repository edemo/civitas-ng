/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import civitas.common.*;
import civitas.crypto.*;

/**
 * This interface is for the persistent storage for a registration teller.
 */
public interface RTStore[principal RT] {
    /**
     * Store the fact that this teller has agreed to participate in an
     * election
     */
    public void storeAcceptance{RT<-RT}(ElectionID{RT<-RT} election);

    /**
     * Did the tabulation teller previously agree to participate in the
     * election?
     */
    public boolean{RT<-RT} isAcceptedElection{RT<-RT}(ElectionID{RT<-RT} election);
    
    public ElectionCache{RT<-RT} electionCache{RT<-RT}(ElectionID{RT<-RT} election, int{RT<-RT} tellerIndex);

    /*
     * Store what the teller's index is in the order of vote mixes.
     */
    public void storeIndex{RT<-RT}(ElectionID{RT<-RT} election, int{RT<-RT} tellerIndex);

    /**
     * Retrieve what the teller's index is in the order of vote mixes. Return
     * -1 if no index recorded.
     */
    public int{RT<-RT} retrieveIndex{RT<-RT}(ElectionID{RT<-RT} election);

   public void storeAbandoment{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} reason);
    public boolean{RT<-RT} isAbandonedElection{RT<-RT}(ElectionID{RT<-RT} election);
    public boolean{RT<-sup} isAbandonedElection{RT<-sup}(principal sup, ElectionID{RT<-sup} election);
    public String{RT<-RT} retrieveAbandonment{RT<-RT}(ElectionID{RT<-RT} election);
    public String{RT<-sup} retrieveAbandonment{RT<-sup}(principal sup, ElectionID{RT<-sup} election);    

    public Long{RT<-RT} retrieveLastMessageProcessed{RT<-RT}(ElectionID{RT<-RT} election);
    public void storeLastMessageProcessed{RT<-RT}(ElectionID{RT<-RT} election, long{RT<-RT} messageID);

    public boolean{RT<-RT} voterCapabilitiesGenerated{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public VoterCapabilityShares[RT]{RT<-RT} retrieveVoterCapabilityShares{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public void storeVoterCapabilityShares{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, VoterCapabilityShares[RT]{RT<-RT} capabilities);
    public ElGamalReencryptFactor{RT<-RT;RT->RT}[]{RT<-RT} retrieveVoterEncryptFactors{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public void storeVoterEncryptFactors{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, ElGamalReencryptFactor{RT<-RT;RT->RT}[]{RT<-RT} factors);
        
    public ElGamalPublicKey{RT<-RT} retrieveVoterEGPublicKey{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public PublicKey{RT<-RT} retrieveVoterPublicKey{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public VoterDetails{RT<-RT} retrieveVoterDetails{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName);
    public void storeVoterDetails{RT<-RT}(ElectionID{RT<-RT} election, String{RT<-RT} voterName, VoterDetails{RT<-RT} vd);
}