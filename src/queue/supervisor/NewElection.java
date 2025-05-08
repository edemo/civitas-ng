/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.supervisor;

import civitas.common.*;
import civitas.crypto.*;
import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.tabulation.client.*;
import civitas.registration.client.*;
import java.io.*;

/**
 * The class implements protocols for the supervisor to create a new election.
 */
public class NewElection {
    /**
     *  create a new election by creating a new bulletin board. 
     *  Return the election id (which is also the bulletin board id)
     */
    public static String{} createElection{}(String{} bbHost, int{} bbPort, PublicKey{} supervisorPublicKey) throws (IOException{}, IllegalArgumentException{}) {
        BBClientUtil bb = new BBClientUtil(bbHost, bbPort);
        String id = bb.newBoard(supervisorPublicKey);
        return id;
    }


    /**
     * Post the election details to the board
     */
    public static void initializeElection{}(final ElectionDetails{} details, PrivateKey{} supPrivKey) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return;
        if (details.supervisor == null) {
            throw new IllegalArgumentException("No supervisor public key specified");
        }
        if (details.registrar == null) {
            throw new IllegalArgumentException("No supervisor public key specified");
        }
        
        BBClientUtil bb = new BBClientUtil(details.electionID);
        bb.post(ElectionDetails.META, details, supPrivKey);
    }

    
    public static RequestTellerResults{} requestTellers{}(final ElectionDetails{} details, TellerDetails{} tellers) {
        if (tellers == null || tellers.registrationTellers == null || 
                tellers.tabulationTellers == null || details == null || details.electionID == null || 
                tellers.voterBBs == null) return null;
        
        HostResults regTellerResults = requestTellers(tellers.registrationTellers, new RTRequester(), details, tellers);
        HostResults tabTellerResults = requestTellers(tellers.tabulationTellers, new TTRequester(), details, tellers);
        HostResults voterBBResults = requestTellers(tellers.voterBBs, new VoterBBRequester(), details, tellers);

        if (regTellerResults == null || tabTellerResults == null || voterBBResults == null) return null;
        
        TellerDetails accepted = new TellerDetails(new label {}, regTellerResults.accepted, tabTellerResults.accepted, voterBBResults.accepted);
        TellerDetails rejected = new TellerDetails(new label {}, regTellerResults.rejected, tabTellerResults.rejected, voterBBResults.rejected);
        TellerDetails uncontactable = new TellerDetails(new label {}, regTellerResults.uncontactable, tabTellerResults.uncontactable, voterBBResults.uncontactable);

        return new RequestTellerResults(accepted, rejected, uncontactable);
    }

    private static HostResults requestTellers{}(Host{} []{} hosts, Requester{} pr, ElectionDetails{} details, TellerDetails{} tellers) {
        if (hosts == null || pr == null) return null;
        Host{}[] accepted = new Host[hosts.length];
        Host{}[] rejected = new Host[hosts.length];
        Host{}[] uncontactable = new Host[hosts.length];
        int acceptedCount = 0;
        int rejectedCount = 0;
        int uncontactableCount = 0;

        // go through each host and ask them to participate in the election.
        for (int i = 0; i < hosts.length; i++) {
            try {
                Host t = hosts[i];
                try {
                    if (pr.requestParticipation(t, i+1, details, tellers)) {
                        accepted[acceptedCount++] = t;
                    }
                    else {
                        rejected[rejectedCount++] = t;
                    }
                }
                catch (IOException e) {
                    uncontactable[uncontactableCount++] = t;
                }
                catch (IllegalArgumentException e) {
                    uncontactable[uncontactableCount++] = t;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        HostResults ret = new HostResults();
        ret.accepted = new Host[acceptedCount];
        ret.rejected = new Host[rejectedCount];
        ret.uncontactable = new Host[uncontactableCount];
        try {
            for (int i = 0; i < acceptedCount; i++) {
                ret.accepted[i] = accepted[i];
            }
            for (int i = 0; i < rejectedCount; i++) {
                ret.rejected[i] = rejected[i];
            }
            for (int i = 0; i < uncontactableCount; i++) {
                ret.uncontactable[i] = uncontactable[i];
            }
        }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        catch (NullPointerException imposs) { }
        return ret;
    }

    public static void confirmTellers{}(final ElectionDetails{} details, TellerDetails{} tellers, PrivateKey{} supPrivKey) 
    throws (IOException{}, IllegalArgumentException{})  {        
        // post to bb set of tellers
        if (details == null || details.electionID == null || tellers == null) return;
        BBClientUtil bb = new BBClientUtil(details.electionID);
        bb.post(TellerDetails.META, tellers, supPrivKey);

        confirmParticipation(tellers.registrationTellers, new RTRequester(), details, tellers);
        confirmParticipation(tellers.tabulationTellers, new TTRequester(), details, tellers);
        confirmParticipation(tellers.voterBBs, new VoterBBRequester(), details, tellers);
    }

    private static void confirmParticipation{}(Host{} []{} hosts, Requester{} pr, ElectionDetails{} details, TellerDetails{} tellers) 
    throws (IOException{}, IllegalArgumentException{})
    {
        if (hosts == null || pr == null) return;
        for (int i = 0; i < hosts.length; i++) {
            try {
                Host t = hosts[i];                    
                pr.confirmParticipation(t, i+1, details, tellers);
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException ignore) { }
        }
        
    }
    /**
     * Post a set of well-known ciphertexts to the bulletin board
     */
    public static void postWellKnownCiphertexts{}(final ElectionDetails{} details, PrivateKey{} supPrivKey) 
    throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return;

        // get the public key
        ElGamalPublicKey publicKey = ElectionUtil.retrieveTabTellerSharedPublicKey(details, null);
        if (publicKey == null) {
            throw new IOException("Cannot retrieve tab teller public key for election " + details.electionID.toString() + ". Have they been posted yet?");
        }

        // how many should we post?
        BallotDesign d = details.ballotDesign;        
        int count = 0;
        if (d != null) count = d.maxPossibleChoices();

        // generate the ciphertexts
        CiphertextList cl = null;
        try {
            cl = new CiphertextList(new label {}, CryptoUtil.factory().constructWellKnownCiphertexts(new label {}, publicKey, count), true);
        }
        catch (NullPointerException imposs) { }

        // and post them...
        if (cl != null) {
            BBClientUtil bb = new BBClientUtil(details.electionID);
            bb.post(CiphertextList.META, cl, supPrivKey);
        }
    }
}


class HostResults {
    Host{}[]{} accepted;
    Host{}[]{} rejected;
    Host{}[]{} uncontactable;
}

abstract class Requester {
    abstract boolean requestParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}); 
    abstract void confirmParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{});
}

class RTRequester extends Requester {
    boolean requestParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) { 
        RTClientUtil rtcu = new RTClientUtil();
        return rtcu.requestParticipation(t, ind, details, tellers);
    }
    void confirmParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) { 
        RTClientUtil rtcu = new RTClientUtil();
        rtcu.confirmParticipation(t, ind, details, tellers);
    }    
}

class TTRequester extends Requester {
    boolean requestParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) { 
        TTClientUtil ttcu = new TTClientUtil();
        return ttcu.requestParticipation(t, ind, details, tellers);
    }
    void confirmParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) { 
        if (details == null || details.electionID == null) return;
        TTClientUtil ttcu = new TTClientUtil();
        ttcu.confirmParticipation(t, ind, details, tellers);
        // ask each teller to start generating the public key
        ttcu.requestKeyGeneration(t, details.electionID);                    
    }
}
class VoterBBRequester extends Requester {
    boolean requestParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return false;
        BBClientUtil bbcu = new BBClientUtil(t, details.electionID.id);
        return bbcu.requestParticipation(details, tellers);
    }
    void confirmParticipation{}(Host{} t, int{} ind, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) { 
        if (details == null || details.electionID == null) return;
        BBClientUtil bbcu = new BBClientUtil(t, details.electionID.id);
        bbcu.confirmParticipation(ind, details, tellers);
    }
}
