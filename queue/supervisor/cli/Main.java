/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.supervisor.cli;

import java.io.*;
import java.net.*;
import jif.runtime.Runtime;
import jif.util.*;

import civitas.common.*;
import civitas.crypto.*;
import civitas.supervisor.*;
import civitas.bboard.client.*;
import civitas.bboard.common.*;

/**
 * Main file for the command line interface for a supervisor.
 */
public class Main {
    public static void main (principal  pp, String []  args)  throws (Exception{pp->*})  
        Runtime[pp]{pp<-*} runtime = null;
        try {
            runtime = Runtime[pp].getRuntime();
        }
        catch (SecurityException{pp<-*} e) { }

        if (runtime == null) return;
        
        PrintStream[{pp->*;_<-_}] out = declassify(runtime.out(), {pp->*;pp<-*} to {pp<-*});
        InputStream[{pp->*;pp<-*}]{pp<-*} ins = declassify(runtime.in(), {pp->*;pp<-*} to {pp<-*});
        InputStream[{}]{pp<-*} ind = new DowngradingInputStream[pp](ins);
        Reader[{}] in = new BufferedReader[{}](new InputStreamReader[{}](ind));
        
        
        // parse arguments
        try {
            if (args == null || args.length < 1) {
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.supervisor.cli.Main help' for help.");
            }
            
            String command = null;
            try {
                command = args[0];
            }
            catch (ArrayIndexOutOfBoundsException{pp<-*} e) { }
            
            if (command == null) return;
            command = command.toLowerCase();
                        
            boolean usage = false;
            
            if ("help".equals(command)) {
                usage = true;
                if (args.length == 1) {
                    out.println("Usage: civitas.supervisor.cli.Main command [options]");
                    out.println(" where command is one of the following.");
                    out.println("       create");
                    out.println("       initialize");
                    out.println("       request");
                    out.println("       confirm");
                    out.println("       event");
                    out.println("       tabulate");
                    out.println("       debug");
                    out.println("       list");
                    out.println("       help");
                    out.println(" Try civitas.supervisor.cli.Main help command for help specific to a command.");
                    return;
                }
                try {
                    command = args[1].toLowerCase();
                }
                catch (NullPointerException e) { }
                catch (ArrayIndexOutOfBoundsException e) { }
            }
            
            if ("create".equals(command)) {
                if (usage) 
                    createElectionUsage(pp, out);
                else
                    createElection(pp, out, args);
            }
            else if ("initialize".equals(command) || (command != null && command.startsWith("init"))) {
                // enter in the election details
                if (usage) 
                    initElectionUsage(pp, out);
                else
                    initElection(pp, out, in, args);
            }
            else if ("request".equals(command) || (command != null && command.startsWith("req"))) {
                // request the tellers.
                if (usage) 
                    requestTellersUsage(pp, out);
                else
                    requestTellers(pp, out, in, args);
            }
            else if ("confirm".equals(command) || (command != null && command.startsWith("conf"))) {
                // confirm the tellers.
                if (usage) 
                    confirmTellersUsage(pp, out);
                else
                    confirmTellers(pp, out, in, args);
            }
            else if ("event".equals(command) || (command != null && command.startsWith("ev"))) {
                // start stop or finalize the election.
                if (usage) 
                    eventUsage(pp, out);
                else
                    event(pp, out, in, args);
            }
            else if ("status".equals(command) || (command != null && command.startsWith("sta"))) {
                // report the status of an election
                if (usage) 
                    statusUsage(pp, out);
                else
                    status(pp, out, in, args);
            }
            else if ("tabulate".equals(command) || (command != null && command.startsWith("tab"))) {
                // tabulate the election.
                if (usage) 
                    tabulateUsage(pp, out);
                else
                    tabulate(pp, out, in, args);
            }
            else if ("debug".equals(command)) {
                // debug the election.
                if (usage) 
                    debugUsage(pp, out);
                else
                    debug(pp, out, in, args);
            }
            else if ("list".equals(command)) {
                // list the bboard
                if (usage) 
                    listBoardUsage(pp, out);
                else
                    listBoard(pp, out, in, args);
                    
            }
            else {
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.supervisor.cli.Main help' for help.");                
            }
        }
        catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
                
    }
    
    
    private static void createElectionUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("create bbHost bbPort bbPublicKeyFile supervisorPublicKeyFile supervisorPrivateKeyFile [outputFile]");
        }
    }
    private static void createElection{pp<-*}(principal{pp<-*} pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        // usage: create bbHost bbPort supPrivKey supPubKey        
        // outputs the new election's id.

        if (out == null || args == null) return;
        try {
            String bbHost = args[1];
            int bbPort;
            try {
                bbPort = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException{pp<-*} e) {
                createElectionUsage(pp, out);
                return;
            }
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[3]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[4]);
            }
            catch (NullPointerException imposs) { }
            catch (IOException e) {  }
            
            String id = null;
            try {
                id = NewElection.createElection(bbHost, bbPort, supPubKey);
            }
            catch (UnknownHostException e) {
                out.println("Unable to create election: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to create election: " + e.getMessage());
            }
            if (args.length > 5) {
                // output the election id to the specified file.
                String outputFile = null;
                try {
                    outputFile = args[5];
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                
                try {
                    FileOutputStream[{pp->*}] idFOut = null;
                    try {
                        Runtime[pp]{pp<-*} runtime = Runtime[pp].getRuntime();
                        idFOut = runtime.openFileWrite(outputFile, false, new label {pp->*});
                    }
                    catch (IOException e) { 
                        out.println("Unable to open file " + outputFile + ": " + e.getMessage());
                    }
                    catch (SecurityException e) { 
                        out.println("Unable to open file " + outputFile + ": " + e.getMessage());
                    }
                    catch (NullPointerException ignored) { }
                    
                    ElectionID electionID = new ElectionID(bbHost, bbPort, id);
                    PrintWriter[{pp->*}] pw = new PrintWriter[{pp->*}](new OutputStreamWriter[{pp->*}](idFOut));
                    electionID.toXML(new label {pp->*}, pw);
                    pw.close();
                    idFOut.flush();
                    idFOut.close();
                }
                catch (NullPointerException ignored) { }
                catch (IOException e) { 
                    out.println("Unable to open file " + outputFile + ": " + e.getMessage());
                }
            }
            
            out.println("Created election, with id = " + id);
            out.println("Please record the election id carefully.");
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            createElectionUsage(pp, out);
            return;
        }                
    }   

    private static void initElectionUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("initialize supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML election details is read in from the standard input.");
        }
    }
    
    private static void initElection{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }

            ElectionDetails details = null; 
            try {
                details = ElectionDetails.fromXML(new label {}, in);                
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election details from the standard input: " + e.getMessage());
            }
          
            try {
                if (details != null && details.electionID != null) {
                    // check that the election doesn't already have a details posted
                    if (ElectionUtil.retrieveElectionDetails(details.electionID, null) != null) {
                        throw new IllegalArgumentException("Election " + details.electionID.toString() + " already has an election details posted!");
                    }
                    else {
                        out.println("Initializing election " + details.electionID.toString() + "'");
                        NewElection.initializeElection(details, supPrivKey);
                        out.println("Initialized election " + details.electionID.toString());
                    }
                }
            }
            catch (UnknownHostException e) {
                out.println("Unable to initialize election: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to initialize election: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            initElectionUsage(pp, out);
            return;
        }                
    }   

    private static void requestTellersUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("request supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML electionID and an XML teller details are read in from the standard input.");
        }
    }
    
    private static void requestTellers{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }

            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }

            TellerDetails tellers = null; 
            try {
                tellers = TellerDetails.fromXML(new label {}, in);                
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the teller details from the standard input: " + e.getMessage());
            }
          
            try {
                if (tellers != null) {
                    ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
                    if (details == null) {
                        throw new IllegalArgumentException("The election does not yet have election details posted!");
                    }

                    // check that there is not already a teller details posted.
                    if (ElectionUtil.retrieveTellerDetails(details, null) != null) {
                        throw new IllegalArgumentException("Election " + electionID.toString() + " already has teller details posted!");
                    }

                    out.println("Requesting tellers for election '" + electionID.toString() + "'");
                    RequestTellerResults res = NewElection.requestTellers(details, tellers);
                    out.println("Requested tellers for election " + electionID.toString());
                    if (res == null || res.accepted == null  || res.declined == null  || res.uncontactable == null ) {
                        out.println("  null results!");
                    }
                    else {
                        out.print("  tellers that accepted: ");
                        out.println(res.accepted.toString());
                        out.print("  tellers that declined: ");
                        out.println(res.declined.toString());
                        out.print("  tellers that could not be contacted: ");
                        out.println(res.uncontactable.toString());
                    }
                }
            }
            catch (UnknownHostException e) {
                out.println("Unable to request tellers: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to request tellers: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            requestTellersUsage(pp, out);
            return;
        }                
    }   

    private static void confirmTellersUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("confirm supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML election ID and an XML teller details is read in from the standard input.");
        }
    }
    
    private static void confirmTellers{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }

            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }
            
            TellerDetails tellers = null; 
            try {
                tellers = TellerDetails.fromXML(new label {}, in);                
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the teller details from the standard input: " + e.getMessage());
            }
          
            try {
                if (tellers != null) {
                    ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
                    if (details == null) {
                        throw new IllegalArgumentException("The election does not yet have election details posted!");
                    }
                    
                    // check that there is not already a teller details posted.
                    if (ElectionUtil.retrieveTellerDetails(details, null) != null) {
                        throw new IllegalArgumentException("Election " + electionID.toString() + " already has teller details posted!");
                    }
                    else {
                        out.println("Confirming tellers for election '" + electionID.toString() + "'");
                        NewElection.confirmTellers(details, tellers, supPrivKey);
                        out.println("Confirmed tellers for election " + electionID.toString());
                    }
                }
            }
            catch (UnknownHostException e) {
                out.println("Unable to confirm tellers: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to confirm tellers: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            requestTellersUsage(pp, out);
            return;
        }                
    }   

    private static void eventUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("event [start|stop] supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML election id is read in from the standard input.");
            out.println("event finalize supervisorPublicKeyFile supervisorPrivateKeyFile [teller n] [message m]");
            out.println("  n is the index of the teller to use for the election results.");
            out.println("  m is the message to include in the finalize election event.");
            out.println("  an XML election id is read in from the standard input.");
        }
    }
    
    private static void event{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        try {
            String kind = args[1];
            PublicKey supPubKeyFromFile = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKeyFromFile = CryptoUtil.factory().publicKeyFromFile(args[2]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[3]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }
            final PublicKey supPubKey = supPubKeyFromFile;

            int index = 4;
            String message = null;
            Integer tellerIndex = null;
            
            while (index < args.length) {
                String s = args[index++];
                if ("message".equalsIgnoreCase(s)) {
                    message = args[index++];
                }
                else if ("teller".equalsIgnoreCase(s)) {
                    try {
                        tellerIndex = Integer.valueOf(args[index++]);
                    }
                    catch (NumberFormatException{pp<-*} e) {
                        eventUsage(pp, out);
                        return;
                    }

                }
            }

            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }

            try {
                ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
                if (details == null) {
                    throw new IllegalArgumentException("The election does not yet have election details posted!");
                }

                // ideally we should be using the principal supPubKey, instead of the principal null.
                // However, to do so, we would need to establish that this code has the authority
                // of the supervisor, which we could gain by checking that pp acts for the supervisor.
                // Perhaps the presence of the supervisors private key would be sufficient...
                int status = ElectionUtil.retrieveElectionStatus(null, electionID, null);  
                
                if ("start".equalsIgnoreCase(kind)) {
                    // check that the election is in a suitable state to be started.
                    if (status != ElectionUtil.STATUS_INITIALIZED) {
                        throw new IllegalArgumentException("The election is not in a suitable state to be started.");
                    }

                    out.println("Posting well known ciphertexts '" + electionID.toString() + "'");
                    NewElection.postWellKnownCiphertexts(details, supPrivKey);
                    out.println("Posted well known ciphertexts '" + electionID.toString() + "'");

                    out.println("Starting election '" + electionID.toString() + "'");
                    StartStopElection.startElection(details, supPrivKey);
                    out.println("Started election " + electionID.toString());                   
                }
                else if ("stop".equalsIgnoreCase(kind)) {
                    // check that the election is in a suitable state to be stopped.
                    if (status != ElectionUtil.STATUS_STARTED) {
                        throw new IllegalArgumentException("The election is not in a suitable state to be stopped.");
                    }

                    out.println("Stopping election '" + electionID.toString() + "'");
                    StartStopElection.stopElection(details, supPrivKey);
                    out.println("Stopped election " + electionID.toString());                    
                }
                else if ("finalize".equalsIgnoreCase(kind)) {
                    // check that the election is in a suitable state to be finalized.
                    if (status != ElectionUtil.STATUS_TABULATED && status != ElectionUtil.STATUS_ABANDONED) {
                        throw new IllegalArgumentException("The election is not in a suitable state to be finalized.");
                    }
                                        
                    out.println("Finalizing election '" + electionID.toString() + "'");
                    if (tellerIndex != null) {
                        StartStopElection.finalizeElection(details, supPrivKey, message, tellerIndex.intValue());
                    }
                    else {
                        StartStopElection.finalizeElection(details, supPrivKey, message);                        
                    }
                    out.println("Finalized election " + electionID.toString());                   
                }
            }
            catch (UnknownHostException e) {
                out.println("Unable to issue event: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to issueEevent: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            eventUsage(pp, out);
            return;
        }                
    }   
    
    private static void statusUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("status");
            out.println("  an XML election ID is read in from the standard input.");
        }
    }
    
    private static void status{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        ElectionID electionID = null;
        try {
            electionID = ElectionID.fromXML(new label {}, in);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
        }
        if (electionID == null) {
            throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
        }

        try {
            int status = ElectionUtil.retrieveElectionStatus(null, electionID, null);
            if (status == ElectionUtil.STATUS_ABANDONED) {
                ElectionCache electionCache = new ElectionCache();
                ElectionAbandonment abandon = ElectionUtil.retrieveAbandonmentReason(electionID, electionCache);
                if (abandon != null) {
                    out.println("The election " + electionID.toString() + " is abandoned: " + abandon.reason);
                    out.println("Reported by " + abandon.reporter());
                }
                else {
                    out.println("The election " + electionID.toString() + " is abandoned. No further information available");                    
                }
            }
            else {
                out.println("The status of election " + electionID.toString() + " is " + ElectionUtil.statusString(status));
            }

        }
        catch (UnknownHostException e) {
            out.println("Unable to determine status: Unknown host " + e.getMessage());
        }
        catch (IOException e) {
            out.println("Unable to determine status: " + e.getMessage());
        }           
    }   

    private static void tabulateUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("tabulate supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML election ID is read in from the standard input.");
            out.println("  this command will start or continue the notification of the tabulation tellers, instructing them to perform the mixing and tabulation of results.");
        }
    }

    private static void tabulate{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        try {
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (IOException e) {  }

            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }

            ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
            if (details == null) {
                throw new IllegalArgumentException("Could not retrieve election details!");
            }

            StartStopElection.startTabulation(details, supPrivKey);
        }
        catch (ArrayIndexOutOfBoundsException e) { 
            tabulateUsage(pp, out);
            return;
        }

    }

    private static void debugUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("debug supervisorPublicKeyFile supervisorPrivateKeyFile");
            out.println("  an XML election id is read in from the standard input.");
        }
    }
    
    private static void debug{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        try {
            PublicKey supPubKey = null;
            PrivateKey supPrivKey = null;
            try {
                supPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                supPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (IOException e) {  }

            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }

            ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
            if (details == null) {
                throw new IllegalArgumentException("Could not retrieve election details!");
            }

            StartStopElection.debugTabulation(details, supPrivKey);
        }
        catch (ArrayIndexOutOfBoundsException e) { 
            tabulateUsage(pp, out);
            return;
        }

    }

    private static void listBoardUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("list [meta metastring] [from timestamp] [to timestamp]");
            out.println("  an XML election ID is read in from the standard input.");
        }
    }
    
    private static void listBoard{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        try {
            int index = 1;
            String meta = null;
            Long fromTime = null;
            Long toTime = null;
            
            while (index < args.length) {
                String s = args[index++];
                if ("meta".equalsIgnoreCase(s)) {
                    meta = args[index++];
                }
                else if ("from".equalsIgnoreCase(s)) {
                    try {
                        fromTime = Long.valueOf(args[index++]);
                    }
                    catch (NumberFormatException{pp<-*} e) {
                        listBoardUsage(pp, out);
                        return;
                    }

                }
                else if ("to".equalsIgnoreCase(s)) {
                    try {
                        toTime = Long.valueOf(args[index++]);
                    }
                    catch (NumberFormatException{pp<-*} e) {
                        listBoardUsage(pp, out);
                        return;
                    }
                }
                else {
                    listBoardUsage(pp, out);
                    return; 
                }
            }
            ElectionID electionID = null;
            try {
                electionID = ElectionID.fromXML(new label {}, in);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input: " + e.getMessage());
            }
            if (electionID == null) {
                throw new IllegalArgumentException("Unable to read in the election id from the standard input");                
            }

            BBClientUtil bb = new BBClientUtil(electionID);
            List[{}] l;
            
            if (meta == null && fromTime == null && toTime == null) {
                l = bb.retrieve(XMLDeserializers.generic());
            }
            else {
                l = bb.retrieveParams(XMLDeserializers.generic(), meta, fromTime, toTime);
            }
            
            // output the retrieved list.
            if (l != null) {
                out.println("<query>");
                out.println("<resultSize>");
                out.println(l.size());
                out.println("</resultSize>");
                StringWriter[{}] sb = new StringWriter[{}]();
                PrintWriter[{}] pw = new PrintWriter[{}](sb);
                for (int i = 0; i < l.size(); i++) {
                    try {
                        BBPost p = (BBPost)l.get(i);
                        p.toXML(pw);
                        pw.print("\n");
                    }
                    catch (IndexOutOfBoundsException imposs) { }
                    catch (ClassCastException imposs) { }
                    catch (NullPointerException ignore) { }
                }
                out.print(sb.toString());
                out.println("</query>");
            }
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            listBoardUsage(pp, out);
            return;
        }                

    }
}

class DowngradingInputStream[principal P] extends InputStream[{}] authority(P) {
    protected final InputStream[{P->*;P<-*}]  s;
    
    protected DowngradingInputStream(InputStream[{P->*;P<-*}]  s) {
        super();
        this.s = s;
    }
    
    public int{} read{}() throws (IOException{})  
        if (s == null) return -1;
        IOException{P<-*} e = null;
        int r = 0;
        try {
            r = s.read();
        }
        catch (IOException{P->*;P<-*} ex) {
            declassify ({P<-*}) {
                e = declassify(ex, {P->*;P<-*} to {P<-*});
            }
        }
        if (e != null) throw e;
        return declassify(r, {P->*;P<-*} to {P<-*});
    }                  
}
