/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.supervisor;

import civitas.common.*;
import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.tabulation.client.*;
import java.io.*;
import civitas.crypto.*;

/**
 * The class implements protocols for the supervisor to create a new election.
 */
public class StartStopElection {
    public static void startElection{}(final ElectionDetails{} details,
                                       PrivateKey{} supPrivKey) 
    throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return;
        BBClientUtil bb = new BBClientUtil(details.electionID);
        
        ElectionCache electionCache = new ElectionCache();
        electionCache.setElectionDetails(details);
        ElectionEvent e = new ElectionEvent(ElectionEvent.EVENT_KIND_START, 
                                            details.electionID, 
                                            newValidSequence(details, electionCache));
        bb.post(ElectionEvent.META, e, supPrivKey);
    }
    public static void stopElection{}(final ElectionDetails{} details, 
                                      PrivateKey{} supPrivKey) 
    throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return;
        BBClientUtil bb = new BBClientUtil(details.electionID);
        
        ElectionCache electionCache = new ElectionCache();
        electionCache.setElectionDetails(details);
        int numVoterBlocks = ElectionUtil.numberVoterBlocks(details, electionCache);

        ElectionEvent e = new ElectionEvent(ElectionEvent.EVENT_KIND_STOP, details.electionID, newValidSequence(details, electionCache));
        bb.post(ElectionEvent.META, e, supPrivKey);
        
        // now tell the voter BBs to stop
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(details, electionCache);
        if (tellerDetails != null && tellerDetails.voterBBs != null) {
            for (int i = 0; i < tellerDetails.voterBBs.length; i++) {
                try {
                    Host b = tellerDetails.voterBBs[i];
                    BBClientUtil bbcu = new BBClientUtil(b, details.electionID.id);
                    bbcu.closeBoard(details.electionID, supPrivKey, numVoterBlocks);
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
    }
    
    public static void startTabulation{}(final ElectionDetails{} details, PrivateKey{} supPrivKey) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null) throw new IllegalArgumentException();
        ElectionID electionID = details.electionID;
        if (electionID == null) throw new IllegalArgumentException();

        ElectionCache cache = new ElectionCache();
        // post all the voter boards that should be included.
        BoardsForTabulation bft = ElectionUtil.retrieveVoterBBContentCommitments(details, cache);
        if (bft == null) {
            throw new IOException("Unable to retrive the Voter BB content commitments. Try again later.");
        }
        BBClientUtil bb = new BBClientUtil(electionID);
        bb.post(BoardsForTabulation.META, bft, supPrivKey);

        // tell the tabulation tellers to start tabulation
        TellerDetails tellers = ElectionUtil.retrieveTellerDetails(details, cache);
        
        // go through each tabulation teller.
        if (tellers != null && tellers.tabulationTellers != null) {
            for (int i = 0; i < tellers.tabulationTellers.length; i++) {
                try {
                    Host t = tellers.tabulationTellers[i];
                    TTClientUtil ttcu = new TTClientUtil();

                    ttcu.tabulate(t, details.electionID);
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
        else {
            throw new IllegalArgumentException("Election " + (electionID==null?"null":electionID.toString()) + " does not have any tabulation tellers!");

        }        
    }
    
    public static void debugTabulation{}(final ElectionDetails{} details, PrivateKey{} supPrivKey) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null) throw new IllegalArgumentException();
        ElectionID electionID = details.electionID;
        if (electionID == null) throw new IllegalArgumentException();

        ElectionCache cache = new ElectionCache();

        // tell the tabulation tellers to start tabulation
        TellerDetails tellers = ElectionUtil.retrieveTellerDetails(details, cache);
        
        // go through each tabulation teller.
        if (tellers != null && tellers.tabulationTellers != null) {
            for (int i = 0; i < tellers.tabulationTellers.length; i++) {
                try {
                    Host t = tellers.tabulationTellers[i];
                    TTClientUtil ttcu = new TTClientUtil();

                    ttcu.debug(t, details.electionID);
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
        }
        else {
            throw new IllegalArgumentException("Election " + (electionID==null?"null":electionID.toString()) + " does not have any tabulation tellers!");

        }        
    }

    public static void finalizeElection{}(final ElectionDetails{} details, PrivateKey{} supPrivKey, String{} message, int{} tellerIndexForTallyState) throws (IOException{}, IllegalArgumentException{}) {
        // retrieve the tally for the teller specified.
        if (details == null) {
            throw new IllegalArgumentException("null election details");
        }
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(details, null);
        ElectionResults er = ElectionUtil.retrieveTTElectionResults(details.electionID, tellerDetails, tellerIndexForTallyState);
        if (er == null) {
            throw new IOException("Could not retrieve election results for teller " + tellerIndexForTallyState);
        }
        if (er.tally == null) {
            throw new IOException("No election results in the post for teller " + tellerIndexForTallyState);
        }
        finalizeElection(details, supPrivKey, message, er.tally);
    }
    public static void finalizeElection{}(final ElectionDetails{} details, PrivateKey{} supPrivKey, String{} message) throws (IOException{}, IllegalArgumentException{}) {
        finalizeElection(details, supPrivKey, message, null);
    }
    public static void finalizeElection{}(final ElectionDetails{} details, PrivateKey{} supPrivKey, String{} message, TallyStateFinal{} tally) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null || details.electionID == null) return;
        BBClientUtil bb = new BBClientUtil(details.electionID);
        
        ElectionCache electionCache = new ElectionCache();
        electionCache.setElectionDetails(details);
        ElectionEvent e = new ElectionEventFinalize(details.electionID, newValidSequence(details, electionCache), tally, message);
        bb.post(ElectionEvent.META, e, supPrivKey);

        // We could at this point close the BB, and tell it to stop accepting posts.
    }    

    private static int{} newValidSequence{}(final ElectionDetails{} details, 
                                            ElectionCache{} electionCache) 
    throws (IOException{}, IllegalArgumentException{}) {
        // determine a unique sequence number, greater than any previous sequence number of any valid event
        if (details == null) return 0;
        ElectionEvent[] validEvents = ElectionUtil.retrieveElectionEvents(details.electionID, electionCache);
        if (validEvents != null && validEvents.length > 0) {
            try {
                ElectionEvent e = validEvents[validEvents.length-1];
                if (e != null) {
                    return e.sequence + 1;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
        }
        return 0;
    }
    
}