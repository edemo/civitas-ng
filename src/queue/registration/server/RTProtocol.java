/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import java.io.*;
import jif.util.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.common.*;
import civitas.crypto.*;


/**
 * Responsible for a single session of the registration teller protocol.
 * Receives inputs from a buffered reader, sends output over an output
 * stream.
 */
public class RTProtocol[principal tellerPubKey] extends Protocol implements Closure[tellerPubKey, {}] {
    public static final int  TELLER_NONCE_LENGTH = 64;

    private final PrivateKey  tellerPrivKey;
    private final BufferedReader[{}]  input;
    private final PrintStream[{}]  output;
    private final RTStore[tellerPubKey]  rtstore;

    
    RTProtocol(PrivateKey  tellerPrivKey,
               InputStream[{}]  input, 
               OutputStream[{}]  output,
               RTStore[tellerPubKey]  rtstore) {
        this.tellerPrivKey = tellerPrivKey;
        this.input = new BufferedReader[{}](new InputStreamReader[{}](input));
        this.output = new PrintStream[{}](output);
        this.rtstore = rtstore;
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
        if (input == null || output == null || rtstore == null) return;
                
        String kind = input.readLine();
        if (kind == null) kind = "";

        if (kind.equalsIgnoreCase("REQUEST_PARTICIPATION")) {            
            ElectionDetails elecDetails = readInElectionDetails();
            TellerDetails tellerDetails = readInTellerDetails();
            // read in the index
            int index = -1;
            try {
                index = Integer.parseInt(input.readLine());
            }
            catch (NumberFormatException e) { 
                throw new IOException(e.getMessage());
            }
            
            // decide whether to participate
            boolean result = false;
            if (elecDetails != null && tellerDetails != null) {
                result = decideParticipation(elecDetails, tellerDetails, index);
            }
            output.println(result?"yes":"no");
            output.flush();
        }
        else if (kind.equalsIgnoreCase("CONFIRM_PARTICIPATION")) {
            ElectionDetails elecDetails = readInElectionDetails();
            TellerDetails tellerDetails = readInTellerDetails();

            // read in the index
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
        else if (kind.equalsIgnoreCase("NEW_REGISTRATION")) {
            ElectionID electionID = readInElectionID();
            // optional param: message number.
            String messageID = input.readLine();
            registration(electionID, messageID, false, output);
        }
        else if (kind.equalsIgnoreCase("ALL_REGISTRATIONS")) {
            ElectionID electionID = readInElectionID();
            registration(electionID, null, true, null);
        }
        else if (kind.equalsIgnoreCase("RETRIEVE_CAPABILITIES")) {
            try {
                retrieveCapabilities();
            }
            catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage());
            }
            catch (CryptoException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
    
    private void retrieveCapabilities{}() throws (IOException{}, IllegalArgumentException{}, CryptoException{})  
        CryptoFactory factory = CryptoUtil.factory();
        if (factory == null)  throw new IllegalArgumentException("couldn't get cryptofactory");
        if (rtstore == null)  throw new IllegalArgumentException("couldn't get rtstore");

        // STEP 1: voter generates nonce NV
        // STEP 2: receive ENC(electionID, Votername, NV; RegPubKey)
        String wire2 = protocolInputString(input);        
        PublicKeyCiphertext cipher2 = factory.publicKeyCiphertextFromXML(new label {}, new BufferedReader[{}](new StringReader[{}](wire2)));        
        String plaintext2 = null;
        try {
            plaintext2 = factory.publicKeyDecrypt(tellerPrivKey, cipher2).toString();
        }
        catch (NullPointerException ignore) { }
                
        BufferedReader[{}] r = new BufferedReader[{}](new StringReader[{}](plaintext2));
        ElectionID electionID = ElectionID.fromXML(new label {}, r);
        String voterName = r.readLine();
        String NV = r.readLine();
        
        if (electionID == null) {
            // no details posted yet
            throw new IOException("Bad election ID");
        }
        ElectionCache electionCache = electionCache(electionID);
        ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
        if (details == null) {
            // no details posted yet
            throw new IOException("Could not retrieve election details");
        }
        
        VoterDetails vd = retrieveVoterDetails(electionID, voterName);
                
        // Make sure that the election is in a suitable state to retrieve capabilities.
                
        if (vd == null || !isElectionReady(details, vd.voterBlock, electionCache)) {
            // election not in suitable state to give out credential shares.
            // inform the voter, and quit the protocol
            protocolOutputString(output, "ELECTION NOT READY");   
            return;
        }
        
        ElGamalPublicKey tabTellerSharedPK = ElectionUtil.retrieveTabTellerSharedPublicKey(details, electionCache);
        if (tabTellerSharedPK == null) {
            // no tab teller key posted yet
            throw new IOException("Could not retrieve tab teller shared public key");
        }

        // STEP 3: generate nonce NR and session key k
        String NR = factory.freshNonceBase64(new label {}, TELLER_NONCE_LENGTH);
        SharedKey k = factory.generateSharedKey(details.sharedKeyLength);
        if (k == null) {
            // couldn't generate a key!
            throw new IOException("Could not genearte a shared key");
        }
        
        // STEP 4: send Enc(electionID, tellerIndex, NR, NV, k; voterPubKey)
        PublicKey voterPK = retrieveVoterPublicKey(electionID, voterName);
        if (voterPK == null) {
            // no appropriate voter
            return; 
        }
        ElGamalPublicKey voterEGPK = retrieveVoterEGPublicKey(electionID, voterName);
        if (voterEGPK == null) {
            // no appropriate voter
            return; 
        }
        
        int index = retrieveIndex(electionID);
               
        StringWriter[{}] ma = new StringWriter[{}]();
        PrintWriter[{}] pw = new PrintWriter[{}](ma);
        pw.print(electionID.host);
        pw.print("\n");
        pw.print(Integer.toString(electionID.port, 16));
        pw.print("\n");
        pw.print(electionID.id);
        pw.print("\n");
        pw.print(Integer.toString(index, 16));
        pw.print("\n");
        pw.print(NR);
        pw.print("\n");
        pw.print(NV);
        pw.print("\n");
        k.toWire(new label {}, pw);
        PublicKeyMsg msg4 = factory.publicKeyMsg(ma.toString());
        PublicKeyCiphertext wire4 = factory.publicKeyEncrypt(voterPK, msg4);
        
        if (wire4 != null) {
            StringWriter[{}] sb = new StringWriter[{}]();
            wire4.toXML(new label {}, new PrintWriter[{}](sb));

            protocolOutputString(output, sb.toString());        
        }
                        
        // STEP 5: voter verifies NV, RegPubKey and electionID
        // STEP 6: receive NR'
        String NRprime = protocolInputString(input);
        
        // STEP 7: verify NRprime
        if (NRprime == null || !NRprime.equals(NR)) {
            // voter sent back the wrong nonce
            return;
        }
        
        // STEP 8: send Enc(capabilities and proofs; k)
        // Ideally, the proof should be private to the registration teller at this point,
        // and we declassify it here.
        VoterCapabilitySharesAndProof proof = constructCapabilitySharesAndProof(details, tabTellerSharedPK, vd, voterEGPK);
        String m8 = null;
        if (proof != null) {
            StringWriter[{}] sb = new StringWriter[{}]();
            proof.toXML(new PrintWriter[{}](sb));
            m8 = sb.toString();
        }
        SharedKeyMsg msg8 = factory.sharedKeyMsg(m8);
        SharedKeyCiphertext cipher8 = factory.sharedKeyEncrypt(k, msg8);
        if (cipher8 != null) {
            StringWriter[{}] sb = new StringWriter[{}]();
            cipher8.toXML(new label {}, new PrintWriter[{}](sb));
            protocolOutputString(output, sb.toString());
        }        
    }
    
    /**
     * Is the election ready
     * @return
     */
    private boolean{} isElectionReady{}(ElectionDetails{} elecDetails, int{} voterBlock, ElectionCache{} electionCache) 
    throws (IOException{}, CryptoException{}) 
             
        // Election status must be started, and all registration tellers must have
        // posted their (encrypted) capability shares for the voter's block.
        
        if (elecDetails == null) return false;
                
        if (ElectionUtil.retrieveElectionStatus(tellerPubKey, elecDetails.electionID, electionCache) != ElectionUtil.STATUS_STARTED) {
            return false;
        }
        // now check that all registration tellers have posted for the voter block.
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(elecDetails, electionCache);
        int numRegTellers = 0;
        try {
            numRegTellers = tellerDetails.registrationTellers.length;
        }
        catch (NullPointerException ignore) { 
            return false;
        }

        for (int i = 0; i < numRegTellers; i++) {
            if (electionCache != null) {
                if (electionCache.hasElectoralRollCapabilitySharesForVoterBlock(voterBlock, i+1)) {
                    // already have it in the cache
                    continue;
                }
            }
            // not in the cache, try fetching from the board.
            ElectoralRollCapabilityShares ercs = 
                 ElectionUtil.retrieveElectoralRollCapabilitySharesForVoterBlock(electionCache, 
                                                                                 elecDetails, 
                                                                                 tellerDetails, 
                                                                                 i+1,
                                                                                 voterBlock);
            if (ercs == null) {                                                                                 
                // not on the board, so not yet posted.
                return false;
            }
            
            // check the shares to make sure the proofs are right.
            VoterEncCapabilityShares[] roll = ercs.roll;
            try {
                for (int j = 0; j < roll.length; j++) {
                    ElGamalSignedCiphertext{}[] voterShares = roll[j].encCapabilityShares;

                    for (int k = 0; k < voterShares.length; k++) {
                        if (!CryptoUtil.factory().elGamalVerify(elecDetails.elGamalParameters, voterShares[k], 
                                                                CryptoUtil.factory().messageDigest((i+1) + roll[j].name))) {
                            // uh oh, one of the registration tellers cheated!
                            throw new CryptoException("Capability share " + (k+1) + 
                                                      " of " + voterShares.length + 
                                                      " posted by teller " + (i+1) + 
                                                      " for voter " + roll[j].name + " is invalid");
                        }
                    }
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { } 
        }            
            
        return true;
    }
    
    /**
     * Process registration for election electionID. If messageID is non-null
     * then only that single message will be processed. If messageID is null,
     * and doEntireBoard is false, then only messages posted since the teller
     * last processed registration will be processed. If If messageID is null,
     * and doEntireBoard is true, then all messages on the board will be
     * processed. 
     */
    private void registration{}(ElectionID{} electionID, 
            String{} messageID, 
            boolean{} doEntireBoard, 
            PrintStream[{}]{} output) 
    throws (IOException{})  
        if (rtstore == null || electionID == null || !isAcceptedElection(electionID)) {
            if (output != null) output.println(false);
            return;
        }
        Long fromTime = null;
        Long toTime = null;
        if (!doEntireBoard && messageID != null && messageID.length() > 0) {
            try {
                long id = Long.parseLong(messageID);
                fromTime = new Long(id-1);
                toTime = new Long(id+1);
            }
            catch (NumberFormatException failSilent) { }
        }
        
        // find the new postings to consider.
        if (!doEntireBoard && fromTime == null) {
            fromTime = retrieveLastMessageProcessed(electionID);
        }
        
        ElectionCache electionCache = electionCache(electionID);
        final ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
        if (details == null) {
            if (output != null) output.println(false);
            return;
        }

        int numVoterBlocks = ElectionUtil.numberVoterBlocks(details, electionCache);
        numVoterBlocks = numVoterBlocks<0?0:numVoterBlocks; // make sure it is positive
        
        BBClientUtil bb = new BBClientUtil(electionID);
            
        // retrieve everything from fromTime to toTime
        List[{}] l = null;
        try {
            l = bb.retrieveParams(XMLDeserializers.ElectoralRoll(), ElectoralRoll.META, fromTime, toTime);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage()); 
        }
        if (l == null || l.isEmpty()) { 
            if (output != null) output.println(false);
            return;
        }
        
        int tellerIndex = retrieveIndex(electionID);
        
        ElGamalPublicKey tabTellerSharedPK = ElectionUtil.retrieveTabTellerSharedPublicKey(details, electionCache);

        // process each one
        long lastTimestamp = -1;
        for (int i = 0; i < l.size(); i++) {
            BBPost post = null;
            try {
                post = (BBPost)l.get(i);
            }
            catch (ClassCastException imposs) { }
            catch (IndexOutOfBoundsException imposs) { }
            if (post == null) continue;
            
            if (post.timestamp > lastTimestamp) {
                lastTimestamp = post.timestamp;
            }
            
            // parse and validate
            try {
                ElectoralRoll er = (ElectoralRoll)post.msg;
                // check the signature of er
                if (er != null && er.roll != null && post.verify(details.registrar)) {
                    // the electoral roll is ok!
                    VoterEncCapabilityShares{}[][] caps = new VoterEncCapabilityShares[numVoterBlocks][er.roll.length];
                    int{}[] capsCount = new int[numVoterBlocks];
                    
                    for (int j = 0; j < er.roll.length; j++) {
                        VoterDetails vd = null;
                        try {
                            vd = er.roll[j];
                        }
                        catch (ArrayIndexOutOfBoundsException imposs) { }
                        VoterEncCapabilityShares vc = processVoter(tabTellerSharedPK, details, vd, bb);
                        if (vc != null) {
                            // add it to the appropriate array for posting to the BB
                            
                            try {
                                int index = capsCount[vc.voterBlock];
                                caps[vc.voterBlock][index] = vc;
                                capsCount[vc.voterBlock]++;
                            }
                            catch (ArrayIndexOutOfBoundsException imposs) { }
                            catch (NullPointerException imposs) { }
                        }
                    }
                    // go through each voter block and post a message.
                    for (int voterBlock = 0; voterBlock < numVoterBlocks; voterBlock++) {
                        int count = -1;                        
                        VoterEncCapabilityShares{}[] cs = null;
                        try {
                            count = capsCount[voterBlock];
                            cs = caps[voterBlock];
                        }
                        catch (ArrayIndexOutOfBoundsException imposs) { }

                        if (cs != null && count != cs.length) {
                            // shrink the array
                            VoterEncCapabilityShares{}[] newcs = new VoterEncCapabilityShares[count<0 ? 0 : count];
                            for (int j = 0; j < count; j++) {
                                try {
                                    newcs[j] = cs[j];
                                }
                                catch (ArrayIndexOutOfBoundsException imposs) { }
                            }
                            cs = newcs;
                        }
                        // post the VoterEncCapabilityShares to the BB
                        // we always post this message, and when retrieving
                        // a mix, we ensure that only a single copy of the
                        // capabilities go into the mix.
                        if (cs != null && cs.length > 0) {
                            ElectoralRollCapabilityShares erc = new ElectoralRollCapabilityShares(cs, tellerIndex, voterBlock);
                            try {
                                bb.post(ElectoralRollCapabilityShares.meta(tellerIndex, voterBlock), erc, tellerPrivKey);                
                            }
                            catch (IllegalArgumentException e) {
                                throw new IOException(e.getMessage());
                            }
                        }
                    }
                }
            }
            catch (ClassCastException ignore) { } 
        }        
            
        // store last message processed.
        if (lastTimestamp >= 0) {
            storeLastMessageProcessed(electionID, lastTimestamp);
        }
        if (output != null) output.println(true);        
    }

    private VoterEncCapabilityShares{} processVoter{}(ElGamalPublicKey{} tabTellerSharedPK, 
            ElectionDetails{} electionDetails,
            VoterDetails{} vd, 
            BBClientUtil{} bb)  
    throws (IOException{})
     
        if (vd == null || electionDetails == null || rtstore == null) return null;

        // have we produced capabilities for vd before?
        if (!voterCapabilitiesGenerated(electionDetails.electionID, vd)) {
            // Generate and store capabilities and voter block for vd
            generateCapabilities(electionDetails, vd); 
                 
            // This is the point that we could email the
            // capability shares to the voter.
        }
        
        // we have already generated capabilities for this voter
        return constructVoterEncCapabilityShares(electionDetails, 
              tabTellerSharedPK, vd, 
              retrieveVoterCapabilityShares(electionDetails.electionID, vd.name),
              retrieveVoterEncryptFactors(electionDetails.electionID, vd.name));                    
    }
    
    private void generateCapabilities{}(final ElectionDetails{} electionDetails_, VoterDetails{} vd_)
     tellerPubKey<-tellerPubKey}) 
    {
        if (rtstore == null) return;
        ElectionDetails electionDetails = endorse(electionDetails_, {tellerPubKey<-tellerPubKey});
        VoterDetails vd = endorse(vd_, {tellerPubKey<-tellerPubKey});
        if (electionDetails == null || vd == null) return;
        
        // generate the capabilities
        BallotDesign bd = electionDetails.ballotDesign;
        if (bd == null) return;
        int size = bd.votesProducedPerBallot();
        ElGamalReencryptFactor{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey}[] encryptFactors = new ElGamalReencryptFactor[size<0?0:size];
        
        for (int i = 0; i < size; i++) {
            try {
                encryptFactors[i] = CryptoUtil.factory().generateElGamalReencryptFactor(electionDetails.elGamalParameters);
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
        }
        // store capabilities and encrypt factors
        VoterCapabilityShares[tellerPubKey] caps = new VoterCapabilityShares[tellerPubKey](size, electionDetails.elGamalParameters);
        rtstore.storeVoterCapabilityShares(electionDetails.electionID, vd.name, caps);
        rtstore.storeVoterEncryptFactors(electionDetails.electionID, vd.name, encryptFactors);

        
        // store voter public key
        rtstore.storeVoterDetails(electionDetails.electionID, vd.name, vd);
    }


    /**
     * Construct the encryptions of a voters capability shares.
     * These will be posted to the bulletin board.
     */
    private VoterEncCapabilityShares{} constructVoterEncCapabilityShares{}(final ElectionDetails{} electionDetails_, 
            ElGamalPublicKey{} tabTellerSharedPK_, VoterDetails{} vd_, 
                         VoterCapabilityShares[tellerPubKey]{} voterCapabilityShares_,
                         ElGamalReencryptFactor{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey}[]{} encryptFactors_) 
                          
        ElectionDetails electionDetails = endorse(electionDetails_, {tellerPubKey<-tellerPubKey}); 
        ElGamalPublicKey tabTellerSharedPK = endorse(tabTellerSharedPK_, {tellerPubKey<-tellerPubKey});
        VoterDetails vd = endorse(vd_, {tellerPubKey<-tellerPubKey});
        final VoterCapabilityShares[tellerPubKey] voterCapabilityShares = endorse(voterCapabilityShares_, {tellerPubKey<-tellerPubKey});
        ElGamalReencryptFactor{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey}[] encryptFactors = endorse(encryptFactors_, {tellerPubKey<-tellerPubKey});
                
        int size = 0;
        if (voterCapabilityShares!=null && voterCapabilityShares.caps!=null) {
            size = voterCapabilityShares.caps.length;
            voterCapabilityShares.deliveryRequested = true;
        }
        
        // the delivery has been requested.
        
        ElGamalSignedCiphertext{}[] encCapabilities = new ElGamalSignedCiphertext[size];
        if (vd == null || electionDetails == null) return null;
        String voterName = vd.name;
                
        int tellerIndex = retrieveIndex(electionDetails.electionID);

        for (int i = 0; i < size; i++) {
            VoteCapabilityShare cap = null;
            try {
                cap = declassify(voterCapabilityShares.caps[i], 
                                                     {tellerPubKey->[tellerPubKey /voterCapabilityShares.delivered/ *]voterCapabilityShares.deliveryRequested-> _; tellerPubKey<-tellerPubKey} 
                       to {} using voterCapabilityShares.deliveryRequested);
                ElGamalReencryptFactor factor = declassify(encryptFactors[i], {});
                // needs to be a signed encryption, i.e. prove we know the
                // plaintext.
                encCapabilities[i] = CryptoUtil.factory().elGamalSignedEncrypt(tabTellerSharedPK, cap, factor, 
                                                      CryptoUtil.factory().messageDigest(tellerIndex + voterName));                
            }
            catch (UnsatisfiedConditionException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { }
        }
        
        if (voterCapabilityShares!=null && voterCapabilityShares.caps!=null) {
            // stop the capabilities from being declassified.
            voterCapabilityShares.deliveryRequested = false;
        }

        int voterBlock = voterBlock(electionDetails, vd);
        
        // store the encrypt factors
        
        return new VoterEncCapabilityShares(tellerIndex, voterName, voterBlock, encCapabilities);
    }
    
    private VoterCapabilitySharesAndProof{} constructCapabilitySharesAndProof{}(final ElectionDetails{} electionDetails_, 
            ElGamalPublicKey{} tabTellerSharedPK_, 
            VoterDetails{} vd_, 
            ElGamalPublicKey{} voterEGPubKey_)
    where caller(tellerPubKey), 
          endorse({tellerPubKey<-tellerPubKey})  {
        ElectionDetails electionDetails = endorse(electionDetails_, {tellerPubKey<-tellerPubKey});  
        ElGamalPublicKey tabTellerSharedPK = endorse(tabTellerSharedPK_, {tellerPubKey<-tellerPubKey});
        VoterDetails vd = endorse(vd_, {tellerPubKey<-tellerPubKey});
        ElGamalPublicKey voterEGPubKey = endorse(voterEGPubKey_, {tellerPubKey<-tellerPubKey});
        
        if (vd == null || electionDetails == null) return null;
        String voterName = vd.name;
        ElectionID electionID = electionDetails.electionID;
        

        // get the capabilities
        final VoterCapabilityShares[tellerPubKey] caps = retrieveVoterCapabilityShares(electionID, voterName);
        ElGamalReencryptFactor{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey}[] factors = retrieveVoterEncryptFactors(electionID, voterName);
        int voterBlock = voterBlock(electionDetails, vd);
        
       if (caps == null || caps.caps == null || factors == null || tabTellerSharedPK == null) return null;
       
       // enable the declassification of capabilities.
       // For clarity, we could perhaps move this closer to the authentication of the voter?
       caps.deliveryRequested = true;

       VoteCapabilityShare{}[] capsUsable = new VoteCapabilityShare[caps.caps.length];
       ElGamalReencryptFactor{}[] proofFactors = new ElGamalReencryptFactor[caps.caps.length];
       ElGamalProofDVR{}[] proofs = new ElGamalProofDVR[caps.caps.length];
       for (int i = 0; i < caps.caps.length; i++) {
           try {
               try {
                   // The capabilities are downgraded all the way to publically readable.
                   // If the Jif language had the ability to reason more precisely about
                   // cryptography, we could maybe ensure that the declassification
                   // was to the principal represented by the voter's public key.
                  capsUsable[i] = declassify(caps.caps[i], 
                                   {tellerPubKey->[tellerPubKey /caps.delivered/ *]caps.deliveryRequested-> _; tellerPubKey<-tellerPubKey} 
                                   to {} using caps.deliveryRequested);
               }
               catch (ArrayIndexOutOfBoundsException imposs) { }

               ElGamalReencryptFactor rprime = CryptoUtil.factory().generateElGamalReencryptFactor(tabTellerSharedPK.getParams());
               proofFactors[i] = rprime;
               ElGamalCiphertext eprime = CryptoUtil.factory().elGamalEncrypt(tabTellerSharedPK, capsUsable[i], rprime);
               
               ElGamalReencryptFactor factori = declassify(factors[i], {});
               ElGamalCiphertext e = CryptoUtil.factory().elGamalEncrypt(tabTellerSharedPK, capsUsable[i], factori);
               ElGamalProofDVR proof = CryptoUtil.factory().constructElGamalProofDVR(tabTellerSharedPK, voterEGPubKey, e, eprime, factori, rprime);
               proofs[i] = proof;
               
           }
           catch (ArrayIndexOutOfBoundsException imposs) { }
           catch (UnsatisfiedConditionException imposs) { }
           catch (NullPointerException ignore) { }
       }
       
       // the capabilities have been delivered, and can now be erased.
       caps.delivered = true;
              
       return new VoterCapabilitySharesAndProof(capsUsable.clone(), proofFactors.clone(), proofs.clone(), voterBlock);
    }

    
    private int{} voterBlock{}(final ElectionDetails{} electionDetails, VoterDetails{} vd)  
        if (electionDetails == null || vd == null) return 0;
        
        try {
            int numVoterBlocks = ElectionUtil.numberVoterBlocks(electionDetails, electionCache(electionDetails.electionID));
            return vd.voterBlock % numVoterBlocks;
        }
        catch (ArithmeticException ignore) {
            return 0;
        }
        catch (IOException e) {
            return vd.voterBlock;
        }
    }
    private ElectionCache{tellerPubKey<-tellerPubKey} electionCache{}(ElectionID{} id_) 
     
        if (rtstore == null) return null;
        return electionCache(id_, retrieveIndex(id_)); 
    }
    private ElectionCache{tellerPubKey<-tellerPubKey} electionCache{}(ElectionID{} id_, int{} tellerIndex_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        int tellerIndex = endorse(tellerIndex_, {tellerPubKey<-tellerPubKey});
        if (rtstore == null || id == null) return null;

        return rtstore.electionCache(id, tellerIndex);
    }
    private boolean{tellerPubKey<-tellerPubKey} voterCapabilitiesGenerated{}(ElectionID{} id_, VoterDetails{} vd_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        VoterDetails vd = endorse(vd_, {tellerPubKey<-tellerPubKey});
        if (id == null || vd == null || rtstore == null) return false;

        return rtstore.voterCapabilitiesGenerated(id, vd.name);
    }
    private void storeIndex{}(final ElectionDetails{} elecDetails_, int{} index_) 
     
        ElectionDetails elecDetails = endorse(elecDetails_, {tellerPubKey<-tellerPubKey});
        int index = endorse(index_, {tellerPubKey<-tellerPubKey});
        if (elecDetails == null || rtstore == null) return;

        rtstore.storeIndex(elecDetails.electionID, index);
    }
    
    private int{tellerPubKey<-tellerPubKey} retrieveIndex{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || rtstore == null) return -1;

        return rtstore.retrieveIndex(id);
    }
    private VoterDetails{tellerPubKey<-tellerPubKey} retrieveVoterDetails{}(ElectionID{} id_, String{} voterName_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        String voterName = endorse(voterName_, {tellerPubKey<-tellerPubKey});
        if (id == null || rtstore == null) return null;

        return rtstore.retrieveVoterDetails(id, voterName);
    }
    private boolean{tellerPubKey<-tellerPubKey} isAcceptedElection{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || rtstore == null) return false;

        return rtstore.isAcceptedElection(id);
    }
    private void storeLastMessageProcessed{}(ElectionID{} id_, long{} index_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || rtstore == null) return;
        long index = endorse(index_, {tellerPubKey<-tellerPubKey});

        rtstore.storeLastMessageProcessed(id, index);
    }
    private Long{tellerPubKey<-tellerPubKey} retrieveLastMessageProcessed{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        if (id == null || rtstore == null) return null;

        return rtstore.retrieveLastMessageProcessed(id);
    }
    private ElGamalPublicKey{tellerPubKey<-tellerPubKey} retrieveVoterEGPublicKey{}(ElectionID{} id_, String{} voterName_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        String voterName = endorse(voterName_, {tellerPubKey<-tellerPubKey});
        if (id == null || voterName == null || rtstore == null) return null;

        return rtstore.retrieveVoterEGPublicKey(id, voterName);
    }
    private PublicKey{tellerPubKey<-tellerPubKey} retrieveVoterPublicKey{}(ElectionID{} id_, String{} voterName_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        String voterName = endorse(voterName_, {tellerPubKey<-tellerPubKey});
        if (id == null || voterName == null || rtstore == null) return null;

        return rtstore.retrieveVoterPublicKey(id, voterName);
    }
    private VoterCapabilityShares[tellerPubKey]{tellerPubKey<-tellerPubKey} retrieveVoterCapabilityShares{}(ElectionID{} id_, String{} voterName_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        String voterName = endorse(voterName_, {tellerPubKey<-tellerPubKey});
        if (id == null || voterName == null || rtstore == null) return null;

        return rtstore.retrieveVoterCapabilityShares(id, voterName);
    }
    
    private ElGamalReencryptFactor{tellerPubKey<-tellerPubKey;tellerPubKey->tellerPubKey}[]{tellerPubKey<-tellerPubKey} 
        retrieveVoterEncryptFactors{}(ElectionID{} id_, String{} voterName_) 
     
        ElectionID id = endorse(id_, {tellerPubKey<-tellerPubKey});
        String voterName = endorse(voterName_, {tellerPubKey<-tellerPubKey});
        if (id == null || voterName == null || rtstore == null) return null;

        return rtstore.retrieveVoterEncryptFactors(id, voterName);
    }

    
    private ElectionDetails{} readInElectionDetails{}() throws (IOException{}) {
        if (input == null) return null;

        ElectionDetails elecDetails = null;
        try {
            elecDetails = ElectionDetails.fromXML(new label {}, new StringReader[{}](protocolInputString(input)));
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }        
        return elecDetails;
    }
    private TellerDetails{} readInTellerDetails{}() throws (IOException{}) {
        if (input == null) return null;
        
        TellerDetails tellerDetails = null;
        try {
            tellerDetails = TellerDetails.fromXML(new label {}, new StringReader[{}](protocolInputString(input)));
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }
        return tellerDetails;
    }
    private ElectionID{} readInElectionID{}() throws (IOException{}) {
        if (input == null) return null;

        ElectionID electionID = null;
        try {
            electionID = ElectionID.fromXML(new label {}, new StringReader[{}](protocolInputString(input)));
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }
        return electionID;
    }
    
    /**
     * Decide whether or not teller p will participate in an election
     */
    protected boolean{} decideParticipation{}(final ElectionDetails{} elecDetails_, TellerDetails{} tellers_, int{} tellerIndex_) 
     
        ElectionDetails elecDetails = endorse(elecDetails_, {tellerPubKey<-tellerPubKey});
        TellerDetails tellers = endorse(tellers_, {tellerPubKey<-tellerPubKey});
        int tellerIndex = endorse(tellerIndex_, {tellerPubKey<-tellerPubKey});
        if (elecDetails == null || rtstore == null) return false;

        ElectionCache electionCache = rtstore.electionCache(elecDetails.electionID, tellerIndex);

        // check that the election status is appropriate
        boolean decision = true;
        try {
            if (ElectionUtil.retrieveElectionStatus(tellerPubKey, elecDetails.electionID, electionCache) != ElectionUtil.STATUS_DEFINED) { 
                decision = false;
            }
        }
        catch (IOException e) {
            // couldn't contact the BB server. Seems like a bad deal...
            decision = false;
        }
                
        
        if (endorse(decision, {tellerPubKey<-tellerPubKey}) && rtstore != null) {
            rtstore.storeAcceptance(elecDetails.electionID);
        }
        return decision;
    }
}