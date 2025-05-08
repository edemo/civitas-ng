/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.registration.server;

import java.io.*;
import jif.util.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.common.*;
import civitas.crypto.*;


/**
 * Data class to hold voter capability shares
 */
public class VoterCapabilityShares[principal RT] {
    condition{RT<-RT} delivered = false;
    condition{RT<-RT} deliveryRequested = false;
    // ERASURE POLICY
    // DECLASSIFICATION POLICY
    final VoteCapabilityShare{RT->[RT /delivered/ *]deliveryRequested-> _; RT<-RT}[]  caps;
    
    public VoterCapabilityShares{RT<-RT}(int  size, ElGamalParameters  params) {
        // ERASURE POLICY
        // DECLASSIFICATION POLICY
        VoteCapabilityShare{RT->[RT /delivered/ *]deliveryRequested-> _; RT<-RT}[] cs = new VoteCapabilityShare[size<0?0:size];
        for (int i = 0; i < size; i++) {
            try {
                cs[i] = CryptoUtil.factory().generateVoteCapabilityShare(params);
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException imposs) { }
        }

        this.caps = cs;        
    }
}