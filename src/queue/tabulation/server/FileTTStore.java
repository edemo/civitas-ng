/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.tabulation.server;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jif.lang.LabelUtil;
import jif.lang.Principal;
import civitas.common.ElectionCache;
import civitas.common.ElectionID;
import civitas.common.FileBasedElectionCache;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalKeyPairShare;
import civitas.crypto.PETShare;
import civitas.crypto.PublicKey;

/**
 * Implementation of tabulation teller storage, using the file system. 
 */
public class FileTTStore implements TTStore {
    private final PublicKey publicKey;
    private final File root;
    private final String cacheRootDir;

    private Map<String, ElectionCache> cacheStore = new HashMap<String, ElectionCache>();
    private Map<String, String> abandonmentStore = new HashMap<String, String>();
    private Map<String, Integer> indexStore = new HashMap<String, Integer>();
    private Map<String, ElGamalKeyPairShare> keyStore = new HashMap<String, ElGamalKeyPairShare>();
    private Map<String, MixInfoHolder> erMixInfoStore = new HashMap<String, MixInfoHolder>();
    private Map<String, MixInfoHolder> vMixInfoStore = new HashMap<String, MixInfoHolder>();
    private Set<String> tellerMixOK = new HashSet<String>();


    FileTTStore(PublicKey publicKey, File root, String cacheRootDir) {
        this.publicKey = publicKey;
        this.root = root;
        this.cacheRootDir = cacheRootDir;
        if (!root.exists()) {
            // try to create the file root.
            root.mkdirs();
        }
        else {
            // the root exists. Pre-populate boardNames

        }            
    }

    static String cleanForFilename(String s) {
        // remove everything except alphanumerics
        StringWriter sb = new StringWriter(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }            
            else {
                sb.append('_');                
            }
        }
        return sb.toString();        
    }
    PrintStream getFilePrintStream(String filename) {
        if (root == null) return null;
        File f = new File(root, cleanForFilename(filename));
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            return new PrintStream(fos);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    BufferedReader getFileContents(String filename) {
        if (root == null) return null;
        File f = new File(root, cleanForFilename(filename));
        if (!f.exists()) return null;
        try {
            FileInputStream fis = new FileInputStream(f);
            return new BufferedReader(new InputStreamReader(fis));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public ElectionCache electionCache(ElectionID election, int tellerIndex) {
        String id = election.toString();
        ElectionCache c = this.cacheStore.get(id);
        if (c == null) {
            c = new FileBasedElectionCache(this.cacheRootDir, "tabTeller" + tellerIndex + id);
            this.cacheStore.put(id, c);
        }
        return c;
    }

    public boolean isAbandonedElection(ElectionID election) {
        return abandonmentStore.containsKey(election.toString());
    }

    public boolean isAbandonedElection(Principal sup, ElectionID election) {
        return isAbandonedElection(election);
    }

    public boolean isAcceptedElection(ElectionID election) {
        return indexStore.containsKey(election.toString());
    }

    public Principal jif$getcivitas_tabulation_server_TTStore_TT() {
        return publicKey;
    }

    public String retrieveAbandonment(ElectionID election) {
        return abandonmentStore.get(election.toString());
    }

    public String retrieveAbandonment(Principal sup, ElectionID election) {
        return retrieveAbandonment(election);
    }

    public ElectoralRollMixInfo retrieveElectoralRollMixInfo(Principal sup, ElectionID election, int block, boolean rightPerm) {
        return retrieveElectoralRollMixInfo(election, block, rightPerm);
    }

    public int retrieveIndex(ElectionID election) {
        return indexStore.get(election.toString());
    }

    public int retrieveIndex(Principal sup, ElectionID election) {
        return retrieveIndex(election);
    }

    public ElGamalKeyPairShare retrieveKeyShare(ElectionID election) {
        return keyStore.get(election.toString());
    }

    public ElGamalKeyPairShare retrieveKeyShare(Principal sup, ElectionID election) {
        return retrieveKeyShare(election);
    }


    public PETShare[] retrieveRollVotePETShares(Principal sup, ElectionID election, int block, int ballotIndex) {
        return retrieveRollVotePETShares(election, block, ballotIndex);
    }


    public boolean retrieveTellerMixOK(Principal sup, ElectionID electionID, int tellerIndex, boolean isVoteMix, int block) {
        return retrieveTellerMixOK(electionID, tellerIndex, isVoteMix, block);
    }


    public VoteMixInfo retrieveVoteMixInfo(Principal sup, ElectionID election, int block, boolean rightPerm) {
        return retrieveVoteMixInfo(election, block, rightPerm);
    }


    public PETShare[] retrieveVoteVotePETShares(Principal sup, ElectionID election, int block, int ballotIndex) {
        return retrieveVoteVotePETShares(election, block, ballotIndex);
    }

    public void storeAbandoment(ElectionID election, String reason) {
        abandonmentStore.put(election.toString(), reason);

    }

    public void storeAcceptance(ElectionID election) {
        indexStore.put(election.toString(), null);
    }

    public void storeIndex(ElectionID election, int tellerIndex) {
        indexStore.put(election.toString(), tellerIndex);

    }

    public void storeKeyShare(ElectionID election, ElGamalKeyPairShare keyshare) {
        keyStore.put(election.toString(), keyshare);
    }


    public boolean retrieveTellerMixOK(ElectionID electionID, int tellerIndex, boolean isVoteMix, int block) {
        String key = electionID.toString() + ":" + tellerIndex + ":" + isVoteMix + ":" + block; 
        return tellerMixOK.contains(key);
    }
    public void storeTellerMixOK(ElectionID electionID, int tellerIndex, boolean isVoteMix, int block) {
        String key = electionID.toString() + ":" + tellerIndex + ":" + isVoteMix + ":" + block; 
        tellerMixOK.add(key);

    }


    public void storeRollVotePETShares(ElectionID election, int block, int ballotIndex, PETShare[] shares) {
        if (shares == null) return;
        String filename = "rollVotePETShares" + election.toString() + block + "B"+ballotIndex;
        PrintStream out = getFilePrintStream(filename);
        out.println(shares.length);
        StringWriter sb;
        for (int i = 0; i < shares.length; i++) {
            sb = new StringWriter();
            shares[i].toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
            out.println(sb.toString());            
        }
        out.close();
    }
    public PETShare[] retrieveRollVotePETShares(ElectionID election, int block, int ballotIndex) {
        String filename = "rollVotePETShares" + election.toString() + block+"B"+ballotIndex;
        BufferedReader reader = getFileContents(filename);
        if (reader != null) {
            try {
                int length = Integer.parseInt(reader.readLine());
                PETShare[] ret = new PETShare[length];
                for (int i = 0; i < length; i++) {                
                    try {
                        ret[i] = CryptoUtil.factory().petShareFromXML(LabelUtil.singleton().noComponents(), reader);
                    }
                    catch (IOException e) { 
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e) { 
                        e.printStackTrace();
                    }
                }
                return ret;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void storeVoteMixInfo(ElectionID electionID, int block, VoteMixInfo leftMixInfo, VoteMixInfo rightMixInfo) {
        String key = "votemix" + electionID.toString() + block;
        vMixInfoStore.put(key, new MixInfoHolder(leftMixInfo, rightMixInfo));
    }
    public void clearVoteMixInfo(ElectionID election, int block) {
        String key = "votemix" + election.toString() + block;
        vMixInfoStore.remove(key);
    }
    public VoteMixInfo retrieveVoteMixInfo(ElectionID election, int block, boolean rightPerm) {
        String key = "votemix" + election.toString() + block;
        MixInfoHolder h = vMixInfoStore.get(key);
        if (h == null) return null;
        return (VoteMixInfo)(rightPerm?h.right:h.left);
    }
    public void storeElectoralRollMixInfo(ElectionID electionID, int block, ElectoralRollMixInfo leftMixInfo, ElectoralRollMixInfo rightMixInfo) {
        String key = "electoralRollmix" + electionID.toString() + block;
        erMixInfoStore.put(key, new MixInfoHolder(leftMixInfo, rightMixInfo));
    }
    public void clearElectoralRollMixInfo(ElectionID election, int block) {
        String key = "electoralRollmix" + election.toString() + block;
        erMixInfoStore.remove(key);
    }
    public ElectoralRollMixInfo retrieveElectoralRollMixInfo(ElectionID election, int block, boolean rightPerm) {
        String key = "electoralRollmix" + election.toString() + block;
        MixInfoHolder h = erMixInfoStore.get(key);
        if (h == null) return null;
        return (ElectoralRollMixInfo)(rightPerm?h.right:h.left);        
    }

    public void storeVoteVotePETShares(ElectionID election, int block, int ballotIndex, PETShare[] shares) {
        if (shares == null) return;
        String filename = "voteVotePETShares" + election.toString() + block+"B"+ballotIndex;
        PrintStream out = getFilePrintStream(filename);
        out.println(shares.length);
        StringWriter sb;
        for (int i = 0; i < shares.length; i++) {
            sb = new StringWriter();
            shares[i].toXML(LabelUtil.singleton().noComponents(), new PrintWriter(sb));
            out.println(sb.toString());            
        }
        out.close();
    }
    public PETShare[] retrieveVoteVotePETShares(ElectionID election, int block, int ballotIndex) {
        String filename = "voteVotePETShares" + election.toString() + block+"B"+ballotIndex;
        BufferedReader reader = getFileContents(filename);
        if (reader != null) {
            try {
                int length = Integer.parseInt(reader.readLine());
                PETShare[] ret = new PETShare[length];
                for (int i = 0; i < length; i++) {                
                    try {
                        ret[i] = CryptoUtil.factory().petShareFromXML(LabelUtil.singleton().noComponents(), reader);
                    }
                    catch (IOException e) { 
                        e.printStackTrace();
                    }
                    catch (IllegalArgumentException e) { 
                        e.printStackTrace();
                    }
                }            
                return ret;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    public void clearRollVotePETShares(ElectionID election, int block, int ballotIndex) {
        // do nothing
    }


    public void clearVoteVotePETShares(ElectionID election, int block, int ballotIndex) {
        // do nothing
    }

    static class MixInfoHolder {
        public MixInfoHolder(MixInfo leftMixInfo, MixInfo rightMixInfo) {
            this.left = leftMixInfo;
            this.right = rightMixInfo;
        }
        final MixInfo left;
        final MixInfo right;

    }
}
