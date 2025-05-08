/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.ElGamalPublicKey;

/**
 * A class for caching information about an election. An ElectionCache object
 * can optionally be given to methods that fetch election information, and the
 * cache will be checked for the info first.
 */
public class ElectionCache {
	private ElectionDetails electionDetails;
	private TellerDetails tellerDetails;
	private Long electionStartTime;
	private Long electionStopTime;
	private CiphertextList ciphertextList;
	private ElectionEvent[] electionEvents;
	private ElectoralRollEstimate electoralRollEstimate;
	private ElGamalKeyShare[] tabTellerKeyShares;
	private TabTellerKeyShareCommitment[] tabTellerKeyShareCommitments;
	private ElGamalPublicKey tabTellerSharedKey;
	private BoardsForTabulation boardsForTabulation;
	private final Map<String, String> voterBlocks;

	public ElectionCache() {
		super();
		this.voterBlocks = new HashMap<String, String>();
	}

	/*
	 * Factory methods
	 */
	public DistDecryptCache newDistDecryptCache(String meta, int numberBlocks, int numTabTellers) {
		return null;
	}

	public PETCache newPETCache(String meta, int numberBlocks, int numTabTellers) {
		return null;
	}

	/*
	 * Caching methods
	 */
	public void setElectionDetails(final ElectionDetails electionDetails) {
		this.electionDetails = electionDetails;
	}

	public ElectionDetails getElectionDetails() {
		return this.electionDetails;
	}

	public void setTellerDetails(TellerDetails tellerDetails) {
		this.tellerDetails = tellerDetails;
	}

	public TellerDetails getTellerDetails() {
		return this.tellerDetails;
	}

	public void setElectionStartTime(Long time) {
		this.electionStartTime = time;
	}

	public Long getElectionStartTime() {
		return this.electionStartTime;
	}

	public void setElectionStopTime(Long time) {
		this.electionStopTime = time;
	}

	public Long getElectionStopTime() {
		return this.electionStopTime;
	}

	public void setCiphertextList(CiphertextList ciphertextList) {
		this.ciphertextList = ciphertextList;
	}

	public CiphertextList getCiphertextList() {
		return this.ciphertextList;
	}

	public void setElectionEvents(ElectionEvent[] electionEvents) {
		this.electionEvents = electionEvents;
	}

	public ElectionEvent[] getElectionEvents() {
		return this.electionEvents;
	}

	public void setElectoralRollEstimate(ElectoralRollEstimate electoralRollEstimate) {
		this.electoralRollEstimate = electoralRollEstimate;
	}

	public ElectoralRollEstimate getElectoralRollEstimate() {
		return this.electoralRollEstimate;
	}

	public void setTabTellerKeyShare(int tellerIndex, ElGamalKeyShare tabTellerKeyShares) {
		ElGamalKeyShare[] shares = this.tabTellerKeyShares;
		if (shares == null) {
			try {
				shares = new ElGamalKeyShare[tellerIndex + 10];
			} catch (NegativeArraySizeException imposs) {
				shares = new ElGamalKeyShare[10];
			}

			this.tabTellerKeyShares = shares;
		}
		if (tellerIndex - 1 >= shares.length) {
			// need to increase the array
			try {
				shares = new ElGamalKeyShare[tellerIndex + 10];
				for (int i = 0; i < this.tabTellerKeyShares.length; i++) {
					shares[i] = this.tabTellerKeyShares[i];
				}
			} catch (NegativeArraySizeException imposs) {
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
			this.tabTellerKeyShares = shares;
		}

		try {
			shares[tellerIndex - 1] = tabTellerKeyShares;
		} catch (ArrayIndexOutOfBoundsException unlikely) {
		}
	}

	public ElGamalKeyShare getTabTellerKeyShare(int tellerIndex) {
		ElGamalKeyShare[] shares = this.tabTellerKeyShares;
		if (shares != null && tellerIndex - 1 < shares.length) {
			try {
				return shares[tellerIndex - 1];
			} catch (ArrayIndexOutOfBoundsException unlikely) {
			}
		}
		return null;
	}

	public void setTabTellerKeyShareCommitment(int tellerIndex, TabTellerKeyShareCommitment c) {
		TabTellerKeyShareCommitment[] coms = this.tabTellerKeyShareCommitments;
		if (coms == null) {
			try {
				coms = new TabTellerKeyShareCommitment[tellerIndex + 10];
			} catch (NegativeArraySizeException imposs) {
				coms = new TabTellerKeyShareCommitment[10];
			}
			this.tabTellerKeyShareCommitments = coms;
		}
		if (tellerIndex - 1 >= coms.length) {
			// need to increase the array
			try {
				coms = new TabTellerKeyShareCommitment[tellerIndex + 10];
				for (int i = 0; i < this.tabTellerKeyShareCommitments.length; i++) {
					coms[i] = this.tabTellerKeyShareCommitments[i];
				}
			} catch (NegativeArraySizeException imposs) {
			} catch (NullPointerException imposs) {
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
			this.tabTellerKeyShareCommitments = coms;
		}

		try {
			coms[tellerIndex - 1] = c;
		} catch (ArrayIndexOutOfBoundsException unlikely) {
		}
	}

	public TabTellerKeyShareCommitment getTabTellerKeyShareCommitment(int tellerIndex) {
		TabTellerKeyShareCommitment[] coms = this.tabTellerKeyShareCommitments;
		if (coms != null && tellerIndex - 1 < coms.length) {
			try {
				return coms[tellerIndex - 1];
			} catch (ArrayIndexOutOfBoundsException unlikely) {
			}
		}
		return null;
	}

	public void setTabTellerSharedKey(ElGamalPublicKey tabTellerSharedKey) {
		this.tabTellerSharedKey = tabTellerSharedKey;
	}

	public ElGamalPublicKey getTabTellerSharedKey() {
		return this.tabTellerSharedKey;
	}

	public void setBoardsForTabulation(BoardsForTabulation boardsForTabulation) {
		this.boardsForTabulation = boardsForTabulation;
	}

	public BoardsForTabulation getBoardsForTabulation() {
		return this.boardsForTabulation;
	}

	/*
	 * Some things that this implementation doesn't cache.
	 */

	public int getInitialVoteMixSize(int block) {
		return -1;
	}

	public int getInitialCapabilityMixSize(int block) {
		return -1;
	}

	public void setElectoralRollCapabilitiesForVoterBlock(int voterBlock, ElectoralRollCapabilities erc) {

	}

	public ElectoralRollCapabilities getElectoralRollCapabilitiesForVoterBlock(int voterBlock) {
		return null;
	}

	public void setElectoralRollCapabilitySharesForVoterBlock(int voterBlock, int tellerIndex,
			ElectoralRollCapabilityShares erc) {

	}

	public ElectoralRollCapabilityShares getElectoralRollCapabilitySharesForVoterBlock(int voterBlock,
			int tellerIndex) {
		return null;
	}

	public boolean hasElectoralRollCapabilitySharesForVoterBlock(int voterBlock, int tellerIndex) {
		return false;
	}

	public void setElectoralRollCapabilities(int block, ElectoralRollCapabilities erc) {

	}

	public ElectoralRollCapabilities getElectoralRollCapabilities(int block) {
		return null;
	}

	public void setValidVerifiableVotesForBlock(int block, VerifiableVote[] vv) {

	}

	public VerifiableVote[] getValidVerifiableVotesForBlock(int block) {
		return null;
	}

	public int getValidVerifiableVoteSizeForBlock(int block) {
		return -1;
	}

	public void setVoterSubmissionsForVoterBlock(int voterBlock, List voterSubmissions) {

	}

	public List getVoterSubmissionsForVoterBlock(int voterBlock) {
		return null;
	}

	public void setTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block, MixHashRevelation mhr) {

	}

	public boolean hasTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block) {
		return false;
	}

	public MixHashRevelation getTTMixHashRevelation(int tellerIndex, boolean isVoteMix, int block) {
		return null;
	}

	public void setInitialCapabilityMix(int block, CapabilityMix mix) {

	}

	public CapabilityMix getInitialCapabilityMix(int block) {
		return null;
	}

	public void setInitialVoteMix(int block, VoteMix mix) {

	}

	public VoteMix getInitialVoteMix(int block) {
		return null;
	}

	public void setMix(int block, int n, boolean isRightMix, boolean isVoteMix, Mix mix) {

	}

	public Mix getMix(int block, int n, boolean isRightMix, boolean isVoteMix) {
		return null;
	}

	public void setTabTellerVoterBlockContents(int voterBlock, int tellerIndex, TabTellerVoterBlockContents c) {

	}

	public TabTellerVoterBlockContents getTabTellerVoterBlockContents(int voterBlock, int tellerIndex) {
		return null;
	}

	public void putVotersBlock(String voterName, int voterBlock) {
		if (voterBlocks != null) {
			voterBlocks.put(voterName, "" + voterBlock);
		}
	}

	public int getVotersBlock(String voterName) {
		if (voterBlocks != null) {
			try {
				String s = voterBlocks.get(voterName);
				if (s != null) {
					return Integer.parseInt(s);
				}
			} catch (ClassCastException imposs) {
			} catch (NumberFormatException imposs) {
			}
		}
		return -1;
	}

}