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
 * A revelation of a single element of a mix. 
 */
public abstract class MixElementRevelation implements XMLSerializable {
    /**
     * where did this element come from/go to?
     */
    protected final int  mapping;
    
    /**
     * Nonce that was combined with the mapping to produce the hash.
     */
    protected final byte{} []  nonce;

    MixElementRevelation(int  mapping, byte{} []  nonce) {
        this.mapping = mapping;
        this.nonce = nonce;
    }
    
    abstract boolean{} verify{}(ElGamalPublicKey{} key, int{} fromIndex, int{} toIndex, Mix{} fromMix, Mix{} toMix);

    
    public void toXML{}(PrintWriter[{}]{} sb) {
        toXML(new label {}, sb);
    }
    public abstract void toXML (  PrintWriter   sb)  this;_!:_;_:_} <= lbl;
    public static MixElementRevelation fromXML{}(Reader[{}]{} r)  throws (IllegalArgumentException{}, IOException{}) {
        final label lbl = new label {};
        MixElementRevelation rev = null;
        if (Util.isNextTag(  r, MixVoteElementRevelation.OPENING_TAG)) {
            return MixVoteElementRevelation.fromXMLsub(r);            
        }
        else {
            return MixCapabilityElementRevelation.fromXMLsub(r);
        } 
    }
}