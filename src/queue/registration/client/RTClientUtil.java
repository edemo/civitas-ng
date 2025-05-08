/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.client;

import java.net.*;
import jif.net.*;
import jif.util.*;
import java.io.*;
import civitas.common.*;
import civitas.crypto.*;

/**
 * Utility methods for a registration teller client.
 */
public class RTClientUtil extends Protocol {
    public static final int  VOTER_NONCE_LENGTH = 64;
    /**
     * Request regTeller to participate in the election described by details,
     * with the other tellers given by tellers.
     */
    public boolean requestParticipation{}(Host{} regTeller, int{} index, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) {
        return participation("REQUEST_PARTICIPATION", regTeller, details, tellers, index);
    }   
    /**
     * Confirm regTeller as a participant in the election described by details,
     * with the other tellers given by tellers.
     */
    public boolean confirmParticipation{}(Host{} regTeller, int{} index, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) {
        return participation("CONFIRM_PARTICIPATION", regTeller, details, tellers, index);
    }
    
    /**
     * utility method for implementing requestParticipation and confirmParticipation.
     */
    private boolean participation{}(String{} kind, Host{} regTeller, ElectionDetails{} details, TellerDetails{} tellers, int{} index) throws (IOException{}, IllegalArgumentException{}) {
        if (regTeller == null) throw new IllegalArgumentException("null teller");
        if (details == null) throw new IllegalArgumentException("null election details");
        if (tellers == null) throw new IllegalArgumentException("null teller details");
        
        Socket[{}] s = new Socket[{}](regTeller.address, regTeller.port);
        BufferedReader[{}] input = new BufferedReader[{}](new InputStreamReader[{}](s.getInputStream()));
        
        PrintStream[{}] output = new PrintStream[{}](s.getOutputStream());
        
        
        output.println(kind);
        StringWriter[{}] sb = new StringWriter[{}]();
        details.toXML(new label {}, new PrintWriter[{}](sb));
        String m = sb.toString();
        if (m == null) throw new IllegalArgumentException("null election details");
        
        protocolOutputString(output, m);

        sb = new StringWriter[{}]();
        tellers.toXML(new label {}, new PrintWriter[{}](sb));
        m = sb.toString();
        if (m == null) throw new IllegalArgumentException("null teller details");
        protocolOutputString(output, m);
        
            // send the index
        output.println("" + index);
        output.flush();
        
        String response = input.readLine();        
        s.close();
        
        return Util.stringToBoolean(response);
    }   

    /**
     * Notify the registration teller to check the electoral rolls.
     * Optionally provide a message id, in which case the registration teller
     * will only process that message id.
     */
    public void notifyRegistration{}(final ElectionDetails{} details, TellerDetails{} tellers, int{} tellerIndex, String{} messageID, boolean{} waitForProcessing) throws (IOException{}, IllegalArgumentException{}) {
        if (details == null) throw new IllegalArgumentException("null details");
        ElectionID electionID = details.electionID;
        if (electionID == null) throw new IllegalArgumentException("null electionID");
        
        Host regTeller = null;
        try {
            regTeller = tellers.registrationTellers[tellerIndex-1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("no appropriate registration teller");
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("no appropriate registration teller");
        }
        
        if (regTeller == null) {
            throw new IllegalArgumentException("no appropriate registration teller");            
        }
        Socket[{}] s = new Socket[{}](regTeller.address, regTeller.port);
        BufferedReader[{}] input = new BufferedReader[{}](new InputStreamReader[{}](s.getInputStream()));
        
        PrintStream[{}] output = new PrintStream[{}](s.getOutputStream());
                
        output.println("NEW_REGISTRATION");
        
        StringWriter[{}] sb = new StringWriter[{}]();
        electionID.toXML(new label {}, new PrintWriter[{}](sb));
        String m = sb.toString();
        if (m == null) throw new IllegalArgumentException("null election ID");
        
        protocolOutputString(output, m);
        if (messageID != null) {
            output.println(messageID);
        }
        else {
            output.println();
        }
        output.flush();
        if (waitForProcessing) {
            input.readLine();
        }
        s.close();
    }
    
    /**
     * Retrieve capabilities from the registration teller
     */
    public VoterCapabilitySharesAndProofHolder{} retrieveCapabilities{}(ElectionCache{} electionCache,
            ElectionDetails{} details, 
            TellerDetails{} tellers,
            int{} tellerIndex,
            String{} voterName, 
            ElGamalPublicKey{} voterEGPubKey,
            ElGamalPrivateKey{} voterEGPrivKey,
            final PublicKey{} voterPubKey,
            PrivateKey{} voterPrivKey) throws (IOException{}, IllegalArgumentException{}, CryptoException{}) {
        if (details == null) throw new IllegalArgumentException("null details");
        if (voterName == null) throw new IllegalArgumentException("null voterName");
        ElectionID electionID = details.electionID;
        if (electionID == null) throw new IllegalArgumentException("null electionID");
        CryptoFactory factory = CryptoUtil.factory();
        if (factory == null)  throw new IllegalArgumentException("couldn't get cryptofactory");
        Host regTeller = null;
        try {
            regTeller = tellers.registrationTellers[tellerIndex-1];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("no appropriate registration teller");
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("no appropriate registration teller");
        }
        
        if (regTeller == null) {
            throw new IllegalArgumentException("no appropriate registration teller");            
        }
        PublicKey regPubKey = regTeller.publicKey;
        ElGamalPublicKey tabTellerSharedPK = ElectionUtil.retrieveTabTellerSharedPublicKey(details, electionCache);
        if (tabTellerSharedPK == null) {
            // no tab teller key posted yet
            throw new IOException("Could not retrieve tab teller shared public key");
        }
        
        
        Socket[{}] s = new Socket[{}](regTeller.address, regTeller.port);
        BufferedReader[{}] input = new BufferedReader[{}](new InputStreamReader[{}](s.getInputStream()));        
        PrintStream[{}] output = new PrintStream[{}](s.getOutputStream());
                
        output.println("RETRIEVE_CAPABILITIES");
        
        // STEP 1: generate nonce NV
        String NV = factory.freshNonceBase64(new label {}, VOTER_NONCE_LENGTH);
                
        // STEP 2: send to RT: ENC(electionID, Votername, NV; RegPubKey)
        StringWriter[{}] sb = new StringWriter[{}]();
        PrintWriter[{}] pw = new PrintWriter[{}](sb);
        electionID.toXML(new label {}, pw);
        pw.print(voterName);
        pw.print("\n");
        pw.print(NV);
        pw.print("\n");
        String msg = sb.toString();
        String m = null;
        try {
            sb = new StringWriter[{}]();
            factory.publicKeyEncrypt(regPubKey, factory.publicKeyMsg(msg)).toXML(new label {}, new PrintWriter[{}](sb));
            m = sb.toString();        
        }
        catch (NullPointerException ignore) { }
        protocolOutputString(output, m);        
        
        
        // STEP 3: RT generates nonce NR and session key k
        // STEP 4: receive from RT Enc(electionID', tellerIndex', NR, NV', k; voterPubKey)
        String wire4 = protocolInputString(input);
        
        // Check to make sure the registration teller said the election was ready
        if ("ELECTION NOT READY".equals(wire4)) {
            // the registration teller isn't ready to give us the
            // capabilities yet.
            return null;
        }
        
        PublicKeyCiphertext cipher4 = factory.publicKeyCiphertextFromXML(new label {}, new BufferedReader[{}](new StringReader[{}](wire4)));        
        String plaintext4 = null;
        try {
            plaintext4 = factory.publicKeyDecrypt(voterPrivKey, cipher4).toString();
        }
        catch (NullPointerException ignore) { }
                
        BufferedReader[{}] r = new BufferedReader[{}](new StringReader[{}](plaintext4));
        String host = r.readLine();
        int port = Integer.parseInt(r.readLine(), 16);
        String eid = r.readLine();
        ElectionID electionIDPrime = new ElectionID(host, port, eid);
        
        int index = Integer.parseInt(r.readLine(), 16);
        String NR = r.readLine();
        String NVprime = r.readLine();
        SharedKey k = factory.sharedKeyFromWire(new label {}, r);
        
        
        // STEP 5: verify NV' == NV and electionIDPrime = electionID and tellerIndex' = tellerIndex
        if (NVprime == null || !NVprime.equals(NV) ||
                electionIDPrime == null || !electionIDPrime.equals(electionID) ||
                index != tellerIndex) {
               
            // doesn't pass muster. abandon the protocol;
            s.close();
            throw new IOException("Failed protocol: teller did not return correct nonce and/or registration teller public key and/or election ID");
        }
                
        // STEP 6: Send to RT: NR
        protocolOutputString(output, NR);        

        
        // STEP 7: RT verifies NR
        // STEP 8: receive Enc(capabilities and proofs; k)
        String wire9 = protocolInputString(input);       
        SharedKeyCiphertext cipher9 = factory.sharedKeyCiphertextFromXML(new label {}, new BufferedReader[{}](new StringReader[{}](wire9)));
        
        VoterCapabilitySharesAndProof proof = null;
        try {
            // This is the encoding of the capability shares and proof, and so is sensitive information.
            final label voterSensitive = new label {voterPubKey->*};
            String{*voterSensitive} plaintext9 = factory.sharedKeyDecrypt(k, cipher9).toString();
        
            BufferedReader[voterSensitive] r9 = new BufferedReader[voterSensitive](new StringReader[voterSensitive](plaintext9));
            proof = VoterCapabilitySharesAndProof.fromXML(voterSensitive, r9);
        
        }
        catch (NullPointerException ignore) { }
        catch (IllegalArgumentException ignore) { }
        catch (IOException ignore) { }

        VoterCapabilitySharesAndProofHolder proofHolder = new VoterCapabilitySharesAndProofHolder(proof, (principal)voterPubKey);
        s.close();
        return proofHolder;
    }   
}