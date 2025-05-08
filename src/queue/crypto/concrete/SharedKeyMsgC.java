/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto.concrete;

import civitas.crypto.SharedKeyMsg;


public class SharedKeyMsgC extends KeyMsgC implements SharedKeyMsg {
    public SharedKeyMsgC(String m) {
        super(m);
    }
    
    public SharedKeyMsgC(byte[] plaintext) {
        super(plaintext);
    }
}
