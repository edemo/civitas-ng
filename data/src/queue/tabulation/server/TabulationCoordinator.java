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
 * 
 */
public interface TabulationCoordinator {
    /**
     * Call when a tabulation request is received for an election.
     * This method returns true if the tabulation should proceed,
     * false otherwise.
     */
    public boolean tabulationNotification(ElectionID id);
    
    public void debugNotification(ElectionID id);
    
}