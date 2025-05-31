package civitas.crypto.votecapabilityshare;

import civitas.crypto.msg.ElgamalMessageTestData;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigInteger;

public interface VoteCapabilityShareTestData extends ElgamalMessageTestData {

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

}
