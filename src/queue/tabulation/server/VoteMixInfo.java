/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import until.util.*;
import civitas.crypto.*;
import civitas.common.*;

/**
 * Information the server must track for 
 * vote mixes.
 */
public class VoteMixInfo[principal TT] extends MixInfo[TT] {
    protected final ElGamalReencryptFactor{TT->TT;TT<-TT} []  choiceFactors;

    VoteMixInfo(final ConditionHolder[{TT<-TT}]  allMixesDone,
                int{TT->TT;TT<-TT}  []  permutation, 
                ElGamalReencryptFactor{TT->TT;TT<-TT} []  choiceFactors, 
                ElGamalReencryptFactor{TT->TT;TT<-TT} []  reencryptFactors, 
                byte{TT->TT;TT<-TT} [][]  permutationCommitmentFactors, 
                byte{TT->TT allMixesDone.c->_;TT<-TT} []  mixNonce) {
        this.choiceFactors = choiceFactors;
        super(allMixesDone, permutation, reencryptFactors, permutationCommitmentFactors, mixNonce);
    }
    void permute{}(Mix{} inMix_, Mix{} outMix_, ElGamalPublicKey{} key_)  
        Mix inMix = endorse(inMix_, {TT<-TT}); 
        Mix outMix = endorse(outMix_, {TT<-TT});
        ElGamalPublicKey key= endorse(key_, {TT<-TT});
        if (!(inMix instanceof VoteMix) || !(outMix instanceof VoteMix) || key == null) {
            return;
        }

        VoteMix inM = (VoteMix)inMix;
        VoteMix outM = (VoteMix)outMix;
        
        try {
            int{TT->TT;TT<-TT} [] perm = this.permutation;
            // invert the permutation so it's easy to use
            int{TT->TT;TT<-TT} [] invPerm = Util.invertPermutation(new label {TT->TT;TT<-TT}, new label {TT<-TT}, perm);
            int size = endorse(inM.size(), {TT<-TT});
            for (int i = 0; i < size; i++) {
                try {
                    byte{TT->TT;TT<-TT} [] nonce = permutationCommitmentFactors[i];
                    int mapping = invPerm[i];
                    byte{} [] digest = CryptoUtil.factory().messageDigest(new label {TT->TT;TT<-TT}, new label {TT->TT;TT<-TT}, nonce, mapping, true);
                    byte{} [] ddigest = declassify(digest, {TT<-TT});
                    outMix.add(inM.getReencrypted(TT, mapping, choiceFactors[i], reencryptFactors[i], key), ddigest);
                }
                catch (IndexOutOfBoundsException imposs) { }
                catch (ClassCastException imposs) { }
                catch (NullPointerException ignore) { }
            }
        }
        catch (NullPointerException ignore) { }

    }
    
    /**
     * if invert is false, then get the revelation for what i maps to. If invert is true,
     * get the revelation for what maps to i.
     */
    public MixElementRevelation{TT<-TT} getRevelation{TT<-TT}(int{TT<-TT} i, boolean{TT<-TT} invert)  
        
        int fromIndex = -1;
        int toIndex = -1;
        try {
            if (invert) {
                fromIndex = Util.invertPermutation(new label {TT->TT;TT<-TT}, new label {TT<-TT}, permutation)[i];
                toIndex = i; 
            }
            else {
                fromIndex = i;
                toIndex = endorse(permutation[i], {TT->TT;TT<-TT}); 
            }
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }

        int ind = declassify(endorse(invert?fromIndex:toIndex, {TT<-TT;TT->TT}), {TT<-TT});
        
        byte{TT->TT;TT<-TT} [] permutationCommitmentFactor = null;
        try {
            permutationCommitmentFactor = declassify(endorse(permutationCommitmentFactors[toIndex], {TT<-TT;TT->TT}), {TT<-TT});
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        

        if (permutationCommitmentFactor != null) {
            byte{TT<-TT}[] pcf = new byte[permutationCommitmentFactor.length];
            for (int j = 0; j < pcf.length; j++) {
                byte b = 0;
                try {
                    b = permutationCommitmentFactor[j];
                }
                catch (NullPointerException imposs) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                try {
                    pcf[j] = declassify(endorse(b, {TT<-TT;TT->TT}), {TT<-TT});
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            
            ElGamalReencryptFactor choiceFactor = null;
            try {
                choiceFactor = declassify(endorse(choiceFactors[toIndex], {TT<-TT;TT->TT}), {TT<-TT});
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
            ElGamalReencryptFactor reencryptFactor = null;
            try {
                reencryptFactor = declassify(endorse(reencryptFactors[toIndex], {TT<-TT;TT->TT}), {TT<-TT});
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }

            return new MixVoteElementRevelation(ind, pcf.clone(), choiceFactor, reencryptFactor);
        }

        return null;
    }

}