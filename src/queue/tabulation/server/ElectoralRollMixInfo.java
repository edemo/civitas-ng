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
 * Information the server must track for 
 * electoral roll mixes.
 */
public class ElectoralRollMixInfo[principal TT] extends MixInfo[TT] {
    ElectoralRollMixInfo(final ConditionHolder[{TT<-TT}]  allMixesDone,
                         int{TT->TT;TT<-TT} []  permutation, 
                         ElGamalReencryptFactor{TT->TT;TT<-TT} []  reencryptFactors, 
                         byte{TT->TT;TT<-TT} [][]  permutationCommitmentFactors, 
                         byte{TT->TT allMixesDone.c->_;TT<-TT} []  mixNonce) {
        super(allMixesDone, permutation, reencryptFactors, permutationCommitmentFactors, mixNonce);
    }
    
    void permute{}(Mix{} inMix_, Mix{} outMix_, ElGamalPublicKey{} key_)  
        Mix inMix = endorse(inMix_, {TT<-TT}); 
        Mix outMix = endorse(outMix_, {TT<-TT});
        ElGamalPublicKey key= endorse(key_, {TT<-TT});
        
        if (!(inMix instanceof CapabilityMix) || !(outMix instanceof CapabilityMix) || key == null) {
            return;
        }

        CapabilityMix inM = (CapabilityMix)inMix;
        CapabilityMix outM = (CapabilityMix)outMix;
        
        int{TT->TT;TT<-TT} [] perm = this.permutation;
        // invert the permutation so it's easy to use
        int{TT->TT;TT<-TT} [] invPerm = Util.invertPermutation(new label {TT->TT;TT<-TT}, new label {TT<-TT}, perm);
        int size = endorse(inM.size(), {TT<-TT});
        for (int i = 0; i < size; i++) {
            byte{} []{} digest = null;
            ElGamalCiphertext reencrypt = null;
            try {
                byte{TT->TT;TT<-TT} [] nonce = permutationCommitmentFactors[i];
                int mapping = invPerm[i];
                digest = declassify(CryptoUtil.factory().messageDigest(new label {TT->TT;TT<-TT}, nonce, mapping, true), {TT<-TT});
                reencrypt = inM.getReencrypted(TT, mapping, reencryptFactors[i], key);
            }
            catch (IndexOutOfBoundsException imposs) { }
            catch (NullPointerException ignore) { }
            try {
                outMix.add(reencrypt, digest);                
            }
            catch (ClassCastException imposs) { }
        }
    }
    /**
     * if invert is false, then get the revelation for what i maps to. If invert is true,
     * get the revelation for what maps to i.
     */
    public MixElementRevelation{TT<-TT} getRevelation{TT<-TT}(int{TT<-TT} i, boolean{TT<-TT} invert)  
        int fromIndex = -1;
        int toIndex = -1;
        int ind = -1;
        try {
            if (invert) {
                fromIndex = Util.invertPermutation(new label {TT->TT;TT<-TT}, new label {TT<-TT}, permutation)[i];
                toIndex = i; 
                ind = fromIndex;
            }
            else {
                fromIndex = i;
                toIndex = permutation[i]; 
                ind = toIndex;
            }
        }
        catch (NullPointerException imposs) { }
        catch (ArrayIndexOutOfBoundsException imposs) { }
        
//        endorse({TT<-TT}) {
            ElGamalReencryptFactor rfi = null;  
            try {
                rfi = reencryptFactors[toIndex];
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            
            ElGamalReencryptFactor{TT<-TT} drfi = declassify(endorse(rfi, {TT<-TT;TT->TT}), {TT<-TT});

            
            byte{TT->TT;TT<-TT}  [] pfi = null;
            int length = 0;
            try {
                pfi = permutationCommitmentFactors[toIndex];
                length = pfi.length;
            }
            catch (NullPointerException imposs) { }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            int{TT<-TT} llength = declassify(endorse(length, {TT<-TT;TT->TT}), {TT<-TT});

            
            byte{}[]{TT<-TT} dpfi = new byte[llength<0?0:llength];  
            for (int j = 0; j < llength; j++) {
                byte{TT->TT;TT<-TT} b = 0;
                try {
                    b = pfi[j];
                }
                catch (NullPointerException imposs) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }

                byte bb = declassify(endorse(b, {TT<-TT;TT->TT}), {});
                try {
                    dpfi[j] = bb;
                }
                catch (NullPointerException imposs) { }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            if (drfi != null && dpfi != null) {
                return new MixCapabilityElementRevelation(declassify(endorse(ind, {TT<-TT;TT->TT}), {TT<-TT}), dpfi.clone(), drfi);
            }
            return null;
//        }
    }


}