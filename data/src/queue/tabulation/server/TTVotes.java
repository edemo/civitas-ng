/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.io.*;
import jif.util.*;
import until.util.*;

import civitas.bboard.client.*;
import civitas.bboard.common.*;
import civitas.tabulation.client.*;
import civitas.common.*;
import civitas.crypto.*;


/**
 * Utility class to help with the gathering and filetering
 * of votes for the tabulation protocol. 
 * The principal TT should be the tab teller's public key.
 * The principal SUP is the election supervisor.
 * The principal TELLS represents all of the tabulation tellers, i.e., it is a principal that
 * every tabulation teller can act for.
 */
public class TTVotes[principal TT, principal SUP, principal TELLS] {
    private final PrivateKey{TT<-TT} tellerPrivKey;
    private final TTStore[TT]  ttstore;
    private final BBClientUtil  bb;
    final TTTabulationUtil[TT,SUP,TELLS]  tttUtil;

    TTVotes(PrivateKey{TT<-TT} tellerPrivKey,
                 TTStore[TT]  ttstore,
                 BBClientUtil  bb,
                 TTTabulationUtil[TT,SUP,TELLS]  tttUtil) {
          this.tellerPrivKey = tellerPrivKey;
          this.ttstore = ttstore;
          this.bb = bb;
          this.tttUtil = tttUtil;
      }

    VerifiableVote{}[]{TT<-SUP} retrieveValidVerifiableVotes{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, int{TT<-SUP} block) 
    throws (IOException{TT<-SUP})  
        VerifiableVote{}[] ret = null;
        IOException exio = null;
        try {
            ret = retrieveValidVerifiableVotes_(electionDetails, electionCache, tabTellerSharedKey, block);
        }
        catch (IOException e) {
            exio = e;
        }
        IOException eexio = endorse(exio, {TT<-SUP});
        if (eexio != null) throw eexio;
        return endorse(ret, {TT<-SUP});
    }

    private VerifiableVote{}[]{} retrieveValidVerifiableVotes_{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
                                                                        ElectionCache{TT<-SUP} electionCache, 
                                                                        ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, 
                                                                        int{TT<-SUP} block) 
    throws (IOException{})  
        if (electionDetails == null || bb == null) return null;
        BallotDesign ballotDesign = electionDetails.ballotDesign;
        if (ballotDesign == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;
        ElGamalParameters params = electionDetails.elGamalParameters;
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        if (tellerDetails == null || tellerDetails.voterBBs == null) return null;
        
        if (electionCache != null) {
            VerifiableVote{}[] vv = electionCache.getValidVerifiableVotesForBlock(block);
            if (vv != null) return vv;
        }
        int voterBlock = electionDetails.voterBlockForBlock(block);

        List[{}] voterSubmissions = retrieveVoterSubmissions(electionDetails, electionCache, tabTellerSharedKey, voterBlock);

        // convert the voter submissions into an array of verifiable votes
        VerifiableVote{}[] vv = voterSubmissionsToVerifiableVotes(electionDetails, voterSubmissions, block);
        if (electionCache != null) {
            electionCache.setValidVerifiableVotesForBlock(block, vv);
        }
        return vv;
    }
    int{TT<-SUP} retrieveValidVerifiableVoteSize{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache, ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, int{TT<-SUP} block) 
    throws (IOException{TT<-SUP})  
        int ret = -1;
        IOException exio = null;
        try {
            ret = retrieveValidVerifiableVoteSize_(electionDetails, electionCache, tabTellerSharedKey, block);
        }
        catch (IOException e) {
            exio = e;
        }
        IOException eexio = endorse(exio, {TT<-SUP});
        if (eexio != null) throw eexio;
        return endorse(ret, {TT<-SUP});
    }
    private int{} retrieveValidVerifiableVoteSize_{TT<-SUP}(final ElectionDetails{TT<-SUP} electionDetails, 
            ElectionCache{TT<-SUP} electionCache, 
            ElGamalPublicKey{TT<-SUP} tabTellerSharedKey, 
            int{TT<-SUP} block) 
     throws (IOException{})  
        
        if (electionCache != null) {
            int size = electionCache.getValidVerifiableVoteSizeForBlock(block);
            if (endorse(size >= 0, {TT<-SUP})) return endorse(size, {TT<-SUP});
        }

        VerifiableVote{}[] vv = retrieveValidVerifiableVotes_(electionDetails, electionCache, tabTellerSharedKey, block);
        return vv == null ? -1 : vv.length;
    }
    

    /**
     * Check the boards for tabulations posted by the supervisor
     * against the commitments posted by the voter BBs.
     */
    private BoardsForTabulation{} retrieveAndCheckBoardsForTabulation{}(final ElectionDetails{} electionDetails, 
            ElectionCache{} electionCache):{} throws IOException  
        if (electionCache != null) {
            BoardsForTabulation bft = electionCache.getBoardsForTabulation();
            if (bft != null) return bft;
        }
        if (electionDetails == null || ttstore == null || tttUtil == null) return null;
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        if (tellerDetails == null || tellerDetails.voterBBs == null) return null;
                
        
        BoardsForTabulation supBoardContents = ElectionUtil.retrieveBoardsForTabulation(electionDetails);
        if (supBoardContents == null) return null;
        BoardsForTabulation bbBoardContents = ElectionUtil.retrieveVoterBBContentCommitments(electionDetails, electionCache);
        if (bbBoardContents == null)return null;
        
        int numVoterBlocks = ElectionUtil.numberVoterBlocks(electionDetails, electionCache);
        
        // check each one
        for (int i = 0; i < tellerDetails.voterBBs.length; i++) {
            int boardIndex = i+1;
            // check that what the supervisor posted agrees with what
            // the bb posted 
            BoardClosedContentCommitment scc = supBoardContents.contentCommitmentForBoard(boardIndex);
            if (scc == null) {
                // the supervisor said to ignore this board
                continue;
            }
            BoardClosedContentCommitment bcc = bbBoardContents.contentCommitmentForBoard(boardIndex);
            for (int voterBlock = 0; voterBlock < numVoterBlocks; voterBlock++) {
                String requiredHash = scc.contentHash(voterBlock);
                if (bcc == null || requiredHash == null || 
                        !requiredHash.equals(bcc.contentHash(voterBlock))) {
                    // nope, what the supervisor posted doesn't agree
                    // with what the voter BB posted.
                    int tellerIndex = retrieveIndex(electionDetails.electionID);
                    tttUtil.abandonElection(electionDetails.electionID, 
                                    "Supervisors content hash for voter BB " + 
                                    boardIndex + ", voter block " + 
                                    voterBlock + " disagrees with what the voter BB posted.", 
                                    tellerIndex); 
                }
            }            
        }
        
        
        if (electionCache != null) {
            electionCache.setBoardsForTabulation(supBoardContents);
        }
        return supBoardContents;   
    }

    private int{TT<-TT} retrieveIndex{}(ElectionID{} id_) 
     
        ElectionID id = endorse(id_, {TT<-TT});
        if (id == null || ttstore == null) return -1;

        return ttstore.retrieveIndex(id);
    }

    /**
     * Retrieve the voter submissions for the given voterBlock.
     */
    public List[{}]{} retrieveVoterSubmissions{}(final ElectionDetails{} electionDetails, 
            ElectionCache{} electionCache, 
            ElGamalPublicKey{} tabTellerSharedKey, 
            int{} voterBlock) 
    throws (IOException{})  
        List[{}] voterSubmissions = null;
        if (electionCache != null) {
            voterSubmissions = electionCache.getVoterSubmissionsForVoterBlock(voterBlock);
            if (voterSubmissions != null) return voterSubmissions;
        }
        
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        if (electionDetails == null || tellerDetails == null || 
                tellerDetails.voterBBs == null || tttUtil == null) return null;

        BoardsForTabulation bft = retrieveAndCheckBoardsForTabulation(electionDetails, electionCache);
        if (bft == null) {
            // not yet ready for tabulation
            return null;
        }
                
        voterSubmissions = new ArrayList[{}]();
        HashSet[{}] voterSubmissionSet = new HashSet[{}]();

        // go to each BB and get the submitted votes. use the set to 
        // detect duplicates efficiently (i.e., votes submitted to more 
        // than one voter BB)
        for (int i = 0; i < tellerDetails.voterBBs.length; i++) {
            int boardIndex = i+1;
            if (bft.contentCommitmentForBoard(boardIndex) == null) {
                // the supervisor didn't post anything for this board, so
                // we do not include it in the results.
                continue;
            }
            VoterSubmission{}[] vss = null;
            try {
                vss = retrieveVoterSubmissions(electionDetails, electionCache, tabTellerSharedKey, voterBlock, boardIndex);
            }
            catch (IOException e) {
                throw e;
            }
            if (vss != null) {
                for (int j = 0; j < vss.length; j++) {
                    try {
                        VoterSubmission vs = vss[j];
                        if (vs != null) {
                            if (!voterSubmissionSet.contains(vs)) {
                                voterSubmissions.add(vs);
                                voterSubmissionSet.add(vs);
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }
                    catch (ClassCastException imposs) { }
                    catch (IllegalArgumentException imposs) { }
                }
            }
            else {
                // failed to retrieve voter submissions. 
                // this is not good, we cannot continue.
                int tellerIndex = retrieveIndex(electionDetails.electionID);
                tttUtil.abandonElection(electionDetails.electionID,
                                        "Tabulation teller " + tellerIndex + " unable to retrieve votes from voter bulletin board " + boardIndex, 
                                        tellerIndex);
            }
        }
        
        if (electionCache != null) {
            electionCache.setVoterSubmissionsForVoterBlock(voterBlock, voterSubmissions);            
        }
           
        return voterSubmissions;
    }
    
    /**
     * Go to the bulletin board boardIndex and get all their valid results for the given block.
     * Some of the array elements may be null.
     */
    private VoterSubmission{}[]{} retrieveVoterSubmissions{}(final ElectionDetails{} electionDetails, 
            ElectionCache{} electionCache, 
            ElGamalPublicKey{} tabTellerSharedKey, 
            int{} voterBlock,
            int{} boardIndex) 
    throws (IOException{})  
        if (electionDetails == null || bb == null) return null;
        BallotDesign ballotDesign = electionDetails.ballotDesign;
        if (ballotDesign == null) return null;
        ElectionID electionID = electionDetails.electionID;
        if (electionID == null) return null;
        
        ElGamalParameters params = electionDetails.elGamalParameters;        
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        if (tellerDetails == null || tellerDetails.voterBBs == null) return null;
        CiphertextList ciphertexts = ElectionUtil.retrieveCiphertextList(TT, electionDetails, electionCache);

        Host h = null;
        try {
            h = tellerDetails.voterBBs[boardIndex-1];
        }
        catch (ArrayIndexOutOfBoundsException ignore) { }
        if (h == null) return null;
        
        BBClientUtil bbcu = new BBClientUtil(h, electionID.id);

        BoardsForTabulation bft = retrieveAndCheckBoardsForTabulation(electionDetails, electionCache);
        if (bft == null) {
            // not yet ready for tabulation
            return null;
        }
        BoardClosedContentCommitment cc = bft.contentCommitmentForBoard(boardIndex);
        if (cc == null) return null;
        String requiredHash = cc.contentHash(voterBlock);
        if (requiredHash == null) return null;
        
        List[{}] l = null;
        try {
            l = bbcu.retrieveParams(XMLDeserializers.VoterSubmission(), h.publicKey, requiredHash, VoterSubmission.meta(voterBlock), null, null);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage()); 
        }
        if (l == null) { 
            // no results! Is there anything to do?
            return null;
        }

        // filter the submitted votes to leave only the valid ones.
        int size = l.size();
        VoterSubmission{}[] validVS = new VoterSubmission[size < 0 ? 0 : size];
        int validCount = 0;
        for (int i = 0; i < size; i++) {
            BBPost post = null;
            try {
                post = (BBPost)l.get(i);
                // Note that votes are not signed, so there is no need to check the signature.
            }
            catch (IndexOutOfBoundsException imposs) { }
            catch (ClassCastException ignore) { }
            if (post == null) continue;

            try {
                VoterSubmission vs = null;
                try {
                    vs = (VoterSubmission)post.msg;
                }
                catch (ClassCastException ignore) { }
                ballotDesign.checkVoterSubmission(vs, electionDetails.baseContext(voterBlock), ciphertexts, tabTellerSharedKey);
                // the voter submission is valid
                
                // is it in the right voter block?
                if (vs != null && voterBlock == vs.voterBlock) {
                    try {
                        validVS[validCount++] = vs;
                    }
                    catch (ArrayIndexOutOfBoundsException imposs) { }
                }
            }
            catch (IllegalArgumentException e) {
                // the voter submission can't be parsed, or
                // it wasn't a valid submission. 
                // ignore it.
            }
        }
        return validVS;
    }

    /**
     * Flatten the array of voter submissions into an array of verifiable votes.
     */
    private VerifiableVote{}[]{} voterSubmissionsToVerifiableVotes{}(final ElectionDetails{} electionDetails, List[{}]{} voterSubmissions, int{} block) {
        if (electionDetails == null || voterSubmissions == null) return null;
        int size = voterSubmissions.size();
        VerifiableVote{}[] vv = new VerifiableVote[size < 0 ? 0 : size]; 
        int vvCount = 0;
        Iterator[{}] iter = voterSubmissions.iterator();
        while (iter != null && iter.hasNext()) {
            VoterSubmission v = null;
            try {
                v = (VoterSubmission)iter.next();
            }
            catch (ClassCastException imposs) { }
            catch (NoSuchElementException imposs) { }
            
            if (v == null || v.votes == null) continue;
            if (!electionDetails.inBlock(v, block)) continue;
            for (int j = 0; j < v.votes.length; j++) {
                try {
                    VerifiableVote verfV = v.votes[j];
                    // is verfV in the block?
                    if (verfV == null || !electionDetails.inBlock(v, verfV.context, block)) continue;
                    if (vvCount >= vv.length) {
                        // extend vv
                        VerifiableVote{}[] newvv = new VerifiableVote[2*vv.length]; 
                        for (int z = 0; z < vv.length; z++) {
                            newvv[z] = vv[z];
                        }
                        vv = newvv;
                    }
                    vv[vvCount++] = verfV;
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }                    
            }
        }
        if (vvCount != vv.length) {
            // shrink vv.
            VerifiableVote{}[] newvv = new VerifiableVote[vvCount]; 
            for (int z = 0; z < vvCount; z++) {
                try {
                    newvv[z] = vv[z];
                }
                catch (ArrayIndexOutOfBoundsException imposs) { }
            }
            vv = newvv;
        }
        return vv;
    }
    
    /**
     * Goto the the BB and get the voter block conects for voterBlock, as 
     * posted by tabulation teller tellerIndex
     */
    TabTellerVoterBlockContents{} retrieveVoterBlockContents{}(final ElectionDetails{} electionDetails, 
            ElectionCache{} electionCache, 
            int{} voterBlock, 
            int{} tellerIndex):{} throws IOException {
        if (bb == null) return null;        
        TellerDetails tellerDetails = ElectionUtil.retrieveTellerDetails(electionDetails, electionCache);
        
        if (electionCache != null) {
            TabTellerVoterBlockContents ttvbc = electionCache.getTabTellerVoterBlockContents(voterBlock, tellerIndex);
            if (ttvbc != null) return ttvbc;
        }
        
        List[{}] results = null;
        
        try {
            results = bb.retrieveParams(XMLDeserializers.TabTellerVoterBlockContents(), TabTellerVoterBlockContents.meta(tellerIndex, voterBlock), null, null);
        }
        catch (IllegalArgumentException e) {
            throw new IOException(e.getMessage());
        }
        
        if (results == null) return null;
        // parse and verify the posts. 
        for (int i = 0; i < results.size(); i++) {
            BBPost post = null;
            try {
                post = (BBPost)results.get(i);
            }
            catch (ClassCastException imposs) { }
            catch (IndexOutOfBoundsException imposs) { }
            if (post == null) continue;
            // parse and validate
            try {
                TabTellerVoterBlockContents c = (TabTellerVoterBlockContents)post.msg;
                // check the signature of c
                if (c != null && c.tellerIndex == tellerIndex && post.verify(ElectionUtil.tabTellerPublicKey(tellerDetails, tellerIndex))) {
                    if (electionCache != null) {
                        electionCache.setTabTellerVoterBlockContents(voterBlock, tellerIndex, c);
                    }
                    return c;
                }
            }
            catch (ClassCastException ignore) {  } 
        }
        return null;  
        
    }
}
