/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.client;

import java.net.*;
import jif.net.*;
import jif.util.*;
import java.io.*;
import civitas.common.*;
import civitas.crypto.CryptoUtil;

/**
 * Utility methods for a tabulation teller client.
 */
public class TTClientUtil extends Protocol {

    /**
     * Request tabTeller to participate in the election described by details,
     * with the other tellers given by tellers.
     */
    public boolean requestParticipation{}(Host{} tabTeller, int{} index, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) {
        return participation("REQUEST_PARTICIPATION", tabTeller, details, tellers, index);
    }   
    /**
     * Confirm tabTeller as a participant in the election described by details,
     * with the other tellers given by tellers.
     */
    public boolean confirmParticipation{}(Host{} tabTeller, int{} index, ElectionDetails{} details, TellerDetails{} tellers) throws (IOException{}, IllegalArgumentException{}) {
        return participation("CONFIRM_PARTICIPATION", tabTeller, details, tellers, index);
    }
    /**
     * Ask each tabTeller to start the generation of the shared tab teller key.
     */
    public void requestKeyGeneration{}(Host{} tabTeller, ElectionID{} electionID) throws (IOException{}, IllegalArgumentException{}) {
        notify(tabTeller, electionID, "GEN_TABULATION_KEY");
    }
    
    
    /**
     * utility method for implementing requestParticipation and confirmParticipation.
     */
    private boolean participation{}(String{} kind, Host{} tabTeller, ElectionDetails{} details, TellerDetails{} tellers, int{} index) throws (IOException{}, IllegalArgumentException{}) {
        if (tabTeller == null) throw new IllegalArgumentException("null teller");
        if (details == null) throw new IllegalArgumentException("null election details");
        if (tellers == null) throw new IllegalArgumentException("null teller details");
        
        Socket[{}] s = new Socket[{}](tabTeller.address, tabTeller.port);
        BufferedReader[{}] input = new BufferedReader[{}](new InputStreamReader[{}](s.getInputStream()));
        
        PrintWriter[{}] output = new PrintWriter[{}](s.getOutputStream());
        
        
        output.println(kind);
        
        details.toXML(new label {}, output);
        tellers.toXML(new label {}, output);
        
        // send the index
        output.println("" + index);

        output.flush();
        
        String response = input.readLine();        
        s.close();
        
        return Util.stringToBoolean(response);
    }   
    
    public void tabulate{}(Host{} tabTeller, ElectionID{} electionID) throws (IOException{}, IllegalArgumentException{}) {
        notify(tabTeller, electionID, "TABULATE");
    }
    public void debug{}(Host{} tabTeller, ElectionID{} electionID) throws (IOException{}, IllegalArgumentException{}) {
        notify(tabTeller, electionID, "DEBUG");
    }

    private void notify{}(Host{} tabTeller, ElectionID{} electionID, String{} notify) throws (IOException{}, IllegalArgumentException{}) {
        if (tabTeller == null) throw new IllegalArgumentException("null teller");
        if (electionID == null) throw new IllegalArgumentException("null electionID");
        
        Socket[{}] s = new Socket[{}](tabTeller.address, tabTeller.port);
        BufferedReader[{}] input = new BufferedReader[{}](new InputStreamReader[{}](s.getInputStream()));
        
        PrintWriter[{}] output = new PrintWriter[{}](s.getOutputStream());
                
        output.println(notify);
        
        electionID.toXML(new label {}, output);
        output.flush();
        s.close();
    }

}