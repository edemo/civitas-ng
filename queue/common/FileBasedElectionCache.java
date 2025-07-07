/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.common;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import jif.lang.Label;
import jif.lang.LabelUtil;
import jif.util.ArrayList;
import jif.util.Iterator;
import jif.util.List;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalPublicKey;

/**
 * An election cache that stores information persistently using the file system.
 *
 */
public class FileBasedElectionCache extends ElectionCache {

    final Label lbl;
    private final File cacheDir;

    public FileBasedElectionCache(String cacheRootDir, String uniqueID) {
        super();
        super.civitas$common$ElectionCache$();        
        File cacheDir = new File(cacheRootDir, cleanForFilename(uniqueID));
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }    
        if (!cacheDir.exists()) {
            // we failed
            cacheDir = null;
        }
        this.lbl = LabelUtil.singleton().noComponents();
        this.cacheDir = cacheDir;
    }
    
    private String cleanForFilename(String s) {
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
    
    private File tempFileForName(String cachename) {
        return fileForName(cachename + "tmp");
    }
    private File fileForName(String cachename) {
        StringWriter sb = new StringWriter(cachename.length());
        String parentDir = null;
        for (int i = 0; i < cachename.length(); i++) {
            char c = cachename.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }     
            else if (':' == c) {
                parentDir = sb.toString();
                sb.append(File.separatorChar);
            }
            else {
                sb.append('_');                
            }
        }
        if (parentDir != null) {
            File p = new File(cacheDir, parentDir);
            while (!p.exists()) {
                p.mkdirs();       
            }
        }
        return new File(cacheDir, sb.toString());
    }

    void writeFileContents(String filename, String s) {
        if (cacheDir == null) return;
        File ftarget = fileForName(filename);
        File f = tempFileForName(filename);
        try {
            PrintStream fos = new PrintStream(f);
            fos.print(s);
            fos.close();
            f.renameTo(ftarget);
        }
        catch (IOException e) {
            e.printStackTrace();
            failedCaching(f);
        }
    }
    
    PrintWriter getFilePrintWriter(String filename) {
        if (cacheDir == null) return null;
        File f = fileForName(filename);
        try {
            return new PrintWriter(f);
        }
        catch (IOException e) {
            e.printStackTrace();
            failedCaching(f);
        }
        return null;
    }

    boolean cachefileExists(String filename) {
        if (cacheDir == null) return false;
        File f = fileForName(filename);
        return f.exists();
    }

    void closedFilePrintStream(String filename) {
        File ftarget = fileForName(filename);
        File f = tempFileForName(filename);
        f.renameTo(ftarget);        
    }
    String getFileContentsAsString(String filename) {
        if (cacheDir == null) return null;
        File f = fileForName(filename);
        if (!f.exists()) return null;
        StringWriter fileData = new StringWriter();
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();  
            failedCaching(f);
        }

        return fileData.toString();    
    }

    BufferedReader getFileContents(String filename) {
        if (cacheDir == null) return null;
        File f = fileForName(filename);
        if (!f.exists()) return null;
        try {
            FileInputStream fis = new FileInputStream(f);
            return new BufferedReader(new InputStreamReader(fis));
        }
        catch (IOException e) {
            e.printStackTrace();
            failedCaching(f);
        }

        return null;
    }

    /**
     * We failed to cache. Try to clean up.
     */
    void failedCaching(File f) {
        if (f.exists()) {
            if (!f.delete()) {
                f.deleteOnExit();
            }
        }
    }
    void failedCaching(String filename) {
        if (cacheDir == null) return;
        File f = fileForName(filename);
        failedCaching(f);
    }
    
    /* ***********
     * Factory methods
     */
    public DistDecryptCache newDistDecryptCache(String meta, int numberBlocks, int numTabTellers) {
        return new FileBasedDistDecryptCache(this, meta, numberBlocks, numTabTellers);
    }
    public PETCache newPETCache(String meta, int numberBlocks, int numTabTellers) {
        return new FileBasedPETCache(this, meta, numberBlocks, numTabTellers);
    }

    /* **********************
     * Caching methods 
     */

    @Override
    public Long getElectionStartTime() {
        if (super.getElectionStartTime() == null) {
            // try getting it from file
            String s = getFileContentsAsString("electionStartTime");
            if (s != null) {
                try {
                    Long time = Long.parseLong(s);
                    super.setElectionStartTime(time);
                    return time;
                }
                catch (NumberFormatException fail) { }
            }

        }
        return super.getElectionStartTime();
    }

    @Override
    public void setElectionStartTime(Long time) {
        if (time != null) {
            writeFileContents("electionStartTime", time.toString());
        }
        super.setElectionStartTime(time);
    }

    @Override
    public Long getElectionStopTime() {
        if (super.getElectionStopTime() == null) {
            // try getting it from file
            String s = getFileContentsAsString("electionStopTime");
            if (s != null) {
                try {
                    Long time = Long.parseLong(s);
                    super.setElectionStopTime(time);
                    return time;
                }
                catch (NumberFormatException fail) { }
            }            
        }
        return super.getElectionStopTime();
    }

    @Override
    public void setElectionStopTime(Long time) {
        if (time != null) {
            writeFileContents("electionStopTime", time.toString());
        }
        super.setElectionStopTime(time);
    }

    @Override
    public ElectionDetails getElectionDetails() {
        if (super.getElectionDetails() == null) {
            String cachename = "electionDetails";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElectionDetails d = ElectionDetails.fromXML(  reader);
                    super.setElectionDetails(d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectionDetails();
    }
    @Override
    public void setElectionDetails(ElectionDetails electionDetails) {
        if (electionDetails != null) {
            PrintWriter out = getFilePrintWriter("electionDetails");
            electionDetails.toXML(LabelUtil.singleton().noComponents(), out);
            out.close();
        }
        super.setElectionDetails(electionDetails);
    }

    @Override
    public CiphertextList getCiphertextList() {
        if (super.getCiphertextList() == null) {
            String cachename = "ciphertextList";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    CiphertextList ciphertextList = CiphertextList.fromXML(  reader);
                    super.setCiphertextList(ciphertextList);
                    return ciphertextList;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getCiphertextList();
    }
    @Override
    public void setCiphertextList(CiphertextList ciphertextList) {
        if (ciphertextList != null) {
            PrintWriter out = getFilePrintWriter("ciphertextList");
            ciphertextList.toXML(LabelUtil.singleton().noComponents(), out);
            out.close();
        }
        super.setCiphertextList(ciphertextList);
    }

    @Override
    public BoardsForTabulation getBoardsForTabulation() {
        if (super.getBoardsForTabulation() == null) {
            String cachename = "boardsForTabulation";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    BoardsForTabulation bft = BoardsForTabulation.fromXML(  reader);
                    super.setBoardsForTabulation(bft);
                    return bft;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getBoardsForTabulation();
    }
    @Override
    public void setBoardsForTabulation(BoardsForTabulation boardsForTabulation) {
        if (boardsForTabulation != null) {
            PrintWriter out = getFilePrintWriter("boardsForTabulation");
            boardsForTabulation.toXML(LabelUtil.singleton().noComponents(), out);
            out.close();
        }
        super.setBoardsForTabulation(boardsForTabulation);
    }

    @Override
    public ElectoralRollEstimate getElectoralRollEstimate() {
        if (super.getElectoralRollEstimate() == null) {
            String cachename = "electoralRollEstimate";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElectoralRollEstimate ere = ElectoralRollEstimate.fromXML(  reader);
                    super.setElectoralRollEstimate(ere);
                    return ere;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectoralRollEstimate();
    }
    @Override
    public void setElectoralRollEstimate(ElectoralRollEstimate electoralRollEstimate) {
        if (electoralRollEstimate != null) {
            PrintWriter out = getFilePrintWriter("electoralRollEstimate");         
            electoralRollEstimate.toXML(LabelUtil.singleton().noComponents(), out); 
            out.close();
        }
        super.setElectoralRollEstimate(electoralRollEstimate);
    }


    @Override
    public ElGamalPublicKey getTabTellerSharedKey() {
        if (super.getTabTellerSharedKey() == null) {
            String cachename = "tabTellerSharedKey";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElGamalPublicKey pk = CryptoUtil.factory().elGamalPublicKeyFromXML(  reader);
                    super.setTabTellerSharedKey(pk);
                    return pk;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getTabTellerSharedKey();
    }

    @Override
    public void setTabTellerSharedKey(ElGamalPublicKey tabTellerSharedKey) {
        if (tabTellerSharedKey != null) {
            PrintWriter out = getFilePrintWriter("tabTellerSharedKey");     
            tabTellerSharedKey.toXML(LabelUtil.singleton().noComponents(), out); 
            out.close();
        }
        super.setTabTellerSharedKey(tabTellerSharedKey);
    }

    @Override
    public TellerDetails getTellerDetails() {
        if (super.getTellerDetails() == null) {
            String cachename = "tellerDetails";
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    TellerDetails d = TellerDetails.fromXML(  reader);
                    super.setTellerDetails(d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getTellerDetails();
    }

    @Override
    public void setTellerDetails(TellerDetails tellerDetails) {
        if (tellerDetails != null) {
            PrintWriter out = getFilePrintWriter("tellerDetails");  
            tellerDetails.toXML(LabelUtil.singleton().noComponents(), out); 
            out.close();
        }
        super.setTellerDetails(tellerDetails);
    }

    @Override
    public ElectionEvent[] getElectionEvents() {
        if (super.getElectionEvents() == null) {
            String cachename = "electionEvents";
            // try getting it from file
            BufferedReader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    int length = Integer.parseInt(reader.readLine());
                    ElectionEvent[] events = new ElectionEvent[length];                   
                    for (int i = 0; i < length; i++) {
                        events[i] = ElectionEvent.fromXML(  reader);

                    }
                    super.setElectionEvents(events);
                    return events;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectionEvents();
    }

    @Override
    public void setElectionEvents(ElectionEvent[] electionEvents) {
        if (electionEvents != null) {
            PrintWriter fout = getFilePrintWriter("electionEvents");
            if (fout != null) {
                fout.println(electionEvents.length);
                for (int i = 0; i < electionEvents.length; i++) {
                    electionEvents[i].toXML(LabelUtil.singleton().noComponents(), fout);
                }
                fout.close();
                closedFilePrintStream("electionEvents");                
            }
        }
        super.setElectionEvents(electionEvents);
    }

    private Map<Integer, Integer> blockVerifiableVoteSize = new HashMap<Integer, Integer>();
    
    public int getValidVerifiableVoteSizeForBlock(int block) {
        if (blockVerifiableVoteSize.containsKey(block)) {
            return blockVerifiableVoteSize.get(block);
        }
        return -1;
    }
    @Override
    public VerifiableVote[] getValidVerifiableVotesForBlock(int block) {
        if (super.getValidVerifiableVotesForBlock(block) == null) {
            String cachename = "validVerifiableVotesForBlock"+block;
            // try getting it from file
            BufferedReader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    int length = Integer.parseInt(reader.readLine());
                    VerifiableVote[] vv = new VerifiableVote[length];                   
                    for (int i = 0; i < length; i++) {
                        vv[i] = VerifiableVote.fromXML(  reader);

                    }
                    super.setValidVerifiableVotesForBlock(block, vv);
                    blockVerifiableVoteSize.put(block, vv.length);
                    return vv;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getValidVerifiableVotesForBlock(block);
    }

    @Override
    public void setValidVerifiableVotesForBlock(int block, VerifiableVote[] vv) {
        if (vv != null) {
            blockVerifiableVoteSize.put(block, vv.length);
            PrintWriter fout = getFilePrintWriter("validVerifiableVotesForBlock"+block);
            if (fout != null) {
                fout.println(vv.length);
                for (int i = 0; i < vv.length; i++) {
                    vv[i].toXML(LabelUtil.singleton().noComponents(), fout);
                }
                fout.close();
                closedFilePrintStream("validVerifiableVotesForBlock"+block);                
            }
        }
        super.setValidVerifiableVotesForBlock(block, vv);
    }

    @Override
    public List getVoterSubmissionsForVoterBlock(int voterBlock) {
        if (super.getVoterSubmissionsForVoterBlock(voterBlock) == null) {
            String cachename = "voterSubmissionsForVoterBlock"+voterBlock;
            // try getting it from file
            BufferedReader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    int length = Integer.parseInt(reader.readLine());
                    List voterSubmissions = new ArrayList(lbl).jif$util$ArrayList$();                   
                    for (int i = 0; i < length; i++) {
                        voterSubmissions.add(VoterSubmission.fromXML(  reader));

                    }
                    super.setVoterSubmissionsForVoterBlock(voterBlock, voterSubmissions);
                    return voterSubmissions;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getVoterSubmissionsForVoterBlock(voterBlock);
    }

    @Override
    public void setVoterSubmissionsForVoterBlock(int voterBlock, List voterSubmissions) {
        if (voterSubmissions != null) {
            PrintWriter fout = getFilePrintWriter("voterSubmissionsForVoterBlock"+voterBlock);
            if (fout != null) {
                fout.println(voterSubmissions.size());
                Iterator iter = voterSubmissions.iterator();
                while (iter.hasNext()) {
                    VoterSubmission v = (VoterSubmission)iter.next();
                    v.toXML(LabelUtil.singleton().noComponents(), fout);
                }
                fout.close();
                closedFilePrintStream("voterSubmissionsForVoterBlock"+voterBlock);                
            }
        }
        super.setVoterSubmissionsForVoterBlock(voterBlock, voterSubmissions);
    }

    @Override
    public void setInitialCapabilityMix(int block, CapabilityMix mix) {
        if (mix != null) {
            PrintWriter out = getFilePrintWriter("initCapabilityMix"+block);   
            mix.toXML(LabelUtil.singleton().noComponents(), out); 
            out.close();
            initialCapabilityMixSize.put(block, mix.size());
        }
        super.setInitialCapabilityMix(block, mix);
    }

    @Override
    public CapabilityMix getInitialCapabilityMix(int block) {
        if (super.getInitialCapabilityMix(block) == null) {
            String cachename = "initCapabilityMix"+block;
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    CapabilityMix d = CapabilityMix.fromXML(reader);
                    super.setInitialCapabilityMix(block, d);
                    if (d != null) {
                        initialCapabilityMixSize.put(block, d.size());
                    }
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getInitialCapabilityMix(block);
    }

    @Override
    public void setInitialVoteMix(int block, VoteMix mix) {
        if (mix != null) {
            PrintWriter out = getFilePrintWriter("initVoteMix"+block);   
            mix.toXML(LabelUtil.singleton().noComponents(), out);
            out.close();            
            initialVoteMixSize.put(block, mix.size());
        }
        super.setInitialVoteMix(block, mix);
    }

    @Override
    public VoteMix getInitialVoteMix(int block) {
        if (super.getInitialVoteMix(block) == null) {
            String cachename = "initVoteMix"+block;
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    VoteMix d = VoteMix.fromXML(reader);
                    super.setInitialVoteMix(block, d);
                    if (d != null) {
                        initialVoteMixSize.put(block, d.size());                        
                    }
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getInitialVoteMix(block);
    }

    private Map<Integer, Integer> initialVoteMixSize = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> initialCapabilityMixSize = new HashMap<Integer, Integer>();
    public int getInitialVoteMixSize(int block) {
        if (initialVoteMixSize.containsKey(block)) {
            return initialVoteMixSize.get(block);
        }
        return -1;        
    }
    public int getInitialCapabilityMixSize(int block) { 
        if (initialCapabilityMixSize.containsKey(block)) {
            return initialCapabilityMixSize.get(block);
        }
        return -1;
    }

    @Override
    public Mix getMix(int block, int n, boolean isRightMix, boolean isVoteMix) {
        if (super.getMix(block, n, isRightMix, isVoteMix) == null) {
            String cachename = mixCacheName(block, n, isRightMix, isVoteMix);
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    Mix d;
                    if (isVoteMix) {
                        d = VoteMix.fromXML(reader);
                    }
                    else {
                        d = CapabilityMix.fromXML(reader);
                    }
                    super.setMix(block, n, isRightMix, isVoteMix, d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        
        return super.getMix(block, n, isRightMix, isVoteMix);
    }



    private static String mixCacheName(int block, int n, boolean isRightMix, boolean isVoteMix) {        
        StringBuffer sb = new StringBuffer();
        sb.append(isVoteMix?"voteMix":"capabilityMix");
        sb.append(isRightMix?"R":"L");
        sb.append(block);
        sb.append('N');
        sb.append(""+n);
        return sb.toString();
    }
    @Override
    public void setMix(int block, int n, boolean isRightMix, boolean isVoteMix, Mix mix) {
        if (mix != null) {
            PrintWriter out = getFilePrintWriter(mixCacheName(block, n, isRightMix, isVoteMix));    
            mix.toXML(LabelUtil.singleton().noComponents(), out);
            out.close();            
        }
        super.setMix(block, n, isRightMix, isVoteMix, mix);
    }
        
    public void setElectoralRollCapabilities(int block, ElectoralRollCapabilities erc) {
        if (erc != null) {
            PrintWriter out = getFilePrintWriter("electoralRollCapabilities_block"+block);    
            erc.toXML(out); 
            out.close();
        }
        super.setElectoralRollCapabilities(block, erc);        
    }
    public ElectoralRollCapabilities getElectoralRollCapabilities(int block) {
        if (super.getElectoralRollCapabilities(block) == null) {
            String cachename = "electoralRollCapabilities_block"+block;
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElectoralRollCapabilities d = ElectoralRollCapabilities.fromXML(reader);
                    super.setElectoralRollCapabilities(block, d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectoralRollCapabilities(block);
    }
    public void setElectoralRollCapabilitySharesForVoterBlock(int voterBlock, int tellerIndex, ElectoralRollCapabilityShares erc) {
        if (erc != null) {
            String cachename = electoralRollCapabilitySharesForVoterBlockCacheName(voterBlock, tellerIndex);
            PrintWriter out = getFilePrintWriter(cachename); 
            erc.toXML(out); 
            out.close();
        }
        super.setElectoralRollCapabilitySharesForVoterBlock(voterBlock, tellerIndex, erc);        
    }
    public ElectoralRollCapabilityShares getElectoralRollCapabilitySharesForVoterBlock(int voterBlock, int tellerIndex) {
        if (super.getElectoralRollCapabilitiesForVoterBlock(voterBlock) == null) {
            String cachename = electoralRollCapabilitySharesForVoterBlockCacheName(voterBlock, tellerIndex);
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElectoralRollCapabilityShares d = ElectoralRollCapabilityShares.fromXML(reader);
                    super.setElectoralRollCapabilitySharesForVoterBlock(voterBlock, tellerIndex, d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectoralRollCapabilitySharesForVoterBlock(voterBlock, tellerIndex);
    }
    private String electoralRollCapabilitySharesForVoterBlockCacheName(int voterBlock, int tellerIndex) {
        return "electoralRollCapabilityShares_teller"+tellerIndex+"_voterBlock"+voterBlock;
    }
    public boolean hasElectoralRollCapabilitySharesForVoterBlock(int voterBlock, int tellerIndex) {
        if (super.hasElectoralRollCapabilitySharesForVoterBlock(voterBlock, tellerIndex)) return true;
        String cachename = electoralRollCapabilitySharesForVoterBlockCacheName(voterBlock, tellerIndex);
        return cachefileExists(cachename);
    }

    public void setElectoralRollCapabilitiesForVoterBlock(int voterBlock, ElectoralRollCapabilities erc) {
        if (erc != null) {
            PrintWriter out = getFilePrintWriter("electoralRollCapabilities_voterBlock"+voterBlock); 
            erc.toXML(out); 
            out.close();
        }
        super.setElectoralRollCapabilitiesForVoterBlock(voterBlock, erc);        
    }
    public ElectoralRollCapabilities getElectoralRollCapabilitiesForVoterBlock(int voterBlock) {
        if (super.getElectoralRollCapabilitiesForVoterBlock(voterBlock) == null) {
            String cachename = "electoralRollCapabilities_voterBlock"+voterBlock;
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    ElectoralRollCapabilities d = ElectoralRollCapabilities.fromXML(reader);
                    super.setElectoralRollCapabilities(voterBlock, d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        return super.getElectoralRollCapabilitiesForVoterBlock(voterBlock);
    }
    
    private static String ttMixHashRevelationCacheName(int tellerIndex, boolean isVoteMix, int block) {
        
        StringBuffer sb = new StringBuffer();
        sb.append(isVoteMix?"voteMix":"capabilityMix");
        sb.append("HashRevel");
        sb.append(block);
        sb.append('I');
        sb.append(""+tellerIndex);
        return sb.toString();
    }
    public void setTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block, MixHashRevelation mhr) {
        if (mhr != null) {
            PrintWriter out = getFilePrintWriter(ttMixHashRevelationCacheName(tellerIndex, isVoteMix, block));    
            mhr.toXML(out); 
            out.close();            
        }
        super.setTTMixHashRevelation(tellerIndex, isVoteMix, block, mhr);        
    }
    public boolean hasTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block) {
        if (super.hasTTMixHashRevelation(tellerIndex, isVoteMix, block)) return true;
        String cachename = ttMixHashRevelationCacheName(tellerIndex, isVoteMix, block);
        return cachefileExists(cachename);
    }
    public MixHashRevelation getTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block) {
        if (super.getTTMixHashRevelation(tellerIndex, isVoteMix, block) == null) {
            String cachename = ttMixHashRevelationCacheName(tellerIndex, isVoteMix, block);
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    MixHashRevelation d = MixHashRevelation.fromXML(reader);
                    super.setTTMixHashRevelation(tellerIndex, isVoteMix, block, d);
                    return d;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        
        return super.getTTMixHashRevelation(tellerIndex, isVoteMix, block);
    }

    private static String ttVoterBlockContentCacheName(int voterBlock, int tellerIndex) {        
        StringBuffer sb = new StringBuffer();
        sb.append("ttVoterBlockContent");
        sb.append(voterBlock);
        sb.append('I');
        sb.append(""+tellerIndex);
        return sb.toString();
    }
    public void setTabTellerVoterBlockContents(int voterBlock, int tellerIndex, TabTellerVoterBlockContents c) {
        if (c != null) {
            PrintWriter out = getFilePrintWriter(ttVoterBlockContentCacheName(voterBlock, tellerIndex));           
            c.toXML(LabelUtil.singleton().noComponents(),out); 
            out.close();            
        }
        super.setTabTellerVoterBlockContents(voterBlock, tellerIndex, c);
    }
    public TabTellerVoterBlockContents getTabTellerVoterBlockContents(int voterBlock, int tellerIndex) {
        if (super.getTabTellerVoterBlockContents(voterBlock, tellerIndex) == null) {
            String cachename = ttVoterBlockContentCacheName(voterBlock, tellerIndex);
            // try getting it from file
            Reader reader = getFileContents(cachename);
            if (reader != null) {
                try {
                    TabTellerVoterBlockContents c = TabTellerVoterBlockContents.fromXML(  reader);
                    return c;
                }
                catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    failedCaching(cachename);
                }
                finally {
                    try {
                        reader.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }            
        }
        
        return super.getTabTellerVoterBlockContents(voterBlock, tellerIndex);
    } 
}