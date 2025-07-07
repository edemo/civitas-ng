/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import java.net.*;
import jif.net.*;
import java.io.*;
import civitas.crypto.*;

/**
 * Responsible for listening on a port and starting appropriate
 * Registration teller sessions.
 */
public class RegisTeller {
    
    /**
     * Usage is:
     *     RegisTeller port publicKeyFile privateKeyFile
     */
    public static void main (String []  args) throws (Exception ) {
        // parse arguments
        if (args == null || args.length != 4) {
            throw new IllegalArgumentException("Usage: RegTeller cacheRootDir port pubKeyFile privKeyFile");
        }
        String cacheRootDir = null;
        int port = -1;
        String pubKeyFile = null;
        String privKeyFile = null;
        
        try {
            cacheRootDir = args[0];
            port = Integer.parseInt(args[1]);
            pubKeyFile = args[2];
            privKeyFile = args[3];
        }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Usage: RegTeller cacheRootDir port pubKeyFile privKeyFile");            
        }
        
        // get the registration teller keys

        PublicKey pubKey_ = null;
        PrivateKey privKey = null;
        try {
            pubKey_ = CryptoUtil.factory().publicKeyFromFile(pubKeyFile);
            privKey = CryptoUtil.factory().privateKeyFromFile(privKeyFile);
        }
        catch (NullPointerException imposs) { }
        catch (FileNotFoundException ignore) { }
        catch (IOException ignore) { }

        final PublicKey pubKey = pubKey_;
        // get the rtStore
        RTStore[pubKey] rtstore_ = null;
        try {
            rtstore_ = (RTStore[pubKey])PrincipalUtil.execute(pubKey, privKey, new RTStoreCreationClosure[pubKey](cacheRootDir), new label {pubKey<-pubKey});

        }
        catch (ClassCastException imposs) { }
        final RTStore[pubKey] rtstore = rtstore_;
        
        // open a server socket
        ServerSocket[ ] ss = new ServerSocket[ ](port);

        // serve connections
        SocketUtil.acceptConnections(new label  ,
                                     ss, 
                                     new RTSocketAcceptor(pubKey, privKey, rtstore));
        
    }            
}

class RTStoreCreationClosure[principal p] implements Closure[p, {p<-p}] {
    private final String  cacheRootDir;
    RTStoreCreationClosure(String  cacheRootDir) {
        this.cacheRootDir = cacheRootDir;
    }
    public Object{p<-p} invoke{p<-p}()  
        return new TransientStore[p](cacheRootDir);
    }
}

class RTSocketAcceptor implements SocketAcceptor {
    private final PublicKey  pubKey;
    private final PrivateKey  privKey;
    private final RTStore[pubKey]  rtstore;
    
    RTSocketAcceptor(final PublicKey  pubKey, PrivateKey  privKey, RTStore[pubKey]  rtstore) {
        this.pubKey = pubKey;
        this.privKey = privKey;
        this.rtstore = rtstore;
    }
    public void accept{}(InputStream[{}]{} input, OutputStream[{}]{} output) throws (IOException{}){
        final RTProtocol[pubKey] rtt = new RTProtocol[pubKey](privKey, 
                                              input, 
                                              output,
                                              rtstore);
        try {
            IOException e = (IOException)PrincipalUtil.execute(pubKey, privKey, rtt, new label {});
            if (e != null) throw e;
        }
        catch (ClassCastException imposs) { }
    }
}
