/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.bboard.server;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import jif.lang.LabelUtil;
import civitas.common.Protocol;
import civitas.crypto.CryptoUtil;
import civitas.crypto.PublicKey;

/**
 * Implements file-based bulletin board storage. Best effort is
 * made for persistent writes.
 *
 */
class FileBBStorage extends Protocol implements BBStorage {
    /**
     * Radix to convert timestamps from longs to strings.
     */
    private static final int TIME_STAMP_RADIX = 16;
    /*
     * Some file name constants
     */
    private static final String INDEX_FILENAME = "bboardIndex";
    private static final String OWNER_PK_FILENAME = "ownerPublicKey.xml";
    private static final String BOARD_CLOSED_FILENAME = "board_closed";
    private static final String TEMP_FILE_PREFIX = "tmp_";

    /**
     * Root of the directory for this service to store bulletin board data.
     */
    private final File root;

    /**
     * Bulletin board service
     */
    private final GenericBBS bbs;

    FileBBStorage(File root, GenericBBS bbs) {
        this.root = root;
        this.bbs = bbs;
        if (!root.exists()) {
            // try to create the file root.
            root.mkdirs();
        }
        else {
            // the root exists. Pre-populate boardNames
            File[] files = root.listFiles();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    bbs.addBoardName(file.getName());
                }                
            }
        }            
    }
    
    /**
     * Return the appropraite directory to store posts with metadata 
     * meta in bulletin board directory bboardDir.
     */
    private File getMetaDir(File bboardDir, String meta) {
        StringBuffer sb = new StringBuffer(meta.length());
        for (int i = 0; i < meta.length(); i++) {
            char c = meta.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(c);
            }            
            else if (':' == c) {
                sb.append(File.separatorChar);
            }
            else {
                sb.append('_');                
            }
        }

        File f = new File(bboardDir, sb.toString());

        return f;
    }

    /*
     *  return the file name of a post
     */
    private static String filenameForPost(long time, int unique) { 
        String timeString = Long.toString(time, TIME_STAMP_RADIX);
        if (unique == 0) {
            return timeString;
        }
        else {
            return timeString + "_" + unique;            
        }
    }


    public void closeBoard(String bbid) throws IOException {
        File closedMarker = new File(new File(root, bbid), BOARD_CLOSED_FILENAME);
        closedMarker.createNewFile();            
    }
    public boolean isBoardClosed(String bbid) {
        File bboardRoot = new File(root,bbid);
        return new File(bboardRoot, BOARD_CLOSED_FILENAME).exists();
    }

    private static final Comparator<File> filenameComparator = new Comparator<File>() {
        public int compare(File o1, File o2) {
            return o1.getName().compareTo(o2.getName());
        }

    };

    public void processPosts(PostProcessor pp, String bbName, String meta, String fromTime, String toTime) throws IOException {
        File bboardRoot = new File(root,bbName);

        File[] posts = getPostsMatchingCriteria(bboardRoot, meta, fromTime, toTime);

        for(File m : posts) {
            BBStoragePost post = readPostFromFile(bbName, m);            
            if (post == null) continue;
            if (metaMatches(meta, post.meta)) {
                pp.processPost(post);
            }
        }
    }

    public long post(String bbName, String meta, String mesg, String sign) throws IOException {
        // get the directory for the meta
        File bboardRoot = new File(root,bbName);
        File metaDir = getMetaDir(bboardRoot, meta);
        while (!metaDir.exists()) {
            metaDir.mkdirs();
        }

        // create file at path board/meta
        // make sure that we have a unique file name by taking a hash of the meta and msg.
        long t = System.currentTimeMillis();
        int unique = (meta==null?0:meta.hashCode()) ^ 
                     (mesg==null?0:mesg.hashCode()) ^
                     (sign==null?0:sign.hashCode());
        String filename = filenameForPost(t, unique);
        File f = new File(metaDir, filename);
        File tempFile = new File(metaDir, TEMP_FILE_PREFIX + filename);

        writePostToFile(bbName, f, tempFile, t, meta, mesg, sign);
        return t;
    }

    /**
     * Output a post to a file
     */
    private void writePostToFile(String bbid, File f, File tempFile, long t, String meta, String mesg, String sign) throws IOException {
        FileOutputStream fos = new FileOutputStream(tempFile);
        PrintStream fout = new PrintStream(fos);
        fout.println(t); 
        protocolOutputString(fout, meta);
        protocolOutputString(fout, mesg);
        protocolOutputString(fout, sign);
        fout.println();
        fout.flush();
        fout.close();
        
        tempFile.renameTo(f);
        f.setReadOnly();
    }
    /**
     * Input a post from a file
     */
    private BBStoragePost readPostFromFile(String bbid, File m) throws IOException {
        FileInputStream fis = new FileInputStream(m);
        try {
            BufferedReader fin = new BufferedReader(new InputStreamReader(fis));
            // get data
            String stamp = fin.readLine();
            String meta = protocolInputString(fin);
            String mesg = protocolInputString(fin);
            String sig = protocolInputString(fin);
            fin.close();
            return new BBStoragePost(Long.parseLong(stamp), meta, mesg, sig);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
    private boolean metaMatches(String metaCriteria, String meta) {
        return metaCriteria == null || metaCriteria.length() == 0 ||
        metaCriteria.equals(meta);
    }

    private File[] getPostsMatchingCriteria(File bboardRoot, String metaCriteria, String fromTime, String toTime) {
        TimeFileFilter tff = new TimeFileFilter(fromTime,
                                                toTime);

        File[] posts = null;
        if (metaCriteria != null && metaCriteria.length() > 0) {
            File dir = getMetaDir(bboardRoot, metaCriteria);
            posts = dir.listFiles(tff);
            if (posts == null) {
                // the meta directory doesn't exist, so return an empty array
                posts = new File[0];
            }
        }
        else {
            File[] dirs = bboardRoot.listFiles();            
            ArrayList<File> allFiles = new ArrayList<File>();
            if (dirs != null) {
                for (int i = 0; i < dirs.length; i++) {
                    if (dirs[i].isDirectory()) {
                        File[] files = dirs[i].listFiles(tff);
                        if (files != null) {
                            for (int j = 0; j < files.length; j++) {
                                allFiles.add(files[j]);
                            }
                        }
                    }
                }
            }
            else {
                System.err.println("dirs was null for " + bboardRoot);
            }
            posts = allFiles.toArray(new File[0]);            
        }

        Arrays.sort(posts, filenameComparator);
        return posts;
    }
    public void storeAcceptance(String boardName, PublicKey ownerPublicKey) throws IOException {
        File bboardRoot = new File(root,boardName);
        bboardRoot.mkdirs();
        if (ownerPublicKey != null) {
            storeOwnerPublicKey(boardName, ownerPublicKey);
        }
    }
    public void storeIndex(String id, int index) throws IOException {
        // put a file into the BB directory
        File bboardRoot = new File(root,id);
        File indexFile = new File(bboardRoot, INDEX_FILENAME);
        File tempFile = new File(bboardRoot, TEMP_FILE_PREFIX + INDEX_FILENAME);
        FileOutputStream fos = new FileOutputStream(tempFile);
        PrintStream out = new PrintStream(fos);
        out.println(index);
        out.close();
        tempFile.renameTo(indexFile);
    }
    public int retrieveIndex(String id) throws IOException {
        File bboardRoot = new File(root,id);
        File indexFile = new File(bboardRoot, INDEX_FILENAME);
        FileInputStream fis = new FileInputStream(indexFile);
        BufferedReader r = new BufferedReader(new InputStreamReader(fis));
        String s = r.readLine();
        r.close();
        return Integer.parseInt(s);
    }
    public void storeOwnerPublicKey(String id, PublicKey ownerPublicKey) throws IOException {
        // put a file into the BB directory
        File bboardRoot = new File(root,id);
        File ownerPubKeyFile = new File(bboardRoot, OWNER_PK_FILENAME);
        File tempFile = new File(bboardRoot, TEMP_FILE_PREFIX + OWNER_PK_FILENAME);
        PrintWriter out = new PrintWriter(tempFile);
        ownerPublicKey.toXML(LabelUtil.singleton().noComponents(), out);
        out.close();
        tempFile.renameTo(ownerPubKeyFile);

    }
    public PublicKey retrieveOwnerPublicKey(String id) throws IOException {
        File bboardRoot = new File(root,id);
        File ownerPubKeyFile = new File(bboardRoot, OWNER_PK_FILENAME);
        FileInputStream fis = new FileInputStream(ownerPubKeyFile);
        BufferedReader r = new BufferedReader(new InputStreamReader(fis));
        return CryptoUtil.factory().publicKeyFromXML(LabelUtil.singleton().noComponents(), r);
    }

    public String storageDir(String bbid) {
        return new File(root,bbid).getAbsolutePath();
    }
    private static class TimeFileFilter implements FilenameFilter {
        private final long fromCriteria, toCriteria;
        public TimeFileFilter(long fromCriteria, long toCriteria) { 
            this.fromCriteria = fromCriteria; 
            this.toCriteria = toCriteria; 
        }

        public TimeFileFilter(String fromTime, String toTime) {
            long fr = Long.MIN_VALUE;
            if (fromTime != null && fromTime.length() > 0) {
                try {
                    fr = Long.parseLong(fromTime);
                }
                catch (NumberFormatException e) {
                }
            }
            this.fromCriteria = fr;

            long t = Long.MAX_VALUE;
            if (toTime != null && toTime.length() > 0) {
                try {
                    t = Long.parseLong(toTime);
                }
                catch (NumberFormatException e) {
                }
            }
            this.toCriteria = t;
        }
        /**
         * Should we accept this file as part of the initial list? Filename is of form
         * timestamp_metaHash
         */
        public boolean accept(File dir, String name) {
            if (name.startsWith(TEMP_FILE_PREFIX)) return false;
            String[] parts = name.split("_");
            String filenameTimestamp = parts[0];
            long timestamp = Long.parseLong(filenameTimestamp, TIME_STAMP_RADIX);
            return fromCriteria<timestamp && timestamp<toCriteria;
        }
    }

}
