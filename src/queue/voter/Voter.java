/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.voter;

import civitas.common.*;
import civitas.crypto.*;
import civitas.registration.client.*;
import civitas.bboard.client.*;
import civitas.bboard.common.*;
import jif.util.*;
import java.io.*;
import until.util.*;

/**
 * The class implements protocols for the voter to register and vote.
 */
public class Voter {
    /**
     * Post the voter submission <code>v</code> for the election described by the <code>ElectionDetails details</code>.
     */
    public static void vote{}(principal{} voter, ElectionDetails{} electionDetails, ElectionCache{} cache, VoterSubmission{} v) 
    throws (IOException{}, IllegalArgumentException{}) 
     
        if (electionDetails == null || electionDetails.electionID == null) return;
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, cache);
        if (tellerDetails == null || tellerDetails.voterBBs == null|| v  == null) return;
        
        
        // choose voter BBs to post to. At the moment, just post to them all.
        for (int i = 0; i < tellerDetails.voterBBs.length; i++) {
            Host h = null;
            try {
                h = tellerDetails.voterBBs[i];
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            if (h != null) {
                BBClientUtil bb = new BBClientUtil(h, electionDetails.electionID.id);

                // Note that the voter's post to the BB must be left unsighed: the vote is meant to be annonymous
                bb.post(VoterSubmission.meta(v.voterBlock), v);        
            }
        }
        
        
    }
    /**
     * Take the ballot, and the (unencrypted) capabilities, and submit a vote for the election described by the <code>ElectionDetails details</code>.
     */
    public static void vote{voter<-voter}(principal{voter<-voter} voter, 
            String{voter<-voter} voterName, 
            ElectionDetails{voter<-voter} details, 
            ElectionCache{voter<-voter} cache, 
            Ballot[{voter->voter;voter<-voter}]{voter<-voter} b, 
            VoteCapability{voter->voter;voter<-voter}  []{voter<-voter} caps) throws (IOException{}, IllegalArgumentException{})  
        // get the voter block
        int voterBlock = -1;
        IOException ioe = null;
        try {
            voterBlock = ElectionUtil.retrieveVotersBlock(cache, details, voterName);
        }
        catch (IOException e) {
            ioe = e;
        }
        IOException eioe = endorse(ioe, {voter<-voter});
        if (eioe != null) throw eioe;
        vote(voter, endorse(voterBlock, {voter<-voter}), details, cache, b, caps);        
    }
    /**
     * Take the ballot, and the (unencrypted) capabilities, and submit a vote for the election described by the <code>ElectionDetails details</code>.
     */
    public static void vote{voter<-voter}(principal{voter<-voter} voter, 
            int{voter<-voter} voterBlock, 
            ElectionDetails{voter<-voter} details, 
            ElectionCache{voter<-voter} cache, 
            Ballot[{voter->voter;voter<-voter}]{voter<-voter} b, 
            VoteCapability{voter->voter;voter<-voter}  []{voter<-voter} caps) throws (IOException{}, IllegalArgumentException{})  
        if (details == null || details.ballotDesign == null) return;
        // turn the array of voter capabilities into a map
        Map[{voter->voter;voter<-voter}, {voter->voter;voter<-voter}] m = new HashMap[{voter->voter;voter<-voter},{voter->voter;voter<-voter}]();
        List[{voter<-voter}] contexts = details.ballotDesign.contextsNeeded(new label {voter<-voter}, details.baseContext(voterBlock));
        for (int i = 0; contexts != null && caps != null && i < caps.length; i++) {
            try {
                m.put(contexts.get(i), new JifObjectWrapper[new label {voter->voter;voter<-voter}](caps[i]));
            }
            catch (IndexOutOfBoundsException unlikely) { }
        }
        vote(voter, voterBlock, details, cache, b, m);
    }
    /**
     * Take the ballot, and the (unencrypted) capabilities, and submit a vote for the election described by the <code>ElectionDetails details</code>.
     * The capabilities are contained in a map from context strings to capabilities, indicating which capability should be used
     * for which vote.
     */
    public static void vote{voter<-voter}(principal{voter<-voter} voter, 
            int{voter<-voter} voterBlock, 
            final ElectionDetails{voter<-voter} details, 
            ElectionCache{voter<-voter} cache, 
            Ballot[{voter->voter;voter<-voter}]{voter<-voter} b, 
            Map[{voter->voter;voter<-voter},{voter->voter;voter<-voter}]{voter<-voter} caps) throws (IOException{}, IllegalArgumentException{})  
        if (details == null || details.ballotDesign == null || 
                details.electionID == null || b == null || caps == null) return;

        CiphertextList cl_ = null;                
        ElGamalPublicKey ttPubKey_ = null;
        IOException ioe = null;
        try {
            cl_ = ElectionUtil.retrieveCiphertextList(voter, details, cache);
            ttPubKey_ = ElectionUtil.retrieveTabTellerSharedPublicKey(details, cache);
        }
        catch (IOException e) {
            ioe = e;
        }
        IOException eioe = endorse(ioe, {voter<-voter});
        if (eioe != null) throw eioe;

        CiphertextList cl = endorse(cl_, {voter<-voter});                
        ElGamalPublicKey ttPubKey = endorse(ttPubKey_, {voter<-voter});
        if (cl == null || cl.ciphertexts == null) {
            throw new IOException("No well known ciphertexts!");
        }

        VoterSubmission v = null;
        IllegalArgumentException iae = null;
        try {
            v = details.ballotDesign.decompose(new label {voter->voter;voter<-voter}, b, voterBlock,
                                       ttPubKey,
                                       cl.ciphertexts, 
                                       details.baseContext(voterBlock), 
                                       caps);
        }
        catch (IllegalArgumentException e) {
            iae = e;
        }
        IllegalArgumentException diae = declassify(iae, {voter<-voter});
        VoterSubmission dv = declassify(v, {voter<-voter});
        
        if (diae != null) throw diae;
        
        declassify ({voter<-voter}) {
            vote(voter, details, cache, dv);
        }
    }
    
    public static VoterCapabilities{voterPubKey->voterPubKey} retrieveCapabilities{}(ElectionCache{} electionCache,
            final ElectionDetails{} electionDetails, 
            TellerDetails{} tellers,
            String{} voterName, 
            final ElGamalPublicKey{} voterEGPubKey,
            ElGamalPrivateKey{} voterEGPrivKey,
            final PublicKey{} voterPubKey,
            PrivateKey{} voterPrivKey) throws (IOException{}, IllegalArgumentException{}, CryptoException{}) 
             
        // go to each registration teller
        if (tellers == null || electionDetails == null || tellers.registrationTellers == null || electionDetails.ballotDesign == null) {
            return null;
        }
        
        int voterBlock = ElectionUtil.retrieveVotersBlock(electionCache, electionDetails, voterName);

        int numRegTellers = tellers.registrationTellers.length;        
        
        final ConditionHolder[{}] combined = new ConditionHolder[{}]();
        combined.c = false;
        final ConditionHolder[{}] postCombined = new ConditionHolder[{}]();
        postCombined.c = false;
        // ERASURE POLICY
        // Voter requires that the share must be erased after being combined, but expresses that
        // the combined capability may be declassified (to voterPubKey).
        // If Jif could reason more precisely about cryptography, we may be able to express
        // that instead of being private to the voter, the capability should be a level that can be
        // sent to the ballot boxes. Also, if the policies were more expressive, we could express
        // that it is the result of combining the shares (i.e., the capability) that may
        // be declassified.
        final elabel capShareLbl =  new elabel {voterPubKey->[voterPubKey /postCombined.c/ *] combined.c-> voterPubKey};
        VoteCapabilityShare{*capShareLbl}[][] allShares = new VoteCapabilityShare[numRegTellers][];
        ElGamalPublicKey tabTellerSharedPK = ElectionUtil.retrieveTabTellerSharedPublicKey(electionDetails, electionCache);
        
        RTClientUtil rtcu = new RTClientUtil();
        int numCaps = electionDetails.ballotDesign.votesProducedPerBallot();
        
        for (int i = 0; i < numRegTellers; i++) {
            int tellerIndex = i+1;
            final VoterCapabilitySharesAndProofHolder vcsph = rtcu.retrieveCapabilities(electionCache,
                                                   electionDetails,
                                                   tellers,
                                                   tellerIndex,
                                                   voterName,
                                                   voterEGPubKey,
                                                   voterEGPrivKey,
                                                   voterPubKey,
                                                   voterPrivKey);
            if (vcsph == null) throw new IOException("Unable to get capability shares for teller " + tellerIndex);
            // retrieve the capabilities posted by this teller.
            ElGamalSignedCiphertext{}[] postedCapabilities = ElectionUtil.retrieveEncryptedCapabilitySharesForVoter(electionCache, 
                                                                                                              electionDetails,
                                                                                                              tellerIndex,
                                                                                                              voterName);
            
            // Check the proofs
            VoterCapabilitySharesAndProof vcsp = vcsph.sharesAndProof;
            VoteCapabilityShare{*capShareLbl}[] shares = null;
            
            if (vcsph.voter equiv voterPubKey) {
                shares = verifyVoterShares(vcsp,
                                           voterPubKey,
                                           voterEGPubKey,
                                           tabTellerSharedPK,
                                           postedCapabilities,
                                           voterName,
                                           tellerIndex,
                                           numCaps,
                                           voterBlock,
                                           capShareLbl);
            }
            else {
                throw new IOException("Incorrect voter listed (should be impossible)");                    
            }

            try {             
                allShares[i] = shares;
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        
        // we have now retrieved all of the capabilities. Combine these shares into a single capability.
        VoteCapability{*capShareLbl}[] allCaps = null;
        try {
            allCaps = CryptoUtil.factory().combineVoteCapabilityShares(capShareLbl, allShares, electionDetails.elGamalParameters);
        }
        catch (NullPointerException imposs) { }
        
        // declassify all capabilities WHAT DECLASSIFICATION 
        combined.c = true;
        VoterCapabilities vc = null;
        try {
            vc = declassify(new VoterCapabilities(allCaps==null?null:allCaps.clone(), voterBlock), 
                                                  {*capShareLbl} to {voterPubKey -> voterPubKey} using combined.c);
        }
        catch (UnsatisfiedConditionException imposs) { }
        
        // Erase shares at this point.
        postCombined.c = true;
                
        return vc;
    }
    
    private static VoteCapabilityShare{*capShareLbl}[]{*capShareLbl} 
        verifyVoterShares{}(
            VoterCapabilitySharesAndProof{voterPubKey->voterPubKey} vcsp_,
            final PublicKey{} voterPubKey,
            final ElGamalPublicKey{} voterEGPubKey,
            ElGamalPublicKey{} tabTellerSharedPK,
            ElGamalSignedCiphertext{}[]{} postedCapabilities,
            String{} voterName, 
            int{} tellerIndex_,
            int{} numCaps_,
            int{} voterBlock_,
            final elabel{} capShareLbl) throws (IOException{}) 
    where caller(voterPubKey), 
          endorse({voterPubKey<-voterPubKey}), // ENDORSE
          {voterPubKey->voterPubKey} <= capShareLbl,
          capShareLbl <= {voterPubKey->*} 
    {          
        VoterCapabilitySharesAndProof vcsp = endorse(vcsp_, {voterPubKey->voterPubKey;voterPubKey<-voterPubKey}); // ENDORSE
        int numCaps  = endorse(numCaps_, {voterPubKey->voterPubKey;voterPubKey<-voterPubKey}); // ENDORSE
        int voterBlock = endorse(voterBlock_, {voterPubKey->voterPubKey;voterPubKey<-voterPubKey}); // ENDORSE
        int tellerIndex = endorse(tellerIndex_, {voterPubKey->voterPubKey;voterPubKey<-voterPubKey}); // ENDORSE

        IOException ioex = null;
        VoteCapabilityShare{*capShareLbl}[] shares = null;
        try {
            if (vcsp == null) {
                throw new IOException("Unable to get capability shares for teller " + tellerIndex);
            }
            boolean vcspVerify = vcsp.verify(postedCapabilities, voterEGPubKey, tabTellerSharedPK,
                                             voterName, tellerIndex); 
            if (!endorse(vcspVerify, {voterPubKey->voterPubKey;voterPubKey<-voterPubKey})) { // ENDORSE
                throw new IOException("The proof of the capabilities from teller " + tellerIndex + " do not verify.");                                    
            }

            int vcspLength;
            try {
                vcspLength = vcsp.capabilities.length;
            }
            catch (NullPointerException e) {
                throw new IOException("Unable to get capability shares for teller " + tellerIndex);                    
            }

            if (numCaps >= 0 && numCaps != vcspLength) {
                throw new IOException("Inconsistent number of voter capability shares from different registration tellers");                                    
            }
            if (voterBlock >= 0 && voterBlock != vcsp.voterBlock) {
                throw new IOException("Inconsistent voter blocks from different registration tellers");                                    
            }
            shares = vcsp.capabilities.clone();

        }
        catch (IOException e) {
            ioex = e;
        }
        // DECLASSIFY
        IOException dioex = declassify(ioex, {});
        if (dioex != null) throw dioex;
        return shares;
    }
}