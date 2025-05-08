/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import civitas.crypto.*;
import civitas.common.*;

import until.util.*;

/**
 * Superclass for information the server must trak for 
 * vote mixes and capability mixes.
 */
public abstract class MixInfo[principal TT] {
    public final ConditionHolder[{TT<-TT}]  allMixesDone;
    
    protected final int{TT->TT;TT<-TT}  []  permutation;
    protected final ElGamalReencryptFactor{TT->TT;TT<-TT} []  reencryptFactors;
    protected final byte{TT->TT;TT<-TT} [][]  permutationCommitmentFactors; 
    
    // DECLASSIFICATION POLICY
    protected final byte{TT:TT allMixesDone.c->_ ; TT<-TT} []  mixNonce; 
        
    MixInfo(final ConditionHolder[{TT<-TT}]  allMixesDone,
            int{TT->TT;TT<-TT} []  permutation, 
            ElGamalReencryptFactor{TT->TT;TT<-TT} []  reencryptFactors, 
            byte{TT->TT;TT<-TT}  [][]  permutationCommitmentFactors, 
            byte{TT->TT allMixesDone.c->_ ;TT<-TT}const []  mixNonce) {
        this.allMixesDone = allMixesDone;
        this.permutation = permutation;
        this.reencryptFactors = reencryptFactors;
        this.permutationCommitmentFactors = permutationCommitmentFactors;
        this.mixNonce = mixNonce;
    }

    int length() {
        if (permutation != null) return permutation.length;
        return 0;
    }
    
    abstract void permute{}(Mix{} inMix, Mix{} outMix, ElGamalPublicKey{} key) where caller(TT);
    
    /**
     * if invert is false, then get the revelation what i maps to. If invert is true,
     * get the revelation for what maps to i. Require the trust of TT, since a declassification
     * is needed.
     */
    abstract MixElementRevelation{TT<-TT} getRevelation{TT<-TT}(int{TT<-TT} i, boolean{TT<-TT} invert) where caller(TT);
}