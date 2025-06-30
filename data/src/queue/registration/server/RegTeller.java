/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import jif.lang.LabelUtil;
import jif.lang.PrincipalUtil;
import jif.net.SocketAcceptor;
import jif.net.SocketUtil;
import civitas.crypto.CryptoUtil;
import civitas.crypto.PrivateKey;
import civitas.crypto.PublicKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.util.CivitasBigInteger;

/**
 * Implementation of a registration teller. Invoking main appropriately will
 * cause a listener to respond to registration requests on the specified port.
 * An administration port can optionally be specified, allowing administration
 * requests (such as getting experimental results) to be made to the registration
 * teller.
 *
 */
public class RegTeller {
    public static void main(final String[] args) throws Exception {
        if (args.length != 4 && args.length != 5) {
            throw new IllegalArgumentException(
            "Usage: RegTeller cacheRootDir port pubKeyFile privKeyFile [adminPort]");
        }
        String cacheRootDir = null;
        int port = -1;
        int adminPort = -1;
        String pubKeyFile = null;
        String privKeyFile = null;
        try {
            cacheRootDir = args[0];
            port = Integer.parseInt(args[1]);
            pubKeyFile = args[2];
            privKeyFile = args[3];
            if (args.length == 5) {
                adminPort = Integer.parseInt(args[4]);
            }
        }
        catch (final ArrayIndexOutOfBoundsException imposs) {

        }
        catch (final NumberFormatException e) {
            throw new IllegalArgumentException(
            "Usage: RegTeller cacheRootDir port pubKeyFile privKeyFile [adminPort]");
        }

        // get the registration teller keys

        PublicKey pubKey = null;
        PrivateKey privKey = null;
        try {
            pubKey = CryptoUtil.factory().publicKeyFromFile(pubKeyFile);
            privKey = CryptoUtil.factory().privateKeyFromFile(privKeyFile);
        }
        catch (NullPointerException imposs) { }
        catch (FileNotFoundException ignore) { }
        catch (IOException ignore) { }

        final RTStore rtstore = new TransientStore(pubKey).civitas$registration$server$TransientStore$(cacheRootDir);

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
        ServerSocket ss = new ServerSocket(port);

        // serve connections
        SocketUtil.acceptConnections(LabelUtil.singleton().noComponents(),
                                     ss, 
                                     new RTSocketAcceptor(pubKey, privKey, rtstore));

    }            
    static class AdminSocketListener implements Runnable {
        ServerSocket ass;
        public AdminSocketListener(ServerSocket ass) {
            this.ass = ass;
        }
        public void run() {
            while (true) {
                try {
                    Socket s = ass.accept();  

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
                }
            }            
        }

    }



    static class RTSocketAcceptor implements SocketAcceptor {
        private final PublicKey pubKey;
        private final PrivateKey privKey;
        private final RTStore rtstore;

        RTSocketAcceptor(PublicKey pubKey, PrivateKey privKey, RTStore rtstore) {
            this.pubKey = pubKey;
            this.privKey = privKey;
            this.rtstore = rtstore;
        }
        public void accept(InputStream input, OutputStream output) throws IOException {
            final RTProtocol rtt = new RTProtocol(pubKey).civitas$registration$server$RTProtocol$(privKey, 
                                                                                               input, 
                                                                                               output,
                                                                                               rtstore);
            try {
                IOException e = (IOException)PrincipalUtil.execute(pubKey, privKey, rtt, LabelUtil.singleton().noComponents());
                if (e != null) throw e;
            }
            catch (ClassCastException imposs) { }
        }
    }
}