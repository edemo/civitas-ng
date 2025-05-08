/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.net.*;
import jif.net.*;
import java.io.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.common.*;
import civitas.crypto.*;


/**
 * Responsible for listening on a port and starting appropriate
 * Tabulation teller sessions.
 */
public class TabTeller {
    
    /**
     * Usage is:
     *     TabTeller port publicKeyFile privateKeyFile
     */
    public static void main (String []  args) throws (Exception ) {
        // parse arguments
        if (args == null || args.length != 4) {
            throw new IllegalArgumentException("Usage: TabTeller cacheRootDir port pubKeyFile privKeyFile");
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
            throw new IllegalArgumentException("Usage: TabTeller cacheRootDir port pubKeyFile privKeyFile");            
        }
        
        // get the tabulation teller keys

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
        // get the ttStore
        TTStore[pubKey] ttstore_ = null;
        try {
            ttstore_ = (TTStore[pubKey])PrincipalUtil.execute(pubKey, privKey, new TTStoreCreationClosure[pubKey](cacheRootDir), new label {pubKey<-pubKey});

        }
        catch (ClassCastException imposs) { }
        final TTStore[pubKey] ttstore = ttstore_;
        
        // open a server socket
        ServerSocket[ ] ss = new ServerSocket[ ](port);

        // serve connections
        SocketUtil.acceptConnections(new label  ,
                                     ss, 
                                     new TTSocketAcceptor(pubKey, privKey, ttstore));
        
    }        
}

class TTStoreCreationClosure[principal p] implements Closure[p, {p<-p}] {
    private final String  cacheRootDir;
    TTStoreCreationClosure(String  cacheRootDir) {
        this.cacheRootDir = cacheRootDir;
    }
    public Object{p<-p} invoke{p<-p}()  
        return new TransientStore[p](cacheRootDir);
    }

}

class TTSocketAcceptor implements SocketAcceptor {
    private final PublicKey  pubKey;
    private final PrivateKey  privKey;
    private final TTStore[pubKey]  ttstore;
    
    TTSocketAcceptor(final PublicKey  pubKey, PrivateKey  privKey, TTStore[pubKey]  ttstore) {
        this.pubKey = pubKey;
        this.privKey = privKey;
        this.ttstore = ttstore;
    }
    public void accept{}(InputStream[{}]{} input, OutputStream[{}]{} output) throws (IOException{}){
        final TTProtocol[pubKey] ttt = new TTProtocol[pubKey](privKey, 
                                              input, 
                                              output,
                                              ttstore,
                                              null,  // no coordinator
                                              null); // no log
        try {
            IOException e = (IOException)PrincipalUtil.execute(pubKey, privKey, ttt, new label {});
            if (e != null) throw e;
        }
        catch (ClassCastException imposs) { }
    }
}