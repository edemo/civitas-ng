/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.crypto;

import java.io.*;

public interface SharedKey extends jif.lang.Principal {
    public final static String  OPENING_TAG = "sharedKey";
    public void toXML (  PrintWriter   sb) where   <= lbl;
    public void toWire (  PrintWriter   sb) where   <= lbl;
}
