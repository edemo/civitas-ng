package civitas.crypto.votecapabilityshare;

import java.util.List;
import java.util.Map;

import civitas.common.ballotdesign.BallotDesignTestData;
import civitas.crypto.msg.ElgamalMessageTestData;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigInteger;

public interface VoteCapabilityShareTestData
		extends ElgamalMessageTestData, BallotDesignTestData {

	VoteCapability VOTE_CAPABILITY = new VoteCapability(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);
	VoteCapabilityShare VOTE_CAPABILITY_SHARE = new VoteCapabilityShare(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	public static final String VOTE_CAPABILITY_SHARE_XML = "<voteCapabilityShare>"
			+ MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED_BASE64 + "</voteCapabilityShare>";
	public static final String VOTE_CAPABILITY_SHARE_NULL_XML = "<voteCapabilityShare></voteCapabilityShare>";

	public static final String VOTE_CAPABILITY_XML = "<voteCapability>"
			+ MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED_BASE64 + "</voteCapability>";
	public static final String VOTE_CAPABILITY_NULL_XML = "<voteCapability>"
			+ "</voteCapability>";

	VoteCapabilityShare[][] CAPABILITY_SHARE_MATRIX = new VoteCapabilityShare[][] {
			{
					new VoteCapabilityShare(CivitasBigInteger.valueOf(2)),
					new VoteCapabilityShare(CivitasBigInteger.valueOf(3)) },
			{
					new VoteCapabilityShare(CivitasBigInteger.valueOf(5)),
					new VoteCapabilityShare(CivitasBigInteger.valueOf(7)) } };

	List<VoteCapability> VOTE_CAPABILITIES = RANDOMS.subList(0, 3).stream()
			.map(x -> new VoteCapability(BIGINT_G.modPow(x, BIGINT_P))).toList();

	Map<String, VoteCapability> CAPABILITY_MAP = Map.of(CONTEXT_0,
			VOTE_CAPABILITIES.get(0), CONTEXT_1, VOTE_CAPABILITIES.get(1), CONTEXT_2,
			VOTE_CAPABILITIES.get(2));

	VoteCapabilityShare[] VOTE_CAPABILITY_SHARES = VOTE_CAPABILITIES.stream()
			.map(x -> new VoteCapabilityShare(x.m)).toList()
			.toArray(new VoteCapabilityShare[0]);

}
