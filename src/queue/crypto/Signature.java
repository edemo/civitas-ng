/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto;

import java.io.*;

/**
 * Represents the a signature of a message. It can be verified
 * that this is the signature of a specific message by using
 * the method CryptoFactory.publicKeyVerifySignature.
 */
public interface Signature {
    public static final String  OPENING_TAG = "signature";
    public void toXML (  PrintWriter   sb) where   <= lbl;
}
