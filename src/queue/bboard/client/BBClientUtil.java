/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.bboard.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import civitas.bboard.common.BBPost;
import civitas.common.BulletinBoardID;
import civitas.common.ElectionDetails;
import civitas.common.ElectionID;
import civitas.common.Host;
import civitas.common.Protocol;
import civitas.common.TellerDetails;
import civitas.common.Util;
import civitas.common.XMLDeserializer;
import civitas.common.XMLSerializable;
import civitas.crypto.CryptoException;
import civitas.crypto.CryptoFactory;
import civitas.crypto.CryptoUtil;
import civitas.crypto.MessageDigest;
import civitas.crypto.MessageDigestWriter;
import civitas.crypto.PrivateKey;
import civitas.crypto.PublicKey;
import civitas.crypto.PublicKeyMsg;
import civitas.crypto.Signature;

/**
 * Utility methods for a bulletin board client.
 */
public class BBClientUtil extends Protocol {
	private static final CharSequence SENTINEL = "SENTINEL";
	/*
	 * The host, port, and bbid are fixed for a given BBClientUtil object. Together,
	 * these three fields uniquely specify a bulletin board instance.
	 */
	public final String bbhost;
	public final int bbport;
	public final String bbid;

	/**
	 * Instantiate a client util for the host bbhost communicating on port bbport.
	 * Note that no bulletin board id is specified. This constructor should only be
	 * used when creating a new board.
	 */
	public BBClientUtil(String bbhost, int bbport) {
		super();
		this.bbhost = bbhost;
		this.bbport = bbport;
		this.bbid = null;
	}

	/**
	 * Instantiate a client util for the bulletin board bbid on host bbhost
	 * communicating on port bbport.
	 */
	public BBClientUtil(String bbhost, int bbport, String bbid) {
		super();
		this.bbhost = bbhost;
		this.bbport = bbport;
		this.bbid = bbid;
	}

	/**
	 * Instantiate a client util for the bulletin board bbid on the host
	 */
	public BBClientUtil(Host host, String bbid) {
		super();
		String bbhost = null;
		int bbport = 0;

		if (host != null) {
			bbhost = host.address;
			bbport = host.port;
		}
		this.bbhost = bbhost;
		this.bbport = bbport;
		this.bbid = bbid;
	}

	/**
	 * Instantiate a client util for the bulletin board specified.
	 */
	public BBClientUtil(BulletinBoardID bb) {
		super();
		String bbhost = null;
		int bbport = 0;
		String bbid = null;

		if (bb != null) {
			bbhost = bb.host;
			bbport = bb.port;
			bbid = bb.id;
		}
		this.bbhost = bbhost;
		this.bbport = bbport;
		this.bbid = bbid;
	}

	/**
	 * Create a new bulletin board. Returns the id of the bulletin board.
	 */
	public String newBoard(final PublicKey owner) throws IOException, IllegalArgumentException {
		if (owner == null) {
			throw new IllegalArgumentException("Must provide a BB owner public key");
		}
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		// session key and things still to do.

		output.println("NEWBOARD");
		owner.toXML(output);
		output.flush();

		String bbID = input.readLine();
		s.close();

		return bbID;
	}

	/**
	 * Close the bulletin board. This will prevent new messages from being post. The
	 * signature is of the BB id with the owner's private key. If postHashTo is not
	 * null, the BB will post a hash of the contents to the specified bulletin
	 * board.
	 */
	public boolean closeBoard(ElectionID electionID, PrivateKey supervisorPrivateKey, int numVoters)
			throws IOException, IllegalArgumentException {
		if (electionID == null) {
			throw new IllegalArgumentException("Must provide an election ID");
		}
		Signature sig = null;
		try {
			sig = CryptoUtil.factory().signature(supervisorPrivateKey,
					CryptoUtil.factory().publicKeyMsg(electionID.id));
		} catch (NullPointerException imposs) {
		} catch (CryptoException ignore) {
		}
		return closeBoard(sig, electionID, numVoters);
	}

	/**
	 * Close the bulletin board. This will prevent new messages from being post. The
	 * signature is of the BB id with the owner's private key. If postHashTo is not
	 * null, the BB will post a hash of the contents to the specified bulletin
	 * board.
	 */
	public boolean closeBoard(Signature sig, ElectionID postHashTo, int numVoterBlocks)
			throws IOException, IllegalArgumentException {
		if (sig == null) {
			throw new IllegalArgumentException("Must provide a signature of the BBID under the owner public key");
		}
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		output.println("CLOSEBOARD");
		output.println(bbid);
		if (postHashTo != null) {
			postHashTo.toXML(output);
			output.println(numVoterBlocks);
		}
		sig.toXML(output);
		output.flush();

		String response = input.readLine();
		s.close();

		return Boolean.parseBoolean(response);
	}

	/**
	 * Post message to the bulletin board. Returns the timestamp of the message.
	 */
	public String post(String meta, XMLSerializable msg) throws IOException, IllegalArgumentException {
		return post(meta, msg, (PrivateKey) null);
	}

	/**
	 * Post message to the bulletin board, signing it using signingKey. Returns the
	 * timestamp of the message.
	 */
	public String post(String meta, XMLSerializable msg, PrivateKey signingKey)
			throws IOException, IllegalArgumentException {
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		// session key and things still to do.
		output.println("POST");

		// send the bbid
		output.println(bbid);

		// send the meta information, first the number of chars as an int, then the
		// chars
		output.append(meta);

		PrintWriter msgOutput = output;
		MessageDigest md = null;
		if (signingKey != null) {
			// we need to sign the message.
			try {
				md = CryptoUtil.factory().messageDigest();
			} catch (NullPointerException imposs) {
			}
			msgOutput = new PrintWriter(new MessageDigestWriter(output, md));
		}
		// send the message.
		if (msg != null) {
			msg.toXML(msgOutput);
		}
		msgOutput.flush();
		output.append(SENTINEL);

		// do we need to send a signature?
		if (md != null) {
			Signature sig = null;
			try {
				sig = CryptoUtil.factory().signature(signingKey, md.digest());
			} catch (NullPointerException imposs) {
			}
			if (sig != null) {
				sig.toXML(output);
			}
		}

		output.append(SENTINEL);

		output.flush();

		// get the timestamp that the server is sending back
		String stamp = input.readLine();
		s.close();

		return stamp;
	}

	/**
	 * Retrieve a list of all messages posted to the bulletin board. Returns a List
	 * of BBPost. No signature from the BB is requested or checked.
	 */
	public List<BBPost> retrieve(XMLDeserializer deserializer) throws IOException, IllegalArgumentException {
		return retrieve(deserializer, null);
	}

	/**
	 * Retrieve a list of all messages posted to the bulletin board. Returns a List
	 * of BBPost. If bbPublicKey is non-null, a signature is requested from the BB
	 * and checked; if the check fails, a null list is returned.
	 */
	public List<BBPost> retrieve(XMLDeserializer deserializer, PublicKey bbPublicKey)
			throws IOException, IllegalArgumentException {
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		// session key and things still to do.

		if (bbPublicKey != null) {
			output.println("RETRIEVE_SIGNED");
		} else {
			output.println("RETRIEVE_UNSIGNED");
		}

		// send the bbid
		output.println(bbid);

		// that's it, now send it off to the BB server.
		output.flush();

		// retrieve the list of things.
		List<BBPost> l = inputList(input, deserializer, bbPublicKey, null);

		s.close();

		return l;
	}

	/**
	 * Retrieve a list of all messages posted to the bulletin board matching the
	 * search criteria. If the argument meta is non-null and non-empty, then all
	 * messages returned will have that value in the meta field. If fromTime is
	 * non-null, then all messages returned will be after fromTime. If toTime is
	 * non-null then all messages return will be before toTime. If bbPublicKey is
	 * non-null, a signature is requested from the BB and checked. If requiredHash
	 * is not null, then the hash returned from the BB is checked against this.
	 * Returns a List of BBPost.
	 */
	public List<BBPost> retrieveParams(XMLDeserializer deserializer, PublicKey bbPublicKey, String requiredHash, String meta,
			Long fromTime, Long toTime) throws IOException, IllegalArgumentException {
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		// session key and things still to do.

		if (bbPublicKey != null) {
			output.println("RETRIEVE_PARAMS_SIGNED");
		} else {
			output.println("RETRIEVE_PARAMS_UNSIGNED");
		}

		// send the bbid
		output.println(bbid);

		// send the meta
		output.append(meta);

		// send the fromTime and toTime, as strings.
		if (fromTime != null) {
			output.println(fromTime.toString());
		} else {
			output.println();
		}
		if (toTime != null) {
			output.println(toTime.toString());
		} else {
			output.println();
		}

		// that's it, now send it off to the BB server.
		output.flush();

		// retrieve the list of things.
		List<BBPost> l = inputList(input, deserializer, bbPublicKey, requiredHash);

		s.close();

		return l;
	}

	/**
	 * Retrieve a list of all messages posted to the bulletin board matching the
	 * search criteria. If the arugment meta is non-null and non-empty, then all
	 * messages returned will have that value in the meta field. If fromTime is
	 * non-null, then all messages returned will be after fromTime. If toTime is
	 * non-null then all messages return will be before toTime. Returns a List of
	 * BBPost.
	 */
	public List<BBPost> retrieveParams(XMLDeserializer deserializer, String meta, Long fromTime, Long toTime)
			throws IOException, IllegalArgumentException {
		return retrieveParams(deserializer, null, null, meta, fromTime, toTime);
	}

	/**
	 * Retrieve a hash of the bulletin board messages that satisfy the search
	 * criteria.
	 */
	public byte[] retrieveHash(final PublicKey bbPublicKey, String meta, Long fromTime, Long toTime)
			throws IOException, IllegalArgumentException {
		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		// session key and things still to do.

		output.println("RETRIEVE_HASH");

		// send the bbid
		output.println(bbid);

		// send the meta
		output.append(meta);

		// send the fromTime and toTime, as strings.
		if (fromTime != null) {
			output.println(fromTime.toString());
		} else {
			output.println();
		}
		if (toTime != null) {
			output.println(toTime.toString());
		} else {
			output.println();
		}

		// that's it, now send it off to the BB server.
		output.flush();

		// retrieve the hash result.
		String hashString = null;
		try {
			hashString = inputHashAndSignature(input, bbPublicKey);
		} catch (CryptoException e) {
			// ignore it, just let the hash fail.
		}
		s.close();

		// convert the hash string into a byte array.
		return Util.stringToBytes(hashString);
	}

	/**
	 * Read in the hash, and a signature of the hash. If the has verifies, it is
	 * returned, otherwise null is returned.
	 */
	private String inputHashAndSignature(BufferedReader input, PublicKey bbPublicKey)
			throws IOException, IllegalArgumentException, CryptoException {

		String hash = input.readLine();
		CryptoFactory factory = CryptoUtil.factory();
		if (factory == null)
			return null;
		Signature sig = factory.signatureFromXML(input);
		PublicKeyMsg hashMsg = factory.publicKeyMsg(hash);
		if (bbPublicKey == null || factory.publicKeyVerifySignature(bbPublicKey, sig, hashMsg)) {
			// the hash verifies, or we weren't given a public key.
			return hash;
		}
		return null;
	}

	/**
	 * Read in a list of bulletin board posts.
	 */
	private List<BBPost> inputList(BufferedReader input, XMLDeserializer deserializer, PublicKey bbPublicKey,
			String requiredHash) throws IOException, IllegalArgumentException {
		if (bbid == null || input == null || !bbid.equals(input.readLine())) {
			throw new IOException("Non-matching bbids");
		}
		if (deserializer == null) {
			throw new IllegalArgumentException("No deserializer");
		}
		MessageDigest md = null;
		if (bbPublicKey != null) {
			try {
				md = CryptoUtil.factory().messageDigest();
			} catch (NullPointerException imposs) {
			}
		}

		List<BBPost> list = new ArrayList<BBPost>();
		String timestamp = input.readLine();
		while (!"<END>".equals(timestamp)) {
			// read in the next post
			long stamp = -1;
			try {
				stamp = Long.parseLong(timestamp);
			} catch (NumberFormatException e) {

			}
			String meta = input.readLine();
			boolean succesfulParse = true;
			XMLSerializable msg = null;
			try {
				msg = deserializer.fromXML(input);
			} catch (IllegalArgumentException ignore) {
				succesfulParse = false;
			} catch (IOException ignore) {
				succesfulParse = false;
			}

			if (succesfulParse) {
				Signature signature = null;
				if (Util.isNextTag(input, Signature.OPENING_TAG)) {
					try {
						signature = CryptoUtil.factory().signatureFromXML(input);
					} catch (NullPointerException imposs) {
					} catch (IllegalArgumentException ignore) {
					} catch (IOException ignore) {
					}
				}
				if (md != null && stamp > 0) {
					md.update(stamp);
					md.update(meta);
					MessageDigestWriter mdw = new MessageDigestWriter(null, md);
					if (msg != null) {
						msg.toXML(new PrintWriter(mdw));
					}
					if (signature != null) {
						signature.toXML(new PrintWriter(mdw));
					}
					mdw.flush();
				}
				BBPost p = new BBPost(bbid, stamp, meta, msg, signature);
				try {
					list.add(p);
				} catch (ClassCastException imposs) {
				} catch (IllegalArgumentException imposs) {
				}
			}
			Util.skipUntil(input, "<EOP>");
			timestamp = input.readLine();
		}

		if (bbPublicKey != null && md != null) {
			String hash = null;
			try {
				hash = inputHashAndSignature(input, bbPublicKey);
			} catch (CryptoException e) {
				// just ignore it, and let the hash fail.
			}
			// the BB has given us a signed hash. Does it agree with the result set?
			String computedHash = Util.constBytesToString(md.digest());

			if (!(hash != null && hash.equals(computedHash))) {
				// bad hashing! the computed hash did not match the hash
				// sent by the BB
				return null;
			}
			if (requiredHash != null && !requiredHash.equals(computedHash)) {
				// the computed hash did not match the required hash!
				return null;
			}
		}

		return list;
	}

	/**
	 * Request the bulletin board to participate in an election.
	 */
	public boolean requestParticipation(final ElectionDetails details, TellerDetails tellers)
			throws IOException, IllegalArgumentException {
		return participation("REQUEST_PARTICIPATION", details, tellers, null);
	}

	/**
	 * Confirm to the bulletin board that it is participating in an election.
	 */
	public boolean confirmParticipation(int index, ElectionDetails details, TellerDetails tellers)
			throws IOException, IllegalArgumentException {
		return participation("CONFIRM_PARTICIPATION", details, tellers, new Integer(index));
	}

	private boolean participation(String kind, ElectionDetails details, TellerDetails tellers, Integer index)
			throws IOException, IllegalArgumentException {
		if (details == null)
			throw new IllegalArgumentException("null election details");
		if (tellers == null)
			throw new IllegalArgumentException("null teller details");

		Socket s = new Socket(bbhost, bbport);
		BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

		PrintWriter output = new PrintWriter(s.getOutputStream());

		output.println(kind);

		details.toXML(output);
		tellers.toXML(output);

		if (index != null) {
			output.println(index.toString());
		}

		output.flush();

		String response = input.readLine();
		s.close();

		return Util.stringToBoolean(response);
	}

}
