/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.bboard.server;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import jif.lang.Label;
import jif.lang.LabelUtil;
import civitas.bboard.client.BBClientUtil;
import civitas.common.*;
import civitas.crypto.*;

/**
 * Bulletin board service.
 * The main method starts a service listening on a specified port. The service
 * provides bulletin board functionality.
 */
public class GenericBBS extends Protocol {
    /**
     * The length in bits of the names of elections. New election names are chosen
     * uniformly at random.
     */
    public static final int ELECTION_ID_LENGTH = 32;
    
    private final Collection<String> boardNames = new HashSet<String>();
    private final short port;
    private final Map<String, Long> createdBoards = new HashMap<String, Long>();

    private final PrivateKey bbPrivateKey;
    private final PublicKey bbPublicKey;
    
    final boolean log;
    private String logfilename;
    PrintWriter logoutput = null;
    
    private final BBStorage store;
    
    
    public static void main(String[] args) {
        if(args.length != 4 && args.length != 6) usage();

        short port = -1;
        File root = null;
        String pubKeyFile = null;
        String privKeyFile = null;
        PrintWriter logoutput = null;
        String logfilename = null;
        try {
            
            for (int ind = 0; ind < args.length; ind++) {
                String arg = args[ind];
                if (arg.equalsIgnoreCase("-log")) {
                    arg = args[++ind];
                    try {
                        logfilename = arg;
                        logoutput = new PrintWriter(arg);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                if (arg.startsWith("-")) usage();
                if (root == null) root = new File(arg);
                else if (port < 0) port = Short.parseShort(arg);
                else if (pubKeyFile == null) pubKeyFile = arg;
                else if (privKeyFile == null) privKeyFile = arg;
            }
        }
        catch (NumberFormatException e) {
            usage();
        }        
        try {
            PublicKey pubKey = CryptoUtil.factory().publicKeyFromFile(pubKeyFile);
            PrivateKey privKey = CryptoUtil.factory().privateKeyFromFile(privKeyFile);
            GenericBBS f = new GenericBBS(root, port, pubKey, privKey, logoutput, logfilename);
            ServerSocket v = new ServerSocket(f.port);
            for(;;) {
                try {
                    new Thread(f.new ServiceHandle(v.accept())).start();
                } catch (IOException e) { e.printStackTrace(); } 
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void usage() {
        System.out.println("Usage: java GenericBBS [-log logfile] rootdir port publicKeyFile privateKeyFile");
        System.exit(1);        
    }
        
    void reopenLogoutput() {
        if (logoutput != null) {
            logoutput.close();
            String filename = logfilename + "-errormode";
            int i = 0;
            String f = filename + i;
            while (new File(f).exists()) {
                i++;
                f = filename + i;
            }
            try {
                logoutput =  new PrintWriter(new FileOutputStream(f));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    public GenericBBS(File root, short port, PublicKey pubKey, PrivateKey privKey, PrintWriter log, String logfilename) throws IOException {  
        this.store = new FileBBStorage(root, this); 
//        this.store = new TransientBBStorage(); 
        this.port = port;
        this.bbPublicKey = pubKey;
        this.bbPrivateKey = privKey;
        this.logoutput = log;
        this.logfilename = logfilename;
        this.log = (log != null);
    }
    
    void addBoardName(String bbid) {
        this.boardNames.add(bbid);
    }
        
    // add the board name to our list
    // and create a place for it in the file structure
    private void newBoard(BufferedReader in, PrintWriter out) throws IOException {
        // read in the public key
        PublicKey publicKey = null; 
        try {
            publicKey = CryptoUtil.factory().publicKeyFromXML(LabelUtil.singleton().noComponents(), in);
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }

        // get a new board name
        String newBoardName;
        do {
            byte[] bs = CryptoUtil.factory().freshNonce(LabelUtil.singleton().noComponents(), ELECTION_ID_LENGTH);  
            newBoardName = new BigInteger(bs).abs().toString(16);
        }
        while (boardNames.contains(newBoardName));
        store.storeAcceptance(newBoardName, publicKey);
        boardNames.add(newBoardName);
        createdBoards.put(newBoardName, System.currentTimeMillis());
        out.println(newBoardName);
        out.flush();        
    }
    
    // close a board for posting
    private void closeBoard(BufferedReader in, PrintWriter out) throws IOException {
        Label lbl = LabelUtil.singleton().noComponents();
        String bbid = in.readLine();
        
        // read in the bulletinboardid to post hash to, if it exists
        ElectionID postHashTo = null;
        int numVoterBlocks = 0;
        if (!Util.isNextTag(  in, Signature.OPENING_TAG)) {
            postHashTo = ElectionID.fromXML(  in);
            numVoterBlocks = Integer.parseInt(in.readLine());
        }
        
        // read in the signature
        Signature sig = null;
        try {
            sig = CryptoUtil.factory().signatureFromXML(  in);
        }
        catch (IllegalArgumentException e) { 
            throw new IOException(e.getMessage());
        }
        
        PublicKey owner = store.retrieveOwnerPublicKey(bbid);
        
        boolean res = false;
        if (owner != null) {
            try {
                PublicKeyMsg msg = CryptoUtil.factory().publicKeyMsg(bbid);
                res = CryptoUtil.factory().publicKeyVerifySignature(owner, sig, msg);
            }
            catch (CryptoException e) { 
                throw new IOException(e.getMessage());
            }
        }
        if (res) {
            // close the board.
            store.closeBoard(bbid);
            
            // post hash to the other board
            if (postHashTo != null) {
                BBClientUtil bbcu = new BBClientUtil().civitas$bboard$client$BBClientUtil$(postHashTo);
                String[] hashes = new String[numVoterBlocks];
                for (int i = 0; i < numVoterBlocks; i++) {
                    hashes[i] = computeHash(bbid, VoterSubmission.meta(i), null, null);                    
                }
                BoardClosedContentCommitment bccc = new BoardClosedContentCommitment().civitas$common$BoardClosedContentCommitment$(  postHashTo, store.retrieveIndex(bbid), hashes);
                bbcu.post(BoardClosedContentCommitment.META, bccc, bbPrivateKey);
            }
        }
        out.println(Boolean.toString(res));
        out.flush();
    }
        
    // create a file under the boardName's directory
    // whose name is generated based on the hash and timestamp
    // containing the body of the message
    private void post(BufferedReader in, PrintWriter out) throws IOException {                
        // read boardname
        String bbName = in.readLine();
        // read data, get timestamp
        String meta = protocolInputString(in);
        
        // read in the message
        // The message is currently stored in memory, in a string; 
        // a performance improvement could be to write the message (or
        // the post part of it) directly to at file, perhaps saving
        // some memory resources.
        StringWriter sw = new StringWriter();
        protocolInputToSentinal(in, new PrintWriter(sw));        
        String mesg = sw.toString();
        sw = new StringWriter();
        protocolInputToSentinal(in, new PrintWriter(sw));        
        String sign = sw.toString();
        
        if (!this.boardNames.contains(bbName)) return;

        
        long t = -1;
        if (!store.isBoardClosed(bbName)) {
            t = store.post(bbName, meta, mesg, sign);
            // log the meta that was posted
            if (log) {
                logoutput.println("post: " + t + ' '+ meta);
                if (logoutput.checkError()) {
                    reopenLogoutput();
                    logoutput.println("error: while posting file " + meta);
                }
            }
        }
        else {
            System.err.println("Post attempted for closed board " + bbName);
        }
        
        // send timestamp back to client
        out.println(t);
        out.flush();    
    }
    private class RetrieveProcessor implements BBStorage.PostProcessor {
        final PrintWriter out;
        final MessageDigest md;
        public RetrieveProcessor(PrintWriter out, MessageDigest md) {
            this.out = out;
            this.md = md;
        }
        public void processPost(BBStoragePost post) throws IOException {
            if (out != null) {
                // send data, message and sig.
                out.println(post.timestamp);
                protocolOutputString(out, post.meta);
                out.print(post.msg);
                out.print(post.sig);
                out.print("<EOP>");
            }
            if (md != null) {
                md.update(post.timestamp); 
                md.update(post.meta);
                md.update(post.msg);
                md.update(post.sig);
            }            
        }        
    }
    // concatenate the contents of all files under the board's directory
    private void retrieve(BufferedReader in, PrintWriter out, boolean signed) throws IOException {
        // read board name and gather all posts on it
        String bbName = in.readLine();
        
        if (!this.boardNames.contains(bbName)) return;        
        Label lbl = LabelUtil.singleton().noComponents();
        MessageDigest md = null;
        if (signed) {
            md = CryptoUtil.factory().messageDigest(lbl);
        }
        
        // send the name
        out.println(bbName);

        RetrieveProcessor pp = new RetrieveProcessor(out, md);        
        store.processPosts(pp, bbName, null, null, null);

        // let the client know the list has finished.
        out.println("<END>");
        if (signed) {
            // now output the hash and signature.
            String hash = Util.constBytesToString(  md.digest());
            protocolOutputString(out, hash);
            try {
                PublicKeyMsg hashMsg = CryptoUtil.factory().publicKeyMsg(hash);
                CryptoUtil.factory().signature(bbPrivateKey, hashMsg).toXML(LabelUtil.singleton().noComponents(), out);
            }
            catch (NullPointerException imposs) { }
            catch (CryptoException e) { 
                throw new IOException(e.getMessage());
            }
        }
        out.flush();
                
    }
    
    private void retrieve_params(BufferedReader in, PrintWriter out, boolean signed) throws IOException {
        // read board name and gather all posts on it
//        long start = System.currentTimeMillis(), end;
        String bbName = in.readLine();
        if (!this.boardNames.contains(bbName)) return;
        
        String metaCriteria = protocolInputString(in);
        String fromTime = in.readLine();
        String toTime = in.readLine();
        

        Label lbl = LabelUtil.singleton().noComponents();
        MessageDigest md = null;
        if (signed) {
            md = CryptoUtil.factory().messageDigest(lbl);
        }

//        System.err.println("BBS read params " + ((end = System.currentTimeMillis()) - start)); start = end;
        // send the name
        out.println(bbName);

        // log the meta that was posted
        if (log) {
            logoutput.println("retrieve: " + System.currentTimeMillis() + ' ' + metaCriteria);
            if (logoutput.checkError()) {
                reopenLogoutput();
                logoutput.println("error: while retrieving " + metaCriteria);
            }
        }

        RetrieveProcessor pp = new RetrieveProcessor(out, md);        
        store.processPosts(pp, bbName, metaCriteria, fromTime, toTime);
//        System.err.println("BBS process posts " + ((end = System.currentTimeMillis()) - start)); start = end;

        // let the client know the list has finished.
        out.println("<END>");

        if (signed) {
            // now output the hash and signature.
            String hash = Util.constBytesToString(  md.digest());
            protocolOutputString(out, hash);
            try {
                PublicKeyMsg hashMsg = CryptoUtil.factory().publicKeyMsg(hash);
                CryptoUtil.factory().signature(bbPrivateKey, hashMsg).toXML(LabelUtil.singleton().noComponents(), out);
//                System.err.println("BBS hash " + ((end = System.currentTimeMillis()) - start)); start = end;
            }
            catch (NullPointerException imposs) { }
            catch (CryptoException e) { 
                throw new IOException(e.getMessage());
            }
        }
        out.flush();
//        System.err.println("BBS finished" + ((end = System.currentTimeMillis()) - start)); start = end;
    }
    
    private void retrieve_hash(BufferedReader in, PrintWriter out) throws IOException {
        // read board name and gather all posts on it
        String bbName = in.readLine();
        String metaCriteria = protocolInputString(in);
        String fromTime = in.readLine();
        String toTime = in.readLine();

        String hash = computeHash(bbName, metaCriteria, fromTime, toTime);

        protocolOutputString(out, hash);
        try {
            PublicKeyMsg hashMsg = CryptoUtil.factory().publicKeyMsg(hash);
            CryptoUtil.factory().signature(bbPrivateKey, hashMsg).toXML(LabelUtil.singleton().noComponents(), out);
        }
        catch (NullPointerException imposs) { }
        catch (CryptoException e) { 
            throw new IOException(e.getMessage());
        }
        out.flush();

    }
    private String computeHash(String bbName, String metaCriteria, String fromTime, String toTime) throws IOException {
        Label lbl = LabelUtil.singleton().noComponents();
        MessageDigest md = CryptoUtil.factory().messageDigest(lbl);
        RetrieveProcessor pp = new RetrieveProcessor(null, md);        
        store.processPosts(pp, bbName, metaCriteria, fromTime, toTime);

        String hash = Util.constBytesToString(  md.digest());   
        
        return hash;
    }
    private void requestParticipation(BufferedReader in, PrintWriter out) throws IOException {
        Label lbl = LabelUtil.singleton().noComponents();
        ElectionDetails elecDetails = ElectionDetails.fromXML(  in);
        TellerDetails tellerDetails = TellerDetails.fromXML(  in);
        
        // decide whether to participate
        boolean result = false;
        if (elecDetails != null && tellerDetails != null) {
            result = decideParticipation(elecDetails, tellerDetails);
        }
        out.println(result?"yes":"no");
        out.flush();        
    }
    private void confirmParticipation(BufferedReader in, PrintWriter out) throws IOException {
        Label lbl = LabelUtil.singleton().noComponents();
        ElectionDetails elecDetails = ElectionDetails.fromXML(  in);
        @SuppressWarnings("unused")
		TellerDetails tellerDetails = TellerDetails.fromXML(  in);
        
        int index = -1;
        try {
            index = Integer.parseInt(in.readLine());
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }

        // Check that the election is one that we have already agreed to
        if (elecDetails != null && elecDetails.electionID != null && isAcceptedElection(elecDetails.electionID.id)) {
            // store the needed information about election and teller details.
            store.storeIndex(elecDetails.electionID.id, index);
            out.println(true);                
        }
        else {
            out.println(false);                                
        }
        
        out.flush();        
    }
    /**
     * Decide whether or not this BB will participate in an election
     */
    protected boolean decideParticipation(ElectionDetails elecDetails, TellerDetails tellers) {
        if (elecDetails == null || elecDetails.electionID == null) return false;

        // check that the election status is appropriate
        boolean decision = true;
        try {
            ElectionCache electionCache = new ElectionCache().civitas$common$ElectionCache$();
            if (ElectionUtil.retrieveElectionStatus(bbPublicKey, elecDetails.electionID, electionCache) != ElectionUtil.STATUS_DEFINED) { 
                decision = false;
            }
        }
        catch (IOException e) {
            // couldn't contact the admin BB server. Seems like a bad deal...
            decision = false;
        }
                
        
        if (decision) {
            try {
                store.storeAcceptance(elecDetails.electionID.id, elecDetails.supervisor);
                boardNames.add(elecDetails.electionID.id);
                createdBoards.put(elecDetails.electionID.id, System.currentTimeMillis());
            }
            catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return decision;
    }
    private boolean isAcceptedElection(String id) {
        return boardNames.contains(id);
    }
    
    /**
     * Just indicate if this bulletin board is alive
     */
    private void heartbeat(BufferedReader in, PrintWriter out) throws IOException {
        out.println(true);                        
        out.flush();        
    }
    
    private class ExperimentResultsProcessor implements BBStorage.PostProcessor {
        long finalizeTime = 0, startTime = 0, stopTime = 0, creationTime = 0;
        long tellerResultsTime = 0;
        String requestedMeta;
        String electionResults = null;
        public void processPost(BBStoragePost post) throws IllegalArgumentException, IOException {
            Label lbl = LabelUtil.singleton().noComponents();
            if (post.meta.equals(ElectionEvent.META)) {
                ElectionEvent e = ElectionEvent.fromXML(  new StringReader(post.msg));
                if (ElectionEvent.EVENT_KIND_FINALIZE.equals(e.kind)) {
                    finalizeTime = post.timestamp;
                    ElectionEventFinalize fe = (ElectionEventFinalize)e;
                    StringWriter sb = new StringWriter();
                    fe.tally.toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
                    electionResults = sb.toString();                    
                }
                else if (ElectionEvent.EVENT_KIND_START.equals(e.kind)) {
                    startTime = post.timestamp;
                }
                else if (ElectionEvent.EVENT_KIND_STOP.equals(e.kind)) {
                    stopTime = post.timestamp;
                }
            }
            else {
                if (post.timestamp > tellerResultsTime) {
                    tellerResultsTime = post.timestamp;
                }                    
            }

        }
    }
    /**
     * Hack to get some experiment results reported
     */
    private void experimentResults(BufferedReader in, PrintWriter out) throws IOException {
        // which election?
        for (String bbid : createdBoards.keySet()) {
            ExperimentResultsProcessor erp = new ExperimentResultsProcessor();
            // get the election event times
            erp.creationTime = createdBoards.get(bbid);
            store.processPosts(erp, bbid, ElectionEvent.META, null, null);

            // get the time the last teller results were posted
            for (int i = 0; i < 10; i++) {
                String meta = ElectionResults.metaForTeller(i);
                erp.requestedMeta = meta;
                store.processPosts(erp, bbid, meta, null, null);
            }

            // output stuff.
            out.println("election : " + bbid);
            out.println("storageDir : " + store.storageDir(bbid));
            out.println("creationTime : " + erp.creationTime);
            out.println("startTime : " + erp.startTime);
            out.println("stopTime : " + erp.stopTime);
            out.println("lastTellerResultTime : " + erp.tellerResultsTime);
            out.println("electionFinalize : " + erp.finalizeTime);
            out.println("elapsedCreationToStop : " + (erp.stopTime - erp.creationTime));
            out.println("elapsedStopToResults : " + (erp.tellerResultsTime - erp.stopTime));            
            out.println();            
            out.println("electionResults : " + erp.electionResults);            
        }
        out.println("<END>");
        out.flush();
        
    }
        
    private class ServiceHandle implements Runnable {
        private Socket s;
        public ServiceHandle(Socket s) { this.s = s; }
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream());
                try {
                    String action = in.readLine();
                    if(action == null) return;

                    if(action.equals("NEWBOARD"))                      newBoard(in,out);
                    else if(action.equals("CLOSEBOARD"))               closeBoard(in,out);
                    else if(action.equals("POST"))                     post(in,out);
                    else if(action.equals("RETRIEVE_UNSIGNED"))        retrieve(in,out,false);
                    else if(action.equals("RETRIEVE_SIGNED"))          retrieve(in,out,true);
                    else if(action.equals("RETRIEVE_PARAMS_UNSIGNED")) retrieve_params(in,out,false);
                    else if(action.equals("RETRIEVE_PARAMS_SIGNED"))   retrieve_params(in,out,true);
                    else if(action.equals("RETRIEVE_HASH"))            retrieve_hash(in,out);
                    else if(action.equals("REQUEST_PARTICIPATION"))    requestParticipation(in,out);                    
                    else if(action.equals("CONFIRM_PARTICIPATION"))    confirmParticipation(in,out);
                    else if(action.equals("HEARTBEAT"))                heartbeat(in,out);
                    else if(action.equals("EXPERIMENT_RESULTS"))       experimentResults(in,out);
                    else { System.err.println("Invalid Action Type: "+action); }
                }
                finally {
                    in.close();
                    out.close();
                    s.close();
                }
            }
            catch (RuntimeException e) { 
                e.printStackTrace(); 
                if (log) {
                    logoutput.println("error: " + e.getMessage());
                    e.printStackTrace(logoutput);
                }
                throw e;
            }
            catch (IOException e) { 
                e.printStackTrace(); 
                if (log) {
                    logoutput.println("error: " + e.getMessage());
                    e.printStackTrace(logoutput);
                }
            }
        }
    }    
}

class BBStoragePost {
    public BBStoragePost(long timestamp, String meta, String msg, String sig) {
        this.timestamp = timestamp;
        this.meta = meta;
        this.msg = msg;
        this.sig = sig;
    }
    public long timestamp;
    public String meta;
    public String msg;
    public String sig;
}
