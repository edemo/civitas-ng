/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */ 
package civitas.bboard.server;

import java.io.IOException;

import civitas.crypto.PublicKey;

/**
 * This interface abstracts the storage mechanism for bulletin boards.
 *
 */
interface BBStorage {
    interface PostProcessor {
        void processPost(BBStoragePost p) throws IOException;
    }

    /**
     * Closes a bulletin board, preventing further posts to the board
     * @param bbid bulletin board to close
     * @throws IOException if unable to close bulletin board successfully
     */
    void closeBoard(String bbid) throws IOException;

    /**
     * Stores the index of this bulletin board within the election id.
     * An election may have multiple bulletin boards (for example, for use as 
     * ballot boxes), and each bulletin board has a unique index within the
     * election. 
     * @param id
     * @param index
     * @throws IOException
     */
    void storeIndex(String id, int index) throws IOException;

    /**
     * Return the index of this bulletin board within the election id.
     * @param bbid
     * @return
     * @throws IOException
     */
    int retrieveIndex(String bbid) throws IOException;
    
    /**
     * Record that this service has agreed to store posts
     * for bulletin board bbid, owned by ownerPublicKey.
     * 
     * @param bbid
     * @param ownerPublicKey
     * @throws IOException
     */
    void storeAcceptance(String bbid, PublicKey ownerPublicKey)
            throws IOException;

    /**
     * Return the storage directory for bulletin board bbid
     * @param bbid
     * @return
     */
    String storageDir(String bbid);

    /**
     * Return the public key of the owner of bulletin board bbid,
     * as recorded in the previous call to storeAcceptance(String, PublicKey).
     * @param bbid
     * @return
     * @throws IOException
     */
    PublicKey retrieveOwnerPublicKey(String bbid) throws IOException;

    /**
     * Post a message to bulletin board bbid.
     * @param bbid bulletin board to post to
     * @param meta metadata for searching
     * @param mesg message to post
     * @param sign signature of message
     * @return timestamp of message
     * @throws IOException
     */
    long post(String bbid, String meta, String mesg, String sign)
            throws IOException;

    /**
     * Is bulletin board bbid closed (no longer accepting posts)?
     * @param bbid
     * @return
     */
    boolean isBoardClosed(String bbid);

    /**
     * Query bulletin board bbid, and call PostProcessor pp on the results
     * of the query. Query parameters may be null, and results must satisfy
     * all parameters
     * @param pp
     * @param bbid
     * @param meta if non null, only posts where the metadata equals meta are returned
     * @param fromTime if non null, only posts made after fromTime are returned
     * @param toTime if non null, only posts made before toTime are returned.
     * @throws IOException
     */
    void processPosts(PostProcessor pp, String bbid, String meta,
            String fromTime, String toTime) throws IOException;

}
