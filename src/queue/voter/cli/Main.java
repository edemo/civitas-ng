    /*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.voter.cli;

import java.io.*;
import java.net.*;
import jif.runtime.Runtime;
import jif.util.*;

import civitas.common.*;
import civitas.crypto.*;
import civitas.voter.*;
import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.registration.client.*;

/**
 * Main file for the command line interface for a voter.
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
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.voter.cli.Main help' for help.");
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
                    out.println("Usage: civitas.voter.cli.Main command [options]");
                    out.println(" where command is one of the following.");
                    out.println("       retrieve");
                    out.println("       vote");
                    out.println("       list");
                    out.println("       help");
                    out.println(" Try civitas.voter.cli.Main help command for help specific to a command.");
                    return;
                }
                try {
                    command = args[1].toLowerCase();
                }
                catch (NullPointerException e) { }
                catch (ArrayIndexOutOfBoundsException e) { }
            }
            
            if ("retrieve".equals(command) || (command != null && command.startsWith("ret"))) {
                if (usage) 
                    retrieveUsage(pp, out);
                else
                    retrieve(pp, out, in, args);
            }
            else if ("vote".equals(command)) {
                // submit a vote
                if (usage) 
                    voteUsage(pp, out);
                else
                    vote(pp, out, in, args);
            }
            else if ("list".equals(command)) {
                // list the bboard
                if (usage) 
                    listBoardUsage(pp, out);
                else
                    listBoard(pp, out, args);
                    
            }
            else {
                throw new IllegalArgumentException("Invalid usage. Use 'civitas.supervisor.cli.Main help' for help.");                
            }
        }
        catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
                
    }
    
    private static void retrieveUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("retrieve voterName voterEGPubKeyFile voterEGPrivKeyFile voterPubKeyFile voterPrivKeyFile");
            out.println("  an XML electionID is read in from the standard input, and capabilities are output to the standard output");
            out.println("  in plaintext.");
        }
    }

    
    private static void retrieve{pp<-*}(principal{} pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        String voterName = null;
        ElGamalPublicKey voterEGPubKey = null;
        ElGamalPrivateKey voterEGPrivKey = null;
        PublicKey voterPubKey_ = null;
        PrivateKey voterPrivKey = null;
        try {
            voterName = args[1];
            try {
                voterEGPubKey = CryptoUtil.factory().egPubKeyFromFile(args[2]);
                voterEGPrivKey = CryptoUtil.factory().egPrivKeyFromFile(args[3]);
                voterPubKey_ = CryptoUtil.factory().publicKeyFromFile(args[4]);
                voterPrivKey = CryptoUtil.factory().privateKeyFromFile(args[5]);
            }
            catch (NullPointerException imposs) { }
            catch (FileNotFoundException ignore) { }
            catch (IOException ignore) { }
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            voteUsage(pp, out);
            return;
        }                

        final PublicKey voterPubKey = voterPubKey_;

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
        
        // get election and teller details
        ElectionCache electionCache = new ElectionCache();
        ElectionDetails electionDetails = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
        if (electionDetails == null) {
            throw new IllegalArgumentException("The election does not yet have election details posted!");
        }
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        if (tellerDetails == null) {
            throw new IllegalArgumentException("The election does not yet have teller details posted!");
        }
        
        // go to registration tellers and get encrypted capabilities

        try {
            RetrieveCapabilitiesClosure[voterPubKey] closure = 
                new RetrieveCapabilitiesClosure[voterPubKey](
                                        electionCache, 
                                        electionDetails, 
                                        tellerDetails,
                                        voterName,
                                        voterEGPubKey,
                                        voterEGPrivKey,
                                        voterPubKey,
                                        voterPrivKey);
            Object ret = PrincipalUtil.execute(voterPubKey, voterPrivKey, closure, new label {});
            if (ret instanceof CryptoException) throw ((CryptoException)ret);
            if (ret instanceof IllegalArgumentException) throw ((IllegalArgumentException)ret);
            if (ret instanceof IOException) throw ((IOException)ret);
            
            VoterCapabilities vc = (ret instanceof VoterCapabilities)?((VoterCapabilities)ret):null;
            if (vc != null) {
                // output capabilities
                StringWriter[{}] sb = new StringWriter[{}]();
                vc.toXML(new label {}, new PrintWriter[{}](sb));
                out.println(sb.toString());
            }
            else {
                throw new IllegalArgumentException("No capabilities could be retrieved.");                        
            }
        }
        catch (CryptoException e) {
            throw new IOException(e.getMessage());
        }
    }
    
    
    
    
    private static void voteUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("vote voterName voterEGPrivKeyFile");
            out.println("  an XML electionID, an XML ballot, and an XML VoterCapabilities is read in from the standard input");
        }
    }

    
    private static void vote{pp<-*}(principal{} pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, Reader[{}]{pp<-*} in, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || in == null || args == null) return;
        try {
            String voterName = args[1];
            ElGamalPrivateKey voterEGPrivKey = null;
            try {
                voterEGPrivKey = CryptoUtil.factory().egPrivKeyFromFile(args[2]);
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

            // retrieve election details
            ElectionCache electionCache = new ElectionCache();
            ElectionDetails details = ElectionUtil.retrieveElectionDetails(electionID, electionCache);
            if (details == null) {
                throw new IllegalArgumentException("The election does not yet have election details posted!");
            }

            // check that voting is open
            int status = ElectionUtil.retrieveElectionStatus(pp, electionID, electionCache);
            if (status != ElectionUtil.STATUS_STARTED) {
                throw new IllegalArgumentException("The election voting has not been started.");
            }
            
            // read in ballot
            Ballot[{}] b = Ballot[{}].fromXML(new label {}, in);
                        

            // read in the capabilities
            VoterCapabilities caps = VoterCapabilities.fromXML(new label {}, in);
            if (caps == null) {
                throw new IllegalArgumentException("No voter capabilities supplied.");                
            }

            // validate ballot against design.
            BallotDesign design = details.ballotDesign;
            if (design != null) {
                design.checkBallot(new label {}, b);
            }

            endorseVote(pp, voterName, details, electionCache, b, caps);
            
        }
        catch (ArrayIndexOutOfBoundsException{pp<-*} e) {
            voteUsage(pp, out);
            return;
        }                
    }   
    

    private static void endorseVote{}(principal{} pp, 
            String{} voterName_, 
            ElectionDetails{} details_, 
            ElectionCache{} cache_, 
            Ballot[{}]{} b_, 
            VoterCapabilities{} caps_
         ) 
        throws (IOException{}, IllegalArgumentException{}) 
         
        final principal epp = endorse(pp, {pp<-pp}); 
        String voterName = endorse(voterName_, {epp<-epp}); 
        ElectionDetails details = endorse(details_, {epp<-epp}); 
        ElectionCache cache = endorse(cache_, {epp<-epp});
        VoterCapabilities caps = endorse(caps_, {epp<-epp});
        
        if (caps == null) return;
        
        Ballot[{epp->epp;epp<-epp}] b = null;
        try {
            Ballot[{}] eb = endorse(b_, {epp<-epp}); 
            StringWriter[{}] sb = new StringWriter[{}]();
            eb.toXML(new PrintWriter[{}](sb));
            String ballotString = endorse(sb.toString(), {epp<-epp});
            b = Ballot[{epp->epp;epp<-epp}].fromXML(new label {epp->epp;epp<-epp}, new StringReader[{epp->epp;epp<-epp}](ballotString));
        }
        catch (IOException ignore) { }
        catch (IllegalArgumentException ignore) { }
        catch (NullPointerException ignore) { }
        Voter.vote(epp, voterName, details, cache, declassify(b, {epp<-epp}), caps.capabilities);
    }
    
    private static void listBoardUsage{pp->*;pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp->*;pp<-*} out) throws (IOException{pp->*;pp<-*}) {
        if (out != null) {
            out.println("list bbHost bbPort bbID [meta metastring] [from timestamp] [to timestamp]");
        }
    }
    
    private static void listBoard{pp<-*}(principal pp, PrintStream[{pp->*;_<-_}]{pp<-*} out, String []{pp<-*} args) throws (IOException{pp->*}, IllegalArgumentException) 
     
        if (out == null || args == null) return;
        try {
            String bbHost = args[1];
            int bbPort = -1;
            try {
                bbPort = Integer.parseInt(args[2]);
            }
            catch (NumberFormatException{pp<-*} e) {
                listBoardUsage(pp, out);
                return;
            }

            String bbID = args[3];
            
            int index = 4;
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
            BBClientUtil bb = new BBClientUtil(bbHost, bbPort, bbID);
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

/*
 * Closure to retrieve a voter's capabilities, which requires the voter's authority.
 */
class RetrieveCapabilitiesClosure[principal P] implements Closure[P, {}] {
    final ElectionCache  electionCache; 
    final ElectionDetails  electionDetails; 
    final TellerDetails  tellerDetails;
    final String  voterName;
    final ElGamalPublicKey  voterEGPubKey;
    final ElGamalPrivateKey  voterEGPrivKey;
    final PublicKey  voterPubKey;
    final PrivateKey  voterPrivKey;
    
    RetrieveCapabilitiesClosure(ElectionCache  electionCache, 
                                ElectionDetails  electionDetails, 
                                TellerDetails  tellerDetails,
                                String  voterName,
                                ElGamalPublicKey  voterEGPubKey,
                                ElGamalPrivateKey  voterEGPrivKey,
                                PublicKey  voterPubKey,
                                PrivateKey  voterPrivKey) {
        super();
        this.electionCache = electionCache;
        this.electionDetails = electionDetails;
        this.tellerDetails = tellerDetails;
        this.voterName = voterName;
        this.voterEGPubKey = voterEGPubKey;
        this.voterEGPrivKey = voterEGPrivKey;
        this.voterPubKey = voterPubKey;
        this.voterPrivKey = voterPrivKey;
    }
    
    public Object{} invoke{}()  
        VoterCapabilities{P->P} vc = null;
        Exception ex = null;
        try {
            if (P equiv this.voterPubKey) {
                vc = Voter.retrieveCapabilities(electionCache, 
                                                electionDetails, 
                                                tellerDetails, 
                                                voterName, 
                                                voterEGPubKey, 
                                                voterEGPrivKey, 
                                                voterPubKey, 
                                                voterPrivKey);
            }
        }
        catch (Exception e) {
            ex = e;
        }
        Exception eex = endorse(ex, {P<-P});
        if (eex != null) return eex;
        
        return declassify(endorse(vc, {P->P; P<-P}), {});
    }
}
