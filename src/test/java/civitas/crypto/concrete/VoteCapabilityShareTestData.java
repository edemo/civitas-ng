package civitas.crypto.concrete;

public interface VoteCapabilityShareTestData extends ElgamalMessageTestData {

	VoteCapabilityC VOTE_CAPABILITY = new VoteCapabilityC(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);
	VoteCapabilityShareC VOTE_CAPABILITY_SHARE = new VoteCapabilityShareC(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	public static final String VOTE_CAPABILITY_SHARE_XML = "<voteCapabilityShare>"
			+ MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED_BASE64 + "</voteCapabilityShare>";
	public static final String VOTE_CAPABILITY_SHARE_NULL_XML = "<voteCapabilityShare></voteCapabilityShare>";

	public static final String VOTE_CAPABILITY_XML = "<voteCapability>"
			+ MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED_BASE64 + "</voteCapability>";
	public static final String VOTE_CAPABILITY_NULL_XML = "<voteCapability>"
			+ "</voteCapability>";

}
