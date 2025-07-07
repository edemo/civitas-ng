/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registrar;

import java.io.*;
import java.net.*;
import jif.runtime.Runtime;
import jif.util.*;

import civitas.common.*;
import civitas.crypto.*;
import civitas.registrar.*;
import civitas.bboard.client.*;
import civitas.registration.client.*;


/**
 * Functionality for a registrar
 */
public class Registrar {
    public void electoralRoll{}(ElectionID{} electionID, 
                                PublicKey{} regPubKey, 
                                PrivateKey{} regPrivKey, 
                                ElectoralRoll{} roll) 
    throws (IOException{}, IllegalArgumentException{}) {  
        if (electionID == null) {
            throw new IllegalArgumentException("No election id given");            
        }
        if (roll != null) {            
            ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
            if (details == null) {
                throw new IllegalArgumentException("The election does not yet have election details posted!");
            }
            
            BBClientUtil bb = new BBClientUtil(electionID);
            String messageID = bb.post(ElectoralRoll.META, roll, regPrivKey);
            
            // now notify the registration tellers.
            TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(details, null);
            int numRegTellers = 0;
            if (tellerDetails != null && tellerDetails.registrationTellers != null) {
                numRegTellers = tellerDetails.registrationTellers.length;
            }
            RTClientUtil rtcu = new RTClientUtil();
            for (int tellerIndex = 1; tellerIndex <= numRegTellers; tellerIndex++) {
                boolean waitForNotification = (tellerIndex == numRegTellers);
                rtcu.notifyRegistration(details, tellerDetails, tellerIndex, messageID, waitForNotification);
            }
        }
    }       
    
    public void estimate{}(ElectionID{} electionID, PublicKey{} regPubKey, PrivateKey{} regPrivKey, int{} estimate) throws (IOException{}, IllegalArgumentException{}){
        if (electionID == null) {
            throw new IllegalArgumentException("No election id given");            
        }
        ElectoralRollEstimate  ere = new ElectoralRollEstimate(estimate);
        
        BBClientUtil bb = new BBClientUtil(electionID.host, electionID.port, electionID.id);
        String messageID = bb.post(ElectoralRollEstimate.META, ere, regPrivKey);
    }    
}
