
/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import civitas.crypto.*;

import java.io.*;

/**
 * A holder for a VoterCapabilitySharesAndProof
 */
public class VoterCapabilitySharesAndProofHolder {
    
    public final VoterCapabilitySharesAndProof{voter->voter} sharesAndProof;  
    public final principal  voter;  

    public VoterCapabilitySharesAndProofHolder{sharesAndProof}(
            VoterCapabilitySharesAndProof{voter->voter} sharesAndProof,
                    principal  voter) {
        this.voter = voter;
        this.sharesAndProof = sharesAndProof;
    }
}