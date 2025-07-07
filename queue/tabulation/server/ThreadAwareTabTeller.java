/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jif.lang.LabelUtil;
import jif.net.SocketAcceptor;
import jif.net.SocketUtil;
import civitas.common.ElectionID;
import civitas.crypto.CryptoUtil;
import civitas.crypto.PrivateKey;
import civitas.crypto.PublicKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.util.CivitasBigInteger;

/**
 * Implementation of a tabulation teller. Invoking main appropriately will
 * cause a listener to respond to tabulation requests on the specified port.
 * An administration port can optionally be specified, allowing administration
 * requests (such as getting experimental results) to be made to the tabulation
 * teller.
 * The ThreadAwareTabTeller ensures that there is at most
 * one thread active for any given election at a time.
 * If a request to tabulate an election is received while
 * there is already an active thread for that election,
 * an interrupt is sent to that thread (in case it is
 * sleeping, waiting for another tabulation teller
 * to post information).
 */
public class ThreadAwareTabTeller {

    private static final boolean DEBUG = false;
    private static final boolean DEBUG_LOGGING = false;
    public static void main(String[] args) {
        // parse arguments
        if (args.length != 5 && args.length != 6) {
            usage();
        }
        String cacheRootDir = null;
        File rootDir = null;
        int port = -1;
        int adminPort = -1;
        String pubKeyFile = null;
        String privKeyFile = null;

        try {
            rootDir = new File(args[0]);
            cacheRootDir = args[1];
            port = Integer.parseInt(args[2]);
            pubKeyFile = args[3];
            privKeyFile = args[4];
            if (args.length == 6) {
                adminPort = Integer.parseInt(args[5]);
            }
        }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        catch (NumberFormatException e) {
            usage();            
        }

        // get the tabulation teller keys from the files specified
        PublicKey pubKey_ = null;
        PrivateKey privKey = null;
        try {
            pubKey_ = CryptoUtil.factory().publicKeyFromFile(pubKeyFile);
            privKey = CryptoUtil.factory().privateKeyFromFile(privKeyFile);
        }
        catch (NullPointerException imposs) { }
        catch (FileNotFoundException ignore) { }
        catch (IOException e) { 
            e.printStackTrace();
        }

        final PublicKey pubKey = pubKey_;
        // get the ttStore
        TTStore ttstore = new FileTTStore(pubKey, rootDir, cacheRootDir); 

        if (adminPort > 0) {
            // open a admin port
            try {
                ServerSocket ass = new ServerSocket(adminPort);
                AdminSocketListener asl = new AdminSocketListener(ass);
                new Thread(asl, "adminSocketListener").start();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

        // open a server socket
        try {
            ServerSocket ss = new ServerSocket(port);

            PrintStream debugLog = null;
            if (DEBUG_LOGGING) debugLog = new TimestampingPrintStream(new File(rootDir, "debugLog-" + port));
            // serve connections
            SocketUtil.acceptConnections(LabelUtil.singleton().noComponents(),
                                         ss, 
                                         new TTSocketAcceptor(pubKey, privKey, ttstore, port, debugLog));
        }
        catch (IOException e) {
            e.printStackTrace();
        }        
    }

    private static void usage() {
        System.err.println("Usage: TabTeller rootDir cacheRootDir port pubKeyFile privKeyFile [adminPort]");
        System.exit(1);
    }        

    /**
     * Listener for administration requests.
     */
    static class AdminSocketListener implements Runnable {
        ServerSocket ass;
        public AdminSocketListener(ServerSocket ass) {
            this.ass = ass;
        }
        public void run() {
            while (true) {
                try {
                    if (DEBUG) System.out.println("Listening on admin port " + ass.getLocalPort());
                    Socket s = ass.accept();  
                    if (DEBUG) System.out.println("Got admin socket: " + s.getPort());

                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintStream out = new PrintStream(s.getOutputStream());

                    String cmd = in.readLine();
                    if (cmd.equalsIgnoreCase("EXPERIMENT_RESULTS")) {
                        out.println("numModPows : " + CivitasBigInteger.numModPows());
                        out.println("numElGamalEncs : " + CryptoFactoryC.numElGamalEncs());
                        out.println("numElGamalDecs : " + CryptoFactoryC.numElGamalDecs());
                        out.println("numElGamalDecShare : " + CryptoFactoryC.numElGamalDecShare());
                        out.println("numElGamalReencs : " + CryptoFactoryC.numElGamalReencs());
                        out.println("numElGamalSignedEncs : " + CryptoFactoryC.numElGamalSignedEncs());
                        out.println("numElGamalVerifies : " + CryptoFactoryC.numElGamalVerifies());
                        out.println("numSharedKeyEncs : " + CryptoFactoryC.numSharedKeyEncs());
                        out.println("numSharedKeyDecs : " + CryptoFactoryC.numSharedKeyDecs());
                        out.println("numPublicKeyEncs : " + CryptoFactoryC.numPublicKeyEncs());
                        out.println("numPublicKeyDecs : " + CryptoFactoryC.numPublicKeyDecs());                        
                        out.println("numPublicKeySign : " + CryptoFactoryC.numPublicKeySign());
                        out.println("numPublicKeyVerifySig : " + CryptoFactoryC.numPublicKeyVerifySig());

                        out.println("<END>");
                    }
                    else if (cmd.equalsIgnoreCase("HEARTBEAT")) {
                        out.println(true);
                    }
                    in.close();
                    out.close();

                }
                catch (Exception e) {
                    // recover silently
                    if (DEBUG) e.printStackTrace();
                }
            }            
        }

    }

    /**
     * Listener for tabulation teller requests.
     */
    static class TTSocketAcceptor implements SocketAcceptor, TabulationCoordinator {
        private final PublicKey pubKey;
        private final PrivateKey privKey;
        private final TTStore ttstore;
        private final int port; // for debugging purposes only
        private final PrintStream debugLog;

        // map from electionIDs to the Thread that is actively processing it.
        private static final Map<String, Thread> electionIDsInProcess = new ConcurrentHashMap<String, Thread>();

        // Thread local variable of the electionID that the thread is currently processing.
        // Used to make sure we can clean up electionIDsInProcess as appropriate.
        private static final ThreadLocal<String> electionId = new ThreadLocal<String>();

        TTSocketAcceptor(PublicKey pubKey, PrivateKey privKey, TTStore ttstore, int port, PrintStream debugLog) {
            this.pubKey = pubKey;
            this.privKey = privKey;
            this.ttstore = ttstore;
            this.port = port;
            this.debugLog = debugLog;
        }

        public boolean tabulationNotification(ElectionID electionID) {
            if (DEBUG) {
                System.err.println(Thread.currentThread().toString() + " on port " + port + " notified for " + electionID);                
            }
            StringWriter sb = new StringWriter();
            electionID.toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
            String id = sb.toString();
            Thread otherThread = electionIDsInProcess.get(id);
            if (otherThread != null) {
                // a thread is already processing the election, so 
                // we don't need to process this request 

                // notify the other thread, in case it is sleeping
                try {
                    if (DEBUG) {
                        System.err.println(Thread.currentThread().toString() + " on port " + port + " is interrupting " + otherThread);                
                    }
//                    if (debugLog != null) {
//                        StackTraceElement[] stack = otherThread.getStackTrace();
//                        try {
//                            int i = 0;
//                            while (stack[i].getClassName().indexOf("civitas.tab") < 0) {
//                                debugLog.println("  -" + stack[i].getClassName() + ":" + stack[i].getMethodName() + " " + stack[i].getLineNumber());
//                                i++;
//                            }
//                            debugLog.println("  -" + stack[i].getClassName() + ":" + stack[i].getMethodName() + ":" + stack[i].getLineNumber());
//                            debugLog.println("  -" + stack[i+1].getClassName() + ":" + stack[i+1].getMethodName() + ":" + stack[i+1].getLineNumber());
//                        }
//                        catch (RuntimeException e) {
//                            //e.printStackTrace();
//                        }
//                    }
                    otherThread.interrupt();
                }
                catch (SecurityException e) {
                    e.printStackTrace();
                }
                return false;
            }
            if (DEBUG) {
                System.err.println(Thread.currentThread().toString() + " on port " + port + " is becoming the active thread for election " + electionID);                
            }

            electionIDsInProcess.put(id, Thread.currentThread());
            electionId.set(id);
            return true;
        }

        public void debugNotification(ElectionID electionID) {
            if (debugLog != null) {
                
                debugLog.println("Total memory: " + Runtime.getRuntime().totalMemory());
                debugLog.println("Free memory " + Runtime.getRuntime().freeMemory());
                debugLog.println("Max memory " + Runtime.getRuntime().maxMemory());
                
                StringWriter sb = new StringWriter();
                electionID.toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
                String id = sb.toString();



                Thread otherThread = electionIDsInProcess.get(id);
                if (otherThread != null) {
                    try {
                        debugLog.println("Active thread's status is " + otherThread.getState());
                        debugLog.println("Active thread is alive? " + otherThread.isAlive());

                        StackTraceElement[] stack = otherThread.getStackTrace();
                        int i = 0;
                        while (i < stack.length) {
                            debugLog.println("  -" + stack[i].getClassName() + ":" + stack[i].getMethodName() + " " + stack[i].getLineNumber());
                            i++;
                        }
                    }
                    catch (RuntimeException e) {
                        //e.printStackTrace();
                    }
                }
            }
        }

        public void accept(InputStream input, OutputStream output) throws IOException {
            final TTProtocol ttt = new TTProtocol(pubKey).civitas$tabulation$server$TTProtocol$(privKey, 
                                                                                             input, 
                                                                                             output,
                                                                                             ttstore,
                                                                                             this, // this object is the coordinator
                                                                                             debugLog); 
            
            try {
                Object o = ttt.invoke();
                if (o != null && o instanceof Exception) {
                    Exception e = (Exception)o;
                    e.printStackTrace();
                    if (debugLog != null) e.printStackTrace(debugLog);
                }
            }
            finally {
                String id = electionId.get();                
                if (id != null && electionIDsInProcess.get(id).equals(Thread.currentThread())) { 
                    // this thread was handling the election, so clean up the
                    // electionIDsInProcess map.
                    electionIDsInProcess.remove(id);
                }
                if (DEBUG) {
                    System.err.println(Thread.currentThread().toString() + " on port " + port + " finished (election id: " + id + ")");                
                }
                electionId.set(null);                
            }
        }
    }
    /**
     * PrintStream that prints the time elapsed since the last println was called. Used
     * for debugging.
     */
    public static class TimestampingPrintStream extends PrintStream {
        private long last = -1;
        public TimestampingPrintStream(OutputStream out) {
            super(out);
        }

        public TimestampingPrintStream(File file) throws FileNotFoundException {
            super(file);
        }

        public void println(String x) {
            long next = System.currentTimeMillis();
            if (last < 0) last = next;
            super.print((next-last) + ": ");
            super.println(x);
            this.last = next;
        }

    }
    
}