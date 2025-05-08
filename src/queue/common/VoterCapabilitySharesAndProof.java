
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
 * A collection of VoteCapability and proofs. This is the data structure that
 * a registration teller sends to a voter when the voter contacts the
 * registration teller.
 */
public class VoterCapabilitySharesAndProof implements XMLSerializable {
    public static final String  OPENING_TAG = "voterCapabilitiesAndProof";
    
    //  the order of these capabilities is important: the ith capability must
    // be used for the ith context, i.e., the ith produced vote.
    public final VoteCapabilityShare   []  capabilities;  

    public final ElGamalReencryptFactor   []  rencryptFactors;  
    public final ElGamalProofDVR   []  proofs;  
    
    public final int  voterBlock;

    public VoterCapabilitySharesAndProof(VoteCapabilityShare   []  capabilities,
                                     ElGamalReencryptFactor   []  rencryptFactors,  
                                     ElGamalProofDVR   []  proofs,
                                     int  voterBlock) {
        this.capabilities = capabilities;
        this.rencryptFactors = rencryptFactors;
        this.proofs = proofs;
        this.voterBlock = voterBlock;
    }
           
    public boolean{_<-_;_->_;postedCapabilities;voterPublicKey;tabTellerSharedPublicKey;voterName;tellerIndex} 
         verify(ElGamalSignedCiphertext{}[] postedCapabilities,
            ElGamalPublicKey voterPublicKey, ElGamalPublicKey tabTellerSharedPublicKey,
            String voterName, int tellerIndex) {
        if (capabilities == null || rencryptFactors == null || proofs == null ||
                capabilities.length != rencryptFactors.length ||
                capabilities.length != proofs.length ||
                postedCapabilities == null || 
                postedCapabilities.length != capabilities.length ||
                tabTellerSharedPublicKey == null) {            
            return false;
        }
            
        ElGamalParameters params = tabTellerSharedPublicKey.getParams();
        
        for (int i = 0; i < capabilities.length; i++) {
            try {
                VoteCapabilityShare vc = capabilities[i];
                ElGamalReencryptFactor r = rencryptFactors[i];
                ElGamalProofDVR p = proofs[i];
                // check that p.e equals the posted capability
                if (!p.getE().equals(postedCapabilities[i])) {
                    return false;
                }
                
                // check that the posted capability verifies
                if (!CryptoUtil.factory().elGamalVerify(params, postedCapabilities[i], 
                                                        CryptoUtil.factory().messageDigest(tellerIndex + voterName))) {
                    return false;
                }
                
                // check that p.e' equals enc(vc, ttKey r)
                if (!CryptoUtil.factory().elGamalEncrypt(tabTellerSharedPublicKey, vc, r).equals(p.getEprime())) {
                    return false;
                }
                
                // check the proof, i.e., that p.e is a reencryption of p.e'
                if (!p.verify(tabTellerSharedPublicKey, voterPublicKey)) {
                    return false;
                }
            }
            catch (ArrayIndexOutOfBoundsException imposs) { }
            catch (NullPointerException e) { 
                return false;
            }
        }
        return true;
    }
 
    public VoterCapabilityShares  extractShares() {
        return new VoterCapabilityShares(capabilities, voterBlock);
    }
    
    public void toXML{}(PrintWriter[{}]{} s) {
        toXML(new label {}, s);
    }
    
    public void toXML (  PrintWriter   sb)  
        if (sb == null) return;
        sb.print("<" + OPENING_TAG + ">");
        if (capabilities != null && rencryptFactors != null && proofs != null) {
            sb.print("<size>");
            sb.print(this.capabilities.length);
            sb.print("</size>");
            for (int i = 0; i < capabilities.length; i++) {
                sb.print("<capabilitySharesAndProof>");
                try {
                    VoteCapabilityShare vc = capabilities[i];
                    ElGamalReencryptFactor rf = rencryptFactors[i];
                    ElGamalProofDVR p = proofs[i];
                    if (vc != null && rf != null && p != null) {
                        vc.toXML(  sb);
                        rf.toXML(  sb);
                        p.toXML(  sb);
                    }
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                sb.print("</capabilitySharesAndProof>");
            }
        }
        else {
            sb.print("<size>0</size>");
            
        }
        sb.print("<voterBlock>" + voterBlock + "</voterBlock>");
        
        sb.print("</" + OPENING_TAG + ">");
    }
    
    public static VoterCapabilitySharesAndProof  fromXML (  Reader   r)  throws (IllegalArgumentException , IOException ) {
        Util.swallowTag(  r, OPENING_TAG);

        int size = Util.readSimpleIntTag(  r, "size");
        try {
            VoteCapabilityShare []  caps = new VoteCapabilityShare[size];
            ElGamalReencryptFactor []  factors = new ElGamalReencryptFactor[size];
            ElGamalProofDVR []  proofs = new ElGamalProofDVR[size];

            for (int i = 0; i < size; i++) {
                Util.swallowTag(  r, "capabilitySharesAndProof");
                VoteCapabilityShare c = null;
                ElGamalReencryptFactor f = null;
                ElGamalProofDVR p = null;
                try {
                    c = CryptoUtil.factory().voteCapabilityShareFromXML(  r);
                    f = CryptoUtil.factory().elGamalReencryptFactorFromXML(  r);
                    p = CryptoUtil.factory().elGamalProofDVRFromXML(  r);
                }
                catch (NullPointerException imposs) { }
                try {
                    caps[i] = c;
                    factors[i] = f;
                    proofs[i] = p;
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
                Util.swallowEndTag(  r, "capabilitySharesAndProof");
            }

            int voterBlock = Util.readSimpleIntTag(  r, "voterBlock");


            Util.swallowEndTag(  r, OPENING_TAG);
            VoterCapabilitySharesAndProof vc = new VoterCapabilitySharesAndProof(caps.clone(), factors.clone(), proofs.clone(), voterBlock);
            return vc;
        }
        catch (NegativeArraySizeException ignore) { }
        return null;
    }    
}