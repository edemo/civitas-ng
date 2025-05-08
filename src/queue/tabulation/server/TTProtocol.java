/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.io.*;
import jif.util.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.common.*;
import civitas.crypto.*;

/**
 * Responsible for a single session of the tabulation teller protocol.
 * Receives inputs from a buffered reader, sends output over an output
 * stream.
 */
public class TTProtocol[principal tellerPubKey] extends Protocol implements Closure[tellerPubKey, {}] {
    private final PrivateKey  tellerPrivKey;
    private final BufferedReader[{}]  input;
    private final PrintStream[{}]  output;
    private final TTStore[tellerPubKey]  ttstore;
    private final TabulationCoordinator  coordinator;
    private final PrintStream[{*->*}]  debugLog;
    
    TTProtocol(PrivateKey  tellerPrivKey,
               InputStream[{}]  input, 
               OutputStream[{}]  output,
               TTStore[tellerPubKey]  ttstore,
               TabulationCoordinator  coordinator,
               PrintStream[{*->*}]  debugLog) {
        this.tellerPrivKey = tellerPrivKey;
        this.input = new BufferedReader[{}](new InputStreamReader[{}](input));
        this.output = new PrintStream[{}](output);
        this.ttstore = ttstore;
        this.coordinator = coordinator; 
        this.debugLog = debugLog;
        super();
    }
    
    public Object{} invoke{}()  
        try {
            run();
        }
        catch (IOException e) {
            return e;
        }
        return null;
    }
    public void run{}():{} throws IOException  
        BufferedReader[{}] input = this.input;
        PrintStream[{}] output = this.output;
        if (input == null || output == null || ttstore == null) return;
                
        String kind = input.readLine();
        if (kind == null) kind = "";
        
        if (kind.equalsIgnoreCase("REQUEST_PARTICIPATION")) {            
            ElectionDetails elecDetails = readInElectionDetails();
            TellerDetails tellerDetails = readInTellerDetails();
            int tellerIndex = -1;
            try {
                tellerIndex = Integer.parseInt(input.readLine());
            }
            catch (NumberFormatException e) {
                throw new IOException("expected an index number");
            }
            // decide whether to participate
            boolean result = false;
            if (elecDetails != null && tellerDetails != null) {
                result = decideParticipation(elecDetails, tellerDetails, tellerIndex);
            }
            output.println(result?"yes":"no");
            output.flush();
        }
        else if (kind.equalsIgnoreCase("CONFIRM_PARTICIPATION")) {
            ElectionDetails elecDetails = readInElectionDetails();
            TellerDetails tellerDetails = readInTellerDetails();
            int index = -1;
            try {
                index = Integer.parseInt(input.readLine());
            }
            catch (NumberFormatException e) { 
                throw new IOException(e.getMessage());
            }

            // Check that the election is one that we have already agreed to
            if (elecDetails != null && isAcceptedElection(elecDetails.electionID)) {
                // store the needed information about election and teller details.
                // store the election key, and the index of this tellers place in it.
                storeIndex(elecDetails, index);
                
                output.println(true);                
            }
            else {
                output.println(false);                                
            }
            
            output.flush();
        }
        else if (kind.equalsIgnoreCase("GEN_TABULATION_KEY")) {
            // start the protocol to form a shared public/private key pair 
            // with the other tellers            
            
            ElectionID electionID = readInElectionID();
            if (isAcceptedElection(electionID) && electionID != null) {
                if (coordinator != null) {
                    if (!coordinator.tabulationNotification(electionID)) {
                        // coordinator told us not to proceed.
                        return;
                    }
                }
                ElectionCache electionCache = electionCache(electionID);
                final ElectionDetails electionDetails = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
                TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
                int numTabTellers = numTabTellers(tellerDetails);
                genSharedKey(electionDetails, numTabTellers);
            }            
        }
        else if (kind.equalsIgnoreCase("TABULATE")) {
            ElectionID electionID = readInElectionID();
            if (isAcceptedElection(electionID) && electionID != null) {
                if (debugLog != null) debugLog.println("Calling tabulate from TTProtocol");
                if (coordinator != null) {
                    if (!coordinator.tabulationNotification(electionID)) {
                        // coordinator told us not to proceed.
                        if (debugLog != null) debugLog.println("Aborting, thread already exists for this election");
                        return;
                    }
                }
                ElectionCache electionCache = electionCache(electionID);
                final ElectionDetails electionDetails = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
                try {
                    tabulate(electionDetails, new BBClientUtil(electionID));
                }
                catch (IOException e) {
                    if (debugLog != null) debugLog.println("Error! " + e.getMessage());
                    throw e;
                }
            }
        }
        else if (kind.equalsIgnoreCase("DEBUG")) {
            ElectionID electionID = readInElectionID();
            if (isAcceptedElection(electionID) && electionID != null) {
                if (debugLog != null) debugLog.println("Calling debug from TTProtocol");
                if (coordinator != null) {
                    coordinator.debugNotification(electionID);
                }
            }            
        }

    }
        
    private void storeIndex{}(final ElectionDetails{} elecDetails_, int{} index_) 
     
        ElectionDetails elecDetails = endorse(elecDetails_, {tellerPubKey<-tellerPubKey});
        int index = endorse(index_, {tellerPubKey<-tellerPubKey});
        if (elecDetails == null || ttstore == null) return;

        ttstore.storeIndex(elecDetails.electionID, index);
    }
    private int{tellerPubKey<-tellerPubKey} retrieveIndex{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || ttstore == null) return -1;

        return ttstore.retrieveIndex(id);
    }
    private boolean{tellerPubKey<-tellerPubKey} isAcceptedElection{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || ttstore == null) return false;

        return ttstore.isAcceptedElection(id);
    }
    
    private void genSharedKey{}(final ElectionDetails{} electionDetails_, int{} numTabTellers_) 
    throws (IOException{tellerPubKey<-tellerPubKey})  
        IOException ioex = null;
        try {
            ElectionDetails electionDetails = endorse(electionDetails_, {tellerPubKey<-tellerPubKey});
            int numTabTellers = endorse(numTabTellers_, {tellerPubKey<-tellerPubKey});
            if (electionDetails != null && ttstore != null) {
                genSharedKey_(electionDetails, numTabTellers, ttstore.retrieveIndex(electionDetails.electionID));
            }
        }
        catch (IOException e) { 
            ioex = e;
        }
        IOException dioex = declassify(ioex, {tellerPubKey<-tellerPubKey});
        if (dioex != null) throw dioex;
    }
    private void genSharedKey_{tellerPubKey<-tellerPubKey}(final ElectionDetails{tellerPubKey<-tellerPubKey} electionDetails, 
            int{tellerPubKey<-tellerPubKey} numTabTellers,
            int{tellerPubKey<-tellerPubKey} tellerIndex):{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey} 
    throws IOException  
        if (electionDetails == null) return;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null || ttstore == null) return;
        ElGamalKeyPairShare keyshare = ttstore.retrieveKeyShare(electionID);
        int index = retrieveIndex(electionID);
        ElectionCache electionCache = electionCache(electionID, index);

        // all we need to do is generate a public/private key pair,
        // post a commitment, wait until everyone has posted commitments, 
        // and post the public key.
        IOException eioex = null;
        IOException ioex = null;
        
//      prepared to declassify whether or not the keyshare has been generated yet. 
        if (declassify(keyshare == null, {tellerPubKey<-tellerPubKey})) { 
            try {
                ElGamalParameters params = electionDetails.elGamalParameters;
                keyshare = CryptoUtil.factory().generateKeyPairShare(params);
            }
            catch (NullPointerException imposs) { }

            // store the private key
            ttstore.storeKeyShare(electionID, keyshare);
        }
        
        final principal p = (Principal)null;

        if (declassify(keyshare != null, {tellerPubKey<-tellerPubKey}) && electionID != null) {

            condition allCommitmentsPostedC;
            // DECLASSIFICATION POLICY
            TabTellerKeyShare{tellerPubKey->tellerPubKey allCommitmentsPostedC->_;tellerPubKey<-tellerPubKey} tabTellShare = null;
            try {
                tabTellShare = declassify(new TabTellerKeyShare(index, CryptoUtil.factory().constructKeyShare(keyshare)),
                                          {tellerPubKey->tellerPubKey;tellerPubKey<-tellerPubKey} to {tellerPubKey->tellerPubKey allCommitmentsPostedC->_;tellerPubKey<-tellerPubKey});
            } catch (NullPointerException imposs) { }

            // post the commitment
            declassify({tellerPubKey<-tellerPubKey}) {
                try {
                    try {
                        BBClientUtil bb = new BBClientUtil(electionID);
                        TabTellerKeyShareCommitment com = declassify(tabTellShare, {tellerPubKey<-tellerPubKey}).constructCommitment(new label {});
                        bb.post(TabTellerKeyShareCommitment.META, com, tellerPrivKey);
                        TTTabulationUtil[p,p,p].broadcast(electionDetails, electionCache, tellerIndex);
                    }
                    catch (IllegalArgumentException e) { 
                        throw new IOException(e.getMessage());
                    }        
                    catch (NullPointerException imposs) { }
                }
                catch (IOException e) {
                    ioex = e;
                }
                eioex = endorse(ioex, {tellerPubKey<-tellerPubKey});
                if (eioex != null) throw eioex;
            }
            
            
            // wait until all commitments are posted
            declassify({tellerPubKey<-tellerPubKey}) {
                boolean allCommitmentsPosted = false;
                int retriesLeft = TTTabulationUtil[p,p,p].maxRetries(numTabTellers);
                try {
                    while (!allCommitmentsPosted && retriesLeft-- > 0) {
                        allCommitmentsPosted = allTabTellerKeyShareCommitmentsPosted(electionID, electionCache, numTabTellers);
                        if (retriesLeft > 0 && !allCommitmentsPosted) {
                            // sleep for a bit
                            if (debugLog != null) debugLog.println("Sleeping for all tab teller shared key commitments. Retries left = " + retriesLeft);
                            jif.runtime.Runtime[null].sleep(TTTabulationUtil[p,p,p].sleepTime(numTabTellers));
                        }
                    }
                }
                catch (IOException e) {
                    ioex = e;
                }
                eioex = endorse(ioex, {tellerPubKey<-tellerPubKey});
                if (eioex != null) throw eioex;

                allCommitmentsPostedC = endorse(allCommitmentsPosted, {tellerPubKey<-tellerPubKey}); 
                if (allCommitmentsPostedC) {                    
                    // post the public key (and proof)
                    try {
                        TabTellerKeyShare tabTellShareD = declassify(tabTellShare, 
                                {tellerPubKey->tellerPubKey allCommitmentsPostedC->_;tellerPubKey<-tellerPubKey} to {} using allCommitmentsPostedC);
                        try {
                            BBClientUtil bb = new BBClientUtil(electionID);
                            bb.post(TabTellerKeyShare.META, tabTellShareD, tellerPrivKey);
                            TTTabulationUtil[p,p,p].broadcast(electionDetails, electionCache, tellerIndex);
                        }
                        catch (IllegalArgumentException e) { 
                            throw new IOException(e.getMessage());
                        }        
                    }
                    catch (IOException e) {
                        ioex = e;
                    }
                    catch (UnsatisfiedConditionException imposs) {
                    }
                    eioex = endorse(ioex, {tellerPubKey<-tellerPubKey});
                    if (eioex != null) throw eioex;
                }
            }
        }        
    }
    static int numTabTellers(TellerDetails tellerDetails) {
        if (tellerDetails != null && tellerDetails.tabulationTellers != null) {
            return tellerDetails.tabulationTellers.length;
        }
        return 0;
    }

    private boolean allTabTellerKeyShareCommitmentsPosted{}(ElectionID{} electionID, ElectionCache{} electionCache, int{} numTabTellers) 
    throws (IOException{})  
        for (int i = 0; i < numTabTellers; i++) {
            if (ElectionUtil.retrieveTabTellerSharedPublicKeyCommitment(electionID, electionCache, i+1) == null) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Because the tabulation code is idempotent, and robust to being
     * called multiple times, the tabulation teller is ok with auto-endorsing
     * the method.
     */
    private void tabulate{}(final ElectionDetails{} electionDetails_, 
                            BBClientUtil{} bb_) 
    throws (IOException{})  
        // prepared to endorse the election details since we will only run the
        // tabulation if the integrity is sufficiently high.
        BBClientUtil bb = endorse(bb_, {tellerPubKey<-tellerPubKey});
        final ElectionDetails electionDetails = endorse(electionDetails_, {tellerPubKey<-tellerPubKey});
        if (electionDetails == null) return;
        // TODO: The tellers principal should be a principal 
        // that all and only the tabulation tellers can act for.
        // This is still to be implemented. Instead we conservatively use
        // the bottom principal, null, who all the tellers can act for.
        final principal tellers = null; 
        final TTTabulation[tellerPubKey, electionDetails.supervisor, tellers] ttt = new TTTabulation[tellerPubKey, electionDetails.supervisor, tellers](tellerPrivKey, ttstore, bb, debugLog);
        ttt.tabulate(electionDetails);
    }
    
    private ElectionDetails{} readInElectionDetails{}() throws (IOException{}) {
        try {
            return ElectionDetails.fromXML(new label {}, input);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    private TellerDetails{} readInTellerDetails{}() throws (IOException{}) {
        try {
            return TellerDetails.fromXML(new label {}, input);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
    }
    private ElectionID{} readInElectionID{}() throws (IOException{}) {
        try {
            return ElectionID.fromXML(new label {}, input);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }        
    }

    /**
     * Decide whether or not teller p will participate in an election
     */
    protected boolean{} decideParticipation{}(final ElectionDetails{} elecDetails_, TellerDetails{} tellers_, int{} tellerIndex_) 
     
        ElectionDetails elecDetails = endorse(elecDetails_, {tellerPubKey<-tellerPubKey});
        TellerDetails tellers = endorse(tellers_, {tellerPubKey<-tellerPubKey});
        int tellerIndex = endorse(tellerIndex_, {tellerPubKey<-tellerPubKey});
        if (elecDetails == null) return false;

        // check that the election status is appropriate
        boolean decision = true;
        try {
            ElectionCache electionCache = electionCache(elecDetails.electionID, tellerIndex);
            if (ElectionUtil.retrieveElectionStatus(tellerPubKey, elecDetails.electionID, electionCache) != ElectionUtil.STATUS_DEFINED) { 
                decision = false;
            }
        }
        catch (IOException e) {
            // couldn't contact the BB server. Seems like a bad deal...
            decision = false;
        }
                
        
        if (endorse(decision, {tellerPubKey<-tellerPubKey}) && ttstore != null) {
            ttstore.storeAcceptance(elecDetails.electionID);
        }
        return decision;
    }
    
    private ElectionCache{tellerPubKey<-tellerPubKey} electionCache{}(ElectionID{} id_) 
     
        return electionCache(id_, retrieveIndex(id_)); 
    }

    private ElectionCache{tellerPubKey<-tellerPubKey} electionCache{}(ElectionID{} id_, int{} tellerIndex_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        int tellerIndex = endorse(tellerIndex_, {tellerPubKey<-tellerPubKey});
        if (ttstore == null || id == null) return null;

        return ttstore.electionCache(id, tellerIndex);
    }
}