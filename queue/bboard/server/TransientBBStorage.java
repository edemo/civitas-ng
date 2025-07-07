/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.bboard.server;

import java.io.IOException;
import java.util.*;

import civitas.crypto.PublicKey;

/**
 * Implements in-memory bulletin board storage.
 *
 */
class TransientBBStorage  implements BBStorage {

    /**
     * Set of bbids of open bulletin boards
     */
    private Set<String> openBoards = new HashSet<String>();
    
    /**
     * Map from bulletin board ids to boards.
     */
    private Map<String, Board> boards = new HashMap<String, Board>();

    TransientBBStorage() {
    }


    public void closeBoard(String bbid) throws IOException {
        openBoards.remove(bbid);
    }
    public boolean isBoardClosed(String bbid) {
        return !openBoards.contains(bbid);
    }


    public long post(String bbid, String meta, String mesg, String sign) throws IOException {
        if (isBoardClosed(bbid)) return -1;
        Board b = boards.get(bbid);
        if (b == null) return -1;
        BBStoragePost p = new BBStoragePost(System.currentTimeMillis(), meta, mesg, sign);
        b.allPosts.add(p);
        List<BBStoragePost> metaList = b.metaIndex.get(meta);
        if (metaList == null) {
            metaList = new ArrayList<BBStoragePost>(200);
            b.metaIndex.put(meta, metaList);
        }
        metaList.add(p);
        return p.timestamp;
    }

    public void processPosts(PostProcessor pp, String bbid, String meta, String fromTime, String toTime) throws IOException {
        long from = Long.MIN_VALUE;
        long to = Long.MAX_VALUE;
        Board b = boards.get(bbid);
        if (b == null) return;
        List<BBStoragePost> l = b.allPosts;
        if (meta != null) {
            l = b.metaIndex.get(meta);
        }
        if (l == null) return;
        

        for(BBStoragePost p : l) {
            if (from <p.timestamp && p.timestamp < to) {
                pp.processPost(p);
            }
        }
    }
    public void storeAcceptance(String bbid, PublicKey ownerPublicKey) throws IOException {
        Board b = boards.get(bbid);
        if (b == null) {
            b = new Board();
            boards.put(bbid, b);
        }
        b.ownerPK = ownerPublicKey;
        openBoards.add(bbid);        
    }
    public void storeIndex(String bbid, int index) throws IOException {
        Board b = boards.get(bbid);
        if (b == null) {
            return;
        }
        b.index = index;
    }
    public int retrieveIndex(String bbid) throws IOException {
        Board b = boards.get(bbid);
        if (b == null) {
            return 0 ;
        }
        return b.index;
    }
    public PublicKey retrieveOwnerPublicKey(String bbid) throws IOException {
        Board b = boards.get(bbid);
        if (b == null) {
            return null;
        }
        return b.ownerPK;
    }

    public String storageDir(String bbid) {
        return "N/A";
    }
    
    /**
     * In memory representation of a bulletin board.
     * It contains a map from meta data to lists of posts
     * with that meta data.
     */
    private class Board {
        Map<String, List<BBStoragePost>> metaIndex = new HashMap<String, List<BBStoragePost>>();
        List<BBStoragePost> allPosts = new ArrayList<BBStoragePost>(1000);
        PublicKey ownerPK;
        int index;
    }    
}
