/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import civitas.bboard.client.BBClientUtil;
import civitas.bboard.common.BBPost;
import civitas.crypto.CryptoException;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.PublicKey;

/**
 * Utility methods for examining eand processing lections
 */
public class ElectionUtil {
	/*
	 * Election statuses
	 */
	public static final int STATUS_UNDEFINED = 0;
	public static final int STATUS_CREATED = 1; // no election details yet
	public static final int STATUS_DEFINED = 2; // election details, but no teller details
	public static final int STATUS_INITIALIZED = 3; // teller details and tab teller public key posted
	public static final int STATUS_STARTED = 4; // voting started
	public static final int STATUS_STOPPED = 5; // voting stopped

	public static final int STATUS_TABULATED = 6; // all tabulation has finished
	public static final int STATUS_FINALIZED = 8; // supervisor has posted results
	public static final int STATUS_ABANDONED = 999; // a teller has posted a reason to abandon

	public static String statusString(int s) {
		switch (s) {
		case STATUS_UNDEFINED:
			return "undefined";
		case STATUS_CREATED:
			return "created";
		case STATUS_DEFINED:
			return "defined";
		case STATUS_INITIALIZED:
			return "initialized";
		case STATUS_STARTED:
			return "started";
		case STATUS_STOPPED:
			return "stopped";

		case STATUS_TABULATED:
			return "tabulated";
		case STATUS_FINALIZED:
			return "finalized";
		case STATUS_ABANDONED:
			return "abandoned";

		default:
			return "<unknown status>";

		}
	}

	/**
	 * Retrieves the election status.
	 */
	public static int retrieveElectionStatus(ElectionID electionID, ElectionCache electionCache) throws IOException

	{
		if (electionID == null)
			return -1;

		final ElectionDetails details = retrieveElectionDetails(electionID, electionCache);
		if (details == null)
			return STATUS_CREATED;
		TellerDetails tellerDetails = retrieveTellerDetails(details, electionCache);

		// do some quick checks.
		// has the election been abandoned?
		if (tellerDetails != null && retrieveAbandonmentReason(electionID, electionCache) != null) {
			return STATUS_ABANDONED;
		}

		// is the election still uninitialized?
		if (tellerDetails == null || retrieveTabTellerSharedPublicKey(details, electionCache) == null) {
			return STATUS_DEFINED;
		}

		int numTabTellers = 0;
		try {
			numTabTellers = tellerDetails.tabulationTellers.length;
		} catch (NullPointerException ignore) {
		}

		// definitely initialized. has it been started, stopped, finalized, or tabulated
		// or is somewhere in the middle of tabulating?
		int status = STATUS_INITIALIZED;

		// look for a start, stop or finalize event.
		ElectionEvent[] events = retrieveElectionEvents(electionID, electionCache);

		if (events != null) {
			for (int i = 0; i < events.length; i++) {
				ElectionEvent e = null;
				try {
					e = events[i];
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}

				if (e != null && e.kind != null) {
					if (e.kind.equals(ElectionEvent.EVENT_KIND_START) && status == STATUS_INITIALIZED) {
						status = STATUS_STARTED;
					} else if (e.kind.equals(ElectionEvent.EVENT_KIND_STOP)) {
						status = STATUS_STOPPED;
					} else if (e.kind.equals(ElectionEvent.EVENT_KIND_FINALIZE)) {
						return STATUS_FINALIZED;
					} else {
						throw new IOException("Bad event kind " + e.kind);
					}
				}
			}
		}

		// if status == START or INITIALIZED, then that is indeed the
		// real status the real status is indeed STARTED
		if (status == STATUS_INITIALIZED || status == STATUS_STARTED) {
			return status;
		}

		// if status == STOPPED, then real status is any of STOPPED, TABULATED
		if (status == STATUS_STOPPED) {
			// have the tabulation results been posted?

			for (int i = 0; i < numTabTellers; i++) {
				if (retrieveTTElectionResults(electionID, tellerDetails, i + 1) == null) {
					// not yet tabulated
					return STATUS_STOPPED;
				}
			}

			status = STATUS_TABULATED;
		}
		return status;
	}

	/**
	 * Retrieves all valid election events for the specified election. Returns null
	 * or an array of size 0 if no events.
	 */
	public static ElectionEvent[] retrieveElectionEvents(ElectionID electionID, ElectionCache electionCache)
			throws IOException {
		if (electionID == null)
			return null;
		ElectionDetails details = retrieveElectionDetails(electionID, electionCache);
		if (details == null)
			return null;

		// see if the electionCache has events.
		ElectionEvent[] cachedEvents = null;
		if (electionCache != null) {
			cachedEvents = electionCache.getElectionEvents();
		}
		if (cachedEvents != null && cachedEvents.length > 0) {
			try {
				ElectionEvent last = cachedEvents[cachedEvents.length - 1];
				if (last != null) {
					if (ElectionEvent.EVENT_KIND_FINALIZE.equals(last.kind)) {
						// it's a finalzed event, this means that we've got
						// all the events we're going to
						return cachedEvents;
					}
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			} catch (NullPointerException ignore) {
			}
		}
		// look for a start, stop or finalize event.
		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectionEvent(), ElectionEvent.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}

		PublicKey supPubKey = details.supervisor;

		int size = l == null ? 0 : l.size();
		ElectionEvent[] events = new ElectionEvent[size < 0 ? 0 : size];
		int count = 0;
		// lastSeq is the sequence number of the last seen event that was correctly
		// signed by the supervisor
		int lastSeq = -1;
		for (int i = 0; l != null && i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
				if (post != null && !post.verify(supPubKey)) {
					// the message isn't signed by the supervisor
					post = null;
				}
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				// check it parses, and check the sequence number
				ElectionEvent e = null;
				try {
					e = (ElectionEvent) post.msg;
				} catch (ClassCastException ignore) {
					// we will not process this event.
					e = null;
				}
				if (e != null && e.sequence <= lastSeq) {
					// the sequence number is not strictly increasing.
					// It may be a replay attack, so ignore it.
					e = null;
				} else if (e != null) {
					// it's good
					lastSeq = e.sequence;
					try {
						events[count++] = e;
					} catch (ArrayIndexOutOfBoundsException imposs) {
					}
				}
			}
		}

		// shrink the array if needed
		if (count != events.length) {
			ElectionEvent[] n = new ElectionEvent[count];
			for (int i = 0; i < count; i++) {
				try {
					n[i] = events[i];
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}

			events = n;
		}

		if (electionCache != null) {
			electionCache.setElectionEvents(events);
		}
		return events;

	}

	/**
	 * Retrieves the election details from the specified bulletin board. Returns
	 * null if no details. Returns the first details that successfully parses.
	 */
	public static ElectionDetails

			retrieveElectionDetails(ElectionID electionID, ElectionCache electionCache) throws IOException {
		if (electionID == null)
			return null;
		if (electionCache != null && electionCache.getElectionDetails() != null)
			return electionCache.getElectionDetails();

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectionDetails(), ElectionDetails.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					ElectionDetails d = (ElectionDetails) post.msg;
					if (d != null) {
						PublicKey supPubKey = d.supervisor;
						// check that the election details is self certifying
						if (post.verify(supPubKey)) {
							if (electionCache != null) {
								electionCache.setElectionDetails(d);
							}
							return d;
						}
					}
				} catch (ClassCastException ignore) {
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves the election start time, returns null if no appropriate start time.
	 */
	public static Long

			retrieveElectionStartTime(final ElectionDetails electionDetails, ElectionCache electionCache)
					throws IOException

	{
		return retrieveElectionEventTime(electionDetails, electionCache, ElectionEvent.EVENT_KIND_START);
	}

	/**
	 * Retrieves the election stop time, returns null if no appropriate stop time.
	 */
	public static Long retrieveElectionStopTime(final ElectionDetails electionDetails, ElectionCache electionCache)
			throws IOException {
		return retrieveElectionEventTime(electionDetails, electionCache, ElectionEvent.EVENT_KIND_STOP);
	}

	/**
	 * Retrieves the election event time matching the kind, returns null if no
	 * appropriate event.
	 */
	private static Long retrieveElectionEventTime(final ElectionDetails electionDetails, ElectionCache electionCache,
			String eventKind) throws IOException {
		Long i = null;
		IOException ioex = null;
		try {
			i = retrieveElectionEventTime_(electionDetails, electionCache, eventKind);
		} catch (IOException e) {
			ioex = e;
		}
		IOException eioe = ioex;
		if (eioe != null)
			throw eioe;
		return i;
	}

	/**
	 * Retrieves the election event time matching the kind, returns null if no
	 * appropriate event.
	 */
	private static Long retrieveElectionEventTime_(final ElectionDetails electionDetails, ElectionCache electionCache,
			String eventKind) throws IOException

	{
		if (electionCache != null && eventKind != null) {
			if (eventKind.equals(ElectionEvent.EVENT_KIND_START) && electionCache.getElectionStartTime() != null) {
				return electionCache.getElectionStartTime();
			}
			if (eventKind.equals(ElectionEvent.EVENT_KIND_STOP) && electionCache.getElectionStopTime() != null) {
				return electionCache.getElectionStopTime();
			}
		}

		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectionEvent(), ElectionEvent.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		PublicKey supPubKey = electionDetails.supervisor;

		int size = l == null ? 0 : l.size();
		ElectionEvent[] events = new ElectionEvent[size < 0 ? 0 : size];
		int count = 0;
		// lastSeq is the sequence number of the last seen event that was correctly
		// signed by the supervisor
		int lastSeq = -1;
		for (int i = 0; l != null && i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
				if (post != null && !post.verify(supPubKey)) {
					// the message isn't signed by the supervisor
					post = null;
				}
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				// check it parses, and check the sequence number
				ElectionEvent e = null;
				try {
					e = (ElectionEvent) post.msg;
				} catch (ClassCastException ex) {
					// the event is not valid.
					// we will not process this event.
					e = null;
				}
				if (e != null && e.sequence <= lastSeq) {
					// the sequence number is not strictly increasing.
					// It may be a replay attack, so ignore it.
					e = null;
				} else if (e != null && e.kind != null) {
					if (e.kind.equals(eventKind)) {
						Long time = new Long(post.timestamp);

						try {
							if (electionCache != null && ElectionEvent.EVENT_KIND_START.equals(eventKind)) {
								electionCache.setElectionStartTime(time);
							} else if (electionCache != null && ElectionEvent.EVENT_KIND_STOP.equals(eventKind)) {
								electionCache.setElectionStopTime(time);
							}
						} catch (NullPointerException ignore) {
						}
						return time;
					}
				}
			}
		}

		return null;
	}

	public static CiphertextList retrieveCiphertextList(final ElectionDetails electionDetails,
			ElectionCache electionCache) throws IOException {
		IOException ex = null;
		CiphertextList d = null;
		try {
			d = doRetrieveCiphertextList(electionDetails, electionCache);
		} catch (IOException e) {
			ex = e;
		}
		IOException eex = ex;
		if (eex != null)
			throw eex;
		if (d != null) {
			return d;
		}
		return null;
	}

	private static CiphertextList doRetrieveCiphertextList(final ElectionDetails electionDetails,
			ElectionCache electionCache) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getCiphertextList() != null) {
			return electionCache.getCiphertextList();
		}

		CiphertextList d = null;
		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.CiphertextList(), CiphertextList.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
				if (post == null || !post.verify(electionDetails.supervisor)) {
					post = null;
				}
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					d = (CiphertextList) post.msg;
					break;
				} catch (ClassCastException ignore) {
				}
			}
		}
		if (d != null && electionCache != null) {
			electionCache.setCiphertextList(d);
		}
		return d;
	}

	/**
	 * Retrieve the content commitments posted by voter bulletin boards, verify the
	 * signatures (but not the hashes) and put them together into a
	 * BoardsForTabulation object.
	 */
	public static BoardsForTabulation retrieveVoterBBContentCommitments(final ElectionDetails electionDetails,
			ElectionCache cache) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		TellerDetails tellerDetails = retrieveTellerDetails(electionDetails, cache);

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.BoardClosedContentCommitment(), BoardClosedContentCommitment.META,
					null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		BoardClosedContentCommitment[] cc = new BoardClosedContentCommitment[100];
		int ccCount = 0;

		for (int i = 0; i < l.size(); i++) {
			BoardClosedContentCommitment bccc = null;
			try {
				BBPost post = l.get(i);
				if (post != null) {
					bccc = (BoardClosedContentCommitment) post.msg;
					// verify using the right key.
					if (bccc != null && !post.verify(voterBBPublicKey(tellerDetails, bccc.boardIndex))) {
						// the message is not signed correctly
						bccc = null;
					}
				}
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (bccc != null) {
				if (ccCount == cc.length) {
					// need to expand the array
					BoardClosedContentCommitment[] newCC = new BoardClosedContentCommitment[ccCount * 2];
					for (int k = 0; k < ccCount; k++) {
						try {
							newCC[k] = cc[k];
						} catch (ArrayIndexOutOfBoundsException imposs) {
						}
					}
					cc = newCC;
				}
				try {
					cc[ccCount++] = bccc;
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}
		if (ccCount != cc.length) {
			// shrink the array
			BoardClosedContentCommitment[] newCC = new BoardClosedContentCommitment[ccCount];
			for (int k = 0; k < ccCount; k++) {
				try {
					newCC[k] = cc[k];
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
			cc = newCC;
		}

		return new BoardsForTabulation(cc);
	}

	/**
	 * Retrieve the voter bulleting boards for tabulation that have been posted and
	 * signed by the election supervisor
	 */
	public static BoardsForTabulation retrieveBoardsForTabulation(final ElectionDetails electionDetails)
			throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.BoardsForTabulation(), BoardsForTabulation.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		final PublicKey supPubKey = electionDetails.supervisor;

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			String verifiedM = null;
			try {
				post = l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}
			if (post != null && post.verify(supPubKey)) {
				try {
					BoardsForTabulation bft = (BoardsForTabulation) post.msg;
					return bft;
				} catch (ClassCastException e) {
					// ignore, and try the next one...
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves the election results posted by teller i. Returns null if no results
	 * have been posted.
	 */
	public static ElectionResults retrieveTTElectionResults(ElectionID electionID, TellerDetails tellerDetails,
			int tellerIndex) throws IOException {
		if (electionID == null)
			return null;
		BBClientUtil bb = new BBClientUtil(electionID);
		try {
			List<BBPost> results = bb.retrieveParams(XMLDeserializers.ElectionResults(),
					ElectionResults.metaForTeller(tellerIndex), null, null);
			if (results == null)
				return null;
			// parse and verify the posts.
			for (int i = 0; i < results.size(); i++) {
				BBPost post = null;
				try {
					post = results.get(i);
					if (post != null && !post.verify(tabTellerPublicKey(tellerDetails, tellerIndex))) {
						// the message isn't signed by the teller
						post = null;
					}
				} catch (ClassCastException imposs) {
				} catch (IndexOutOfBoundsException imposs) {
				}
				if (post == null)
					continue;
				// parse and validate
				try {
					ElectionResults r = (ElectionResults) post.msg;
					try {
						if (r != null && r.computer.equals(ElectionResults.computerForTeller(tellerIndex))) {
							return r;
						}
					} catch (NullPointerException ignore) {
					}
				} catch (ClassCastException ignore) {
					// unable to parse, so it's an invalid post.
				}
			}
			return null;
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}

	}

	public static PublicKey tabTellerPublicKey(TellerDetails tellerDetails, int tellerIndex) {
		if (tellerDetails == null || tellerDetails.tabulationTellers == null)
			return null;
		try {
			Host t = tellerDetails.tabulationTellers[tellerIndex - 1];
			if (t != null)
				return t.publicKey;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	public static PublicKey regTellerPublicKey(TellerDetails tellerDetails, int tellerIndex) {
		if (tellerDetails == null || tellerDetails.registrationTellers == null)
			return null;
		try {
			Host t = tellerDetails.registrationTellers[tellerIndex - 1];
			if (t != null)
				return t.publicKey;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	public static PublicKey voterBBPublicKey(TellerDetails tellerDetails, int boardIndex) {
		if (tellerDetails == null || tellerDetails.voterBBs == null)
			return null;
		try {
			Host t = tellerDetails.voterBBs[boardIndex - 1];
			if (t != null)
				return t.publicKey;
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	/**
	 * Retrieves the teller details from the specified bulletin board. Returns null
	 * if no details. Returns the first details that successfully parses and
	 * validates
	 */
	public static TellerDetails retrieveTellerDetails(final ElectionDetails electionDetails,
			ElectionCache electionCache) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getTellerDetails() != null) {
			return electionCache.getTellerDetails();
		}
		BBClientUtil bb = new BBClientUtil(electionID);

		List l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.TellerDetails(), TellerDetails.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		final PublicKey supPubKey = electionDetails.supervisor;

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = (BBPost) l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}
			if (post != null && post.verify(supPubKey)) {
				try {
					TellerDetails td = (TellerDetails) post.msg;

					if (electionCache != null) {
						electionCache.setTellerDetails(td);
					}
					return td;
				} catch (ClassCastException e) {
					// ignore, and try the next one...
				}
			}
		}
		return null;

	}

	/**
	 * Computes and returns the number of voter blocks for the given election. The
	 * number of voter blocks is the estimated number of voters divided by the voter
	 * anonymity parameter. If electionDetails is null, or no electoral roll
	 * estimate has been posted, then -1 is returned. NOTE: the number of blocks in
	 * an election is the number of voter blocks times the number of votes produced
	 * per ballot.
	 */
	public static int numberVoterBlocks(final ElectionDetails electionDetails, ElectionCache electionCache)
			throws IOException {
		if (electionDetails == null)
			return -1;
		ElectoralRollEstimate ere = retrieveElectoralRollEstimate(electionDetails, electionCache);
		if (ere == null)
			return -1;
		try {
			// get the estimated number of voters
			int div = ere.estimate / electionDetails.voterAnonymityParam;
			if (div == 0)
				div++; // make sure there is at least one
						// block
			return div;
		} catch (ArithmeticException e) {
			return 1;
		}
	}

	/**
	 * Computes and returns the number of blocks for the given election. The number
	 * of blocks in an election is the number of voter blocks times the number of
	 * votes produced per ballot.
	 */
	public static int numberBlocks(final ElectionDetails electionDetails, ElectionCache electionCache)
			throws IOException {
		if (electionDetails == null || electionDetails.ballotDesign == null)
			return -1;
		return numberVoterBlocks(electionDetails, electionCache)
				* electionDetails.ballotDesign.votesProducedPerBallot();
	}

	/**
	 * Computes and returns the number of voter blocks for the given election. The
	 * number of voter blocks is the estimated number of voters divided by the voter
	 * anonymity parameter. If electionDetails is null, or no electoral roll
	 * estimate has been posted, then -1 is returned.
	 */
	public static int numberVoterBlocks(final ElectionDetails electionDetails, ElectoralRollEstimate ere) {
		if (electionDetails == null)
			return -1;
		if (ere == null)
			return -1;
		try {
			// get the estimated number of voters
			int div = ere.estimate / electionDetails.voterAnonymityParam;
			if (div == 0)
				div++; // make sure there is at least one block
			return div;
		} catch (ArithmeticException e) {
			return 1;
		}
	}

	/**
	 * Retrieves the ElectoralRollEstimate from the specified bulletin board.
	 * Returns null if no ElectoralRollEstimate. Returns the first
	 * ElectoralRollEstimate that successfully parses and validates.
	 */
	public static ElectoralRollEstimate retrieveElectoralRollEstimate(final ElectionDetails electionDetails,
			ElectionCache electionCache) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getElectoralRollEstimate() != null) {
			return electionCache.getElectoralRollEstimate();
		}

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectoralRollEstimate(), ElectoralRollEstimate.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					ElectoralRollEstimate ere = (ElectoralRollEstimate) post.msg;
					if (ere != null) {
						PublicKey regPubKey = electionDetails.registrar;
						if (post.verify(regPubKey)) {
							if (electionCache != null) {
								electionCache.setElectoralRollEstimate(ere);
							}
							return ere;
						}
					}
				} catch (ClassCastException ignore) {
				}
			}
		}
		return null;
	}

	public static int retrieveVotersBlock(ElectionCache electionCache, ElectionDetails electionDetails,
			String voterName) throws IOException {
		if (electionDetails == null || voterName == null || electionCache == null)
			return -1;

		int votersBlock = electionCache.getVotersBlock(voterName);
		if (votersBlock >= 0)
			return votersBlock;

		// not in the cache. Gp to the BB

		BBClientUtil bb = new BBClientUtil(electionDetails.electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectoralRoll(), ElectoralRoll.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			return -1;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
			} catch (ClassCastException imposs) {
			} catch (IndexOutOfBoundsException imposs) {
			}
			if (post == null)
				continue;

			// parse and validate
			try {
				ElectoralRoll er = (ElectoralRoll) post.msg;
				// check the signature of er
				if (er != null && er.roll != null && post.verify(electionDetails.registrar)) {
					// the electoral roll is ok!
					for (int j = 0; j < er.roll.length; j++) {
						VoterDetails vd = null;
						try {
							vd = er.roll[j];
						} catch (ArrayIndexOutOfBoundsException imposs) {
						}
						if (vd != null) {
							electionCache.putVotersBlock(vd.name, vd.voterBlock);
						}
					}
				}
			} catch (ClassCastException ignore) {
			}
		}
		return electionCache.getVotersBlock(voterName);
	}

	/**
	 * retrieve electoral roll shares from the bulletin board for a particular voter
	 * and particular registration teller. Ideally, the label of the ciphertexts
	 * should indicate that they were produced by the approriate registration
	 * teller; the DLM is not sufficiently expressive to capture this.
	 */
	public static ElGamalSignedCiphertext[] retrieveEncryptedCapabilitySharesForVoter(ElectionCache electionCache,
			ElectionDetails electionDetails, int tellerIndex, String voterName) throws IOException {
		if (voterName == null)
			return null;
		// first get the voter block
		int voterBlock = retrieveVotersBlock(electionCache, electionDetails, voterName);
		ElectoralRollCapabilityShares ercs = retrieveElectoralRollCapabilitySharesForVoterBlock(electionCache,
				electionDetails, retrieveTellerDetails(electionDetails, electionCache), tellerIndex, voterBlock);
		if (ercs != null && ercs.roll != null) {
			for (int i = 0; i < ercs.roll.length; i++) {
				try {
					VoterEncCapabilityShares vecs = ercs.roll[i];
					if (vecs != null && voterName.equals(vecs.name)) {
						// found it!
						return vecs.encCapabilityShares;
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
		}
		return null;
	}

	/**
	 * retrieve electoral roll shares from the bulletin board for a particular voter
	 * block and particular registration teller Ideally, the label of the return
	 * value should indicate that they were produced by the approriate registration
	 * teller; the DLM is not sufficiently expressive to capture this.
	 */
	public static ElectoralRollCapabilityShares retrieveElectoralRollCapabilitySharesForVoterBlock(
			ElectionCache electionCache, ElectionDetails electionDetails, TellerDetails tellerDetails, int tellerIndex,
			int voterBlock) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null) {
			ElectoralRollCapabilityShares erc = electionCache.getElectoralRollCapabilitySharesForVoterBlock(voterBlock,
					tellerIndex);
			if (erc != null)
				return erc;
		}

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectoralRollCapabilityShares(),
					ElectoralRollCapabilityShares.meta(tellerIndex, voterBlock), null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}
		PublicKey registrationTellerPubKey = regTellerPublicKey(tellerDetails, tellerIndex);
		ElectoralRollCapabilityShares ret = null;
		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
				if (post != null && !post.verify(registrationTellerPubKey)) {
					// the message isn't signed by the registration teller
					post = null;
				}
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					ElectoralRollCapabilityShares er = (ElectoralRollCapabilityShares) post.msg;
					if (ret == null) {
						ret = er;
					} else if (er != null) {
						// there are more than one valid ElectoralRollCapabilities posting.
						// combine them.
						int retSize = (ret.roll == null ? 0 : ret.roll.length);
						int size = retSize + (er.roll == null ? 0 : er.roll.length);
						VoterEncCapabilityShares[] vd = new VoterEncCapabilityShares[size < 0 ? 0 : size];
						try {
							for (int j = 0; j < retSize; j++) {
								vd[j] = ret.roll[j];
							}
							for (int j = 0; j < er.roll.length; j++) {
								vd[retSize + j] = er.roll[j];
							}
						} catch (NullPointerException imposs) {
						} catch (ArrayIndexOutOfBoundsException imposs) {
						}
						ret = new ElectoralRollCapabilityShares(vd, tellerIndex, voterBlock);
					}
				} catch (ClassCastException ignore) {
				}
			}
		}
		if (electionCache != null && ret != null) {
			electionCache.setElectoralRollCapabilitySharesForVoterBlock(voterBlock, tellerIndex, ret);
		}
		return ret;
	}

	/**
	 * Retrieve the posted capability shares for the voter block from each
	 * registration teller, and combine them into real capabilities
	 */
	private static ElectoralRollCapabilities retrieveElectoralRollCapabilitiesForVoterBlock(ElectionCache electionCache,
			ElectionDetails electionDetails, TellerDetails tellerDetails, int voterBlock)
			throws IOException, CryptoException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;
		if (electionCache != null) {
			ElectoralRollCapabilities erc = electionCache.getElectoralRollCapabilitiesForVoterBlock(voterBlock);
			if (erc != null)
				return erc;
		}
		ElGamalParameters params = electionDetails.elGamalParameters;
		// go through each registration teller, and get the shares for that teller.
		int numRegTellers = 0;
		try {
			numRegTellers = tellerDetails.registrationTellers.length;
		} catch (NullPointerException ignore) {
		}

		if (numRegTellers <= 0)
			return null;

		ElectoralRollCapabilityShares[] tellerShares = new ElectoralRollCapabilityShares[numRegTellers];

		for (int i = 0; i < numRegTellers; i++) {
			int tellerIndex = i + 1;
			try {
				tellerShares[i] = retrieveElectoralRollCapabilitySharesForVoterBlock(electionCache, electionDetails,
						tellerDetails, tellerIndex, voterBlock);
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		// now go through the results, and combine the shares.
		ElectoralRollCapabilityShares teller0 = null;
		try {
			teller0 = tellerShares[0];
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		if (teller0 == null || teller0.roll == null)
			return null;

		VoterEncCapabilities[] roll = new VoterEncCapabilities[teller0.roll.length];
		Set processedVoters = new HashSet();
		int count = 0;
		for (int i = 0; i < teller0.roll.length; i++) {
			VoterEncCapabilityShares voter = null;
			try {
				voter = teller0.roll[i];
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
			if (voter == null || voter.voterBlock != voterBlock)
				continue;

			// have we processed voter already?
			if (processedVoters.contains(voter.name))
				continue;

			ElGamalSignedCiphertext[][] voterShares = new ElGamalSignedCiphertext[numRegTellers][];
			try {
				voterShares[0] = voter.encCapabilityShares;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}

			boolean foundAll = true;
			for (int j = 1; j < numRegTellers; j++) {
				try {
					ElectoralRollCapabilityShares tellerj = tellerShares[j];
					// find the voter in tellerj.
					for (int k = 0; k < tellerj.roll.length; k++) {
						VoterEncCapabilityShares v = tellerj.roll[k];
						if (v != null && v.name.equals(voter.name) && v.voterBlock == voterBlock) {
							// found the voter!
							voterShares[j] = v.encCapabilityShares;
							break;
						}
					}
					if (voterShares[j] == null) {
						// couldn't find shares posted by teller j.
						foundAll = false;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException imposs) {
				} catch (NullPointerException e) {
					foundAll = false;
				}
			}
			if (foundAll) {
				try {
					// Check the voter shares are correctly signed!
					for (int j = 0; j < voterShares.length; j++) {
						for (int k = 0; k < voterShares[j].length; k++) {
							if (!CryptoUtil.factory().elGamalVerify(params, voterShares[j][k],
									CryptoUtil.factory().messageDigest((j + 1) + voter.name))) {
								// uh oh, one of the registration tellers cheated!
								throw new CryptoException("Capability share " + (k + 1) + " of " + voterShares[j].length
										+ " posted by teller " + (j + 1) + " for voter " + voter.name + " is invalid");
							}
						}
					}

					ElGamalCiphertext[] encCapabilities = CryptoUtil.factory().multiplyCiphertexts(voterShares, params);
					roll[count++] = new VoterEncCapabilities(voter.name, voterBlock, encCapabilities);
				} catch (ArrayIndexOutOfBoundsException imposs) {
				} catch (NullPointerException imposs) {
				}
			}

			// add voter to the set of processed voters
			try {
				processedVoters.add(voter.name);
			} catch (IllegalArgumentException imposs) {
			} catch (ClassCastException imposs) {
			}
		}

		// shrink the arary if needed?
		if (count != roll.length) {
			VoterEncCapabilities[] n = new VoterEncCapabilities[count];
			for (int i = 0; i < count; i++) {
				try {
					n[i] = roll[i];
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
			roll = n;
		}

		ElectoralRollCapabilities voterBlockCaps = new ElectoralRollCapabilities(roll);
		if (electionCache != null) {
			electionCache.setElectoralRollCapabilitiesForVoterBlock(voterBlock, voterBlockCaps);
		}
		return voterBlockCaps;

	}

	/**
	 * Retrieves the electoral roll from the specified bulletin board, restricting
	 * to the subset indicated by the block. Returns null if no electoral roll.
	 */
	public static ElectoralRollCapabilities retrieveElectoralRollCapabilities(ElectionCache electionCache,
			ElectionDetails electionDetails, TellerDetails tellerDetails, int block)
			throws IOException, CryptoException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;
		if (electionCache != null) {
			ElectoralRollCapabilities erc = electionCache.getElectoralRollCapabilities(block);
			if (erc != null)
				return erc;
		}

		// get the capabilities for the voter block
		int voterBlock = electionDetails.voterBlockForBlock(block);
		ElectoralRollCapabilities voterBlockCaps = retrieveElectoralRollCapabilitiesForVoterBlock(electionCache,
				electionDetails, tellerDetails, voterBlock);

		if (voterBlockCaps == null || voterBlockCaps.roll == null)
			return null;

		VoterEncCapabilities[] vcs = new VoterEncCapabilities[100];
		int vcsCount = 0;

		// go through the VogerCapabilities, and add
		// only those for the block
		try {
			for (int j = 0; j < voterBlockCaps.roll.length; j++) {
				VoterEncCapabilities vc = voterBlockCaps.roll[j];
				if (vc != null && electionDetails.inBlock(vc, block)) {
					// put vc into vcs.
					if (vcsCount == vcs.length) {
						// extend the array
						VoterEncCapabilities[] newVcs = new VoterEncCapabilities[vcsCount * 2];
						for (int k = 0; k < vcsCount; k++) {
							newVcs[k] = vcs[k];
						}
						vcs = newVcs;
					}
					vcs[vcsCount++] = vc;
				}
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}

		if (vcsCount != vcs.length) {
			// shrink the array
			VoterEncCapabilities[] newVcs = new VoterEncCapabilities[vcsCount];
			for (int k = 0; k < vcsCount; k++) {
				try {
					newVcs[k] = vcs[k];
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}
			}
			vcs = newVcs;
		}

		ElectoralRollCapabilities blockCaps = new ElectoralRollCapabilities(vcs);
		if (electionCache != null) {
			electionCache.setElectoralRollCapabilities(block, blockCaps);
		}

		return blockCaps;
	}

	/**
	 * Retrieves the tabulation tellers' public key. Returns null if no key has been
	 * posted yet.
	 */
	public static ElGamalPublicKey retrieveTabTellerSharedPublicKey(final ElectionDetails electionDetails,
			ElectionCache electionCache) throws IOException {
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getTabTellerSharedKey() != null) {
			return electionCache.getTabTellerSharedKey();
		}

		ElectionCache ec = electionCache;
		if (ec == null)
			ec = new ElectionCache();

		TellerDetails tellerDetails = retrieveTellerDetails(electionDetails, ec);

		int numTabTellers = 0;
		try {
			numTabTellers = tellerDetails.tabulationTellers.length;
		} catch (NullPointerException ignore) {
		}

		ElGamalPublicKey tabTellerSharedKey = null;

		ElGamalKeyShare[] shares = new ElGamalKeyShare[numTabTellers];
		// see if we have all the key shares
		boolean allPresent = true;
		for (int i = 0; i < shares.length; i++) {
			try {
				shares[i] = retrieveTabTellerSharedPublicKey(electionID, ec, i + 1);
				if (shares[i] == null) {
					// nope. we can't get it.
					allPresent = false;
					break;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}

		if (allPresent) {
			try {
				tabTellerSharedKey = CryptoUtil.factory().combineKeyShares(shares);
			} catch (NullPointerException imposs) {
			} catch (CryptoException e) {
				throw new IOException("Unable to create tabulation teller shared key: " + e.getMessage());
			}
			ec.setTabTellerSharedKey(tabTellerSharedKey);
		}

		return tabTellerSharedKey;
	}

	/**
	 * Retrieves a single tabulation tellers' public key. Returns null if no key has
	 * been posted yet.
	 */
	public static ElGamalKeyShare retrieveTabTellerSharedPublicKey(ElectionID electionID, ElectionCache electionCache,
			int tellerIndex) throws IOException {
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getTabTellerKeyShare(tellerIndex) != null) {
			return electionCache.getTabTellerKeyShare(tellerIndex);
		}

		// retrieve the key share
		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.TabTellerKeyShare(), TabTellerKeyShare.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		ElectionDetails electionDetails = retrieveElectionDetails(electionID, electionCache);
		TellerDetails tellerDetails = retrieveTellerDetails(electionDetails, electionCache);
		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					TabTellerKeyShare d = (TabTellerKeyShare) post.msg;
					if (d != null && d.keyShare != null) {
						TabTellerKeyShareCommitment com = retrieveTabTellerSharedPublicKeyCommitment(electionID,
								electionCache, d.tellerIndex);
						TabTellerKeyShareCommitment com2 = d.constructCommitment();
						if (electionCache != null) {
							// process all keys so we can cache them
							if (post.verify(tabTellerPublicKey(tellerDetails, d.tellerIndex)) && d.keyShare.verify()) {
								// it's a valid key share
								// check that it matches the commitment
								if (com != null && com2 != null && com.keyShareHash != null
										&& com.keyShareHash.equals(com2.keyShareHash)) {
									electionCache.setTabTellerKeyShare(d.tellerIndex, d.keyShare);
								}
							}
						} else {
							// don't need to worry about caching
							if (d.tellerIndex == tellerIndex) {
								// check the signature too
								if (post.verify(tabTellerPublicKey(tellerDetails, d.tellerIndex))
										&& d.keyShare.verify()) {
									if (com != null && com2 != null && com.keyShareHash != null
											&& com.keyShareHash.equals(com2.keyShareHash)) {
										return d.keyShare;
									}
								}
							}
						}
					}
				} catch (ClassCastException ignore) {
				} catch (NullPointerException imposs) {
				}
			}
		}
		if (electionCache != null && electionCache.getTabTellerKeyShare(tellerIndex) != null) {
			return electionCache.getTabTellerKeyShare(tellerIndex);
		}

		return null;
	}

	/**
	 * Retrieves a single tabulation tellers' public key. Returns null if no key has
	 * been posted yet.
	 */
	public static TabTellerKeyShareCommitment retrieveTabTellerSharedPublicKeyCommitment(ElectionID electionID,
			ElectionCache electionCache, int tellerIndex) throws IOException {
		if (electionID == null)
			return null;

		if (electionCache != null && electionCache.getTabTellerKeyShareCommitment(tellerIndex) != null) {
			return electionCache.getTabTellerKeyShareCommitment(tellerIndex);
		}

		// retrieve the key share commitment
		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.TabTellerKeyShareCommitment(), TabTellerKeyShareCommitment.META,
					null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		ElectionDetails electionDetails = retrieveElectionDetails(electionID, electionCache);
		TellerDetails tellerDetails = retrieveTellerDetails(electionDetails, electionCache);
		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					TabTellerKeyShareCommitment c = (TabTellerKeyShareCommitment) post.msg;
					if (c != null) {
						if (electionCache != null) {
							// process all commitments so we can cache them
							if (post.verify(tabTellerPublicKey(tellerDetails, c.tellerIndex))) {
								// it's a valid commitment
								electionCache.setTabTellerKeyShareCommitment(c.tellerIndex, c);
							}
						} else {
							// don't need to worry about caching
							if (c.tellerIndex == tellerIndex) {
								// check the signature too
								if (post.verify(tabTellerPublicKey(tellerDetails, c.tellerIndex))) {
									return c;
								}
							}
						}
					}
				} catch (ClassCastException ignore) {
				} catch (NullPointerException imposs) {
				}
			}
		}
		if (electionCache != null && electionCache.getTabTellerKeyShareCommitment(tellerIndex) != null) {
			return electionCache.getTabTellerKeyShareCommitment(tellerIndex);
		}

		return null;
	}

	/**
	 * Retrieves the parameters for the tabulation teller public key.
	 */
	public static ElGamalParameters retrieveTabTellKeyParams(ElectionID electionID, ElectionCache electionCache)
			throws IOException {
		final ElectionDetails details = retrieveElectionDetails(electionID, electionCache);

		if (details != null) {
			return details.elGamalParameters;
		}

		throw new IOException("No election details.");
	}

	public static ElectionAbandonment retrieveAbandonmentReason(ElectionID electionID, ElectionCache electionCache)
			throws IOException {
		if (electionID == null)
			return null;

		ElectionDetails electionDetails = retrieveElectionDetails(electionID, electionCache);
		TellerDetails tellerDetails = retrieveTellerDetails(electionDetails, electionCache);

		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(XMLDeserializers.ElectionAbandonment(), ElectionAbandonment.META, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);

			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					ElectionAbandonment ea = (ElectionAbandonment) post.msg;
					// check that signature matches.
					if (ea != null) {
						PublicKey pk;
						if (ea.isTabulationTeller) {
							pk = tabTellerPublicKey(tellerDetails, ea.tellerIndex);
						} else {
							// the election abandonment reason is not posted by a tabulation teller.
							// Here we are out of the realms of the Civitas voting scheme, and
							// would need procedures for dealing with an abandonment from
							// an entity other than a tabulation teller.
							// For the moment, we do not accept abandonments by these entities,
							// and post.verify(pk) will return false.
							pk = null;
						}
						if (post.verify(pk)) {
							return ea;
						}
					}
				} catch (ClassCastException ignore) {
				}
			}
		}
		return null;
	}

	/**
	 * Retrieves the vote mix or capability mix n. Returns null if no such mix
	 * exists. Returns the first details that successfully parses n is greater than
	 * zero.
	 */
	public static Mix retrieveMix(ElectionCache electionCache, ElectionDetails electionDetails,
			TellerDetails tellerDetails, boolean isVoteMix, int block, int n, boolean rightMix) throws IOException

	{
		if (electionDetails == null)
			return null;
		ElectionID electionID = electionDetails.electionID;
		if (electionID == null)
			return null;

		if (electionCache != null) {
			Mix m = electionCache.getMix(block, n, rightMix, isVoteMix);
			if (m != null)
				return m;
		}

		String meta = isVoteMix ? VoteMix.meta(electionDetails, block, n, rightMix)
				: CapabilityMix.meta(electionDetails, block, n, rightMix);
		XMLDeserializer deserializer = isVoteMix ? XMLDeserializers.VoteMix() : XMLDeserializers.CapabilityMix();
		BBClientUtil bb = new BBClientUtil(electionID);

		List<BBPost> l = null;
		try {
			l = bb.retrieveParams(deserializer, meta, null, null);
		} catch (IllegalArgumentException e) {
			throw new IOException(e.getMessage());
		}
		if (l == null || l.isEmpty()) {
			// no results!
			return null;
		}

		for (int i = 0; i < l.size(); i++) {
			BBPost post = null;
			try {
				post = l.get(i);
				// check the signature on the message.
				if (post != null && !post.verify(tabTellerPublicKey(tellerDetails, n))) {
					post = null;
				}

			} catch (IndexOutOfBoundsException imposs) {
			} catch (ClassCastException ignore) {
			}

			if (post != null) {
				try {
					Mix ret = (Mix) post.msg;
					return ret;

				} catch (ClassCastException ignore) {
				}
			}
		}
		return null;
	}
}