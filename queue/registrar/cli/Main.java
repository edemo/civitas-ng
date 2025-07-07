/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registrar.cli;

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
 * Main file for the command line interface for a registrar.
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
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.registrar.cli.Main help' for help.");
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
                    out.println("Usage: civitas.registrar.cli.Main command [options]");
                    out.println(" where command is one of the following.");
                    out.println("       roll");
                    out.println("       estimate n");
                    out.println("       help");
                    out.println(" Try civitas.registrar.cli.Main help command for help specific to a command.");
                    return;
                }
                try {
                    command = args[1].toLowerCase();
                }
                catch (NullPointerException e) { }
                catch (ArrayIndexOutOfBoundsException e) { }
            }
            
            if ("roll".equals(command)) {
                if (usage) 
                    electoralRollUsage(pp, out);
                else
                    electoralRoll(pp, out, in, args);
            }
            else if ("estimate".equals(command)) {
                if (usage) 
                    estimateUsage(pp, out);
                else
                    estimate(pp, out, in, args);
            }
            else {
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.registrar.cli.Main help' for help.");                
            }
        }
        catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
                
    }
    
    
    private static void electoralRollUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("roll registrarPublicKeyFile registrarPrivateKeyFile");
            out.println("  an XML election ID and an XML electoral roll is read in from the standard input.");
        }
    }
    
    private static void electoralRoll{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            PublicKey regPubKey = null;
            PrivateKey regPrivKey = null;
            try {
                regPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                regPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
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
            ElectoralRoll roll = null; 
            try {
                roll = ElectoralRoll.fromXML(in);                
            }
            catch (Exception e) {
                throw new IllegalArgumentException("Unable to read in the electoral roll from the standard input: " + e.getMessage());
            }
          
            try {
                if (roll != null) {
                    ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, null);
                    if (details == null) {
                        throw new IllegalArgumentException("The election does not yet have election details posted!");
                    }

                    out.println("Posting electoral roll for election '" + electionID.toString() + "'");
                    new Registrar().electoralRoll(electionID, regPubKey, regPrivKey, roll);
                    out.println("Posted electoral roll for election '" + electionID.toString() + "' and notified registration teller");
                }
            }
            catch (UnknownHostException e) {
                out.println("Unable to post electoral roll: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to post electoral roll: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException e) {
            electoralRollUsage(pp, out);
            return;
        }                
    }       
    
    private static void estimateUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("estimate registrarPublicKeyFile registrarPrivateKeyFile n");
            out.println("  n is a postive integer estimating the number of voters that will be in the electoral roll.");
            out.println("  an XML election ID is read in from the standard input.");
        }
    }
    
    private static void estimate{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            PublicKey regPubKey = null;
            PrivateKey regPrivKey = null;
            try {
                regPubKey = CryptoUtil.factory().publicKeyFromFile(args[1]);
                regPrivKey = CryptoUtil.factory().privateKeyFromFile(args[2]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }

            int estimate = -1;
            try {
                estimate = Integer.parseInt(args[3]);
            }
            catch (NumberFormatException{pp<-*} e) {
                estimateUsage(pp, out);
                return;
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
                out.println("Posting electoral roll estimate for election '" + electionID.toString() + "'");
                new Registrar().estimate(electionID, regPubKey, regPrivKey, estimate);
                out.println("Posted electoral roll estimate for election '" + electionID.toString() + "'");
            }
            catch (UnknownHostException e) {
                out.println("Unable to post electoral roll estimate: Unknown host " + e.getMessage());
            }
            catch (IOException e) {
                out.println("Unable to post electoral roll estimate: " + e.getMessage());
            }           
        }
        catch (ArrayIndexOutOfBoundsException e) {
            estimateUsage(pp, out);
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
