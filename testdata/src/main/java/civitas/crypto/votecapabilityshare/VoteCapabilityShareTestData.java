package civitas.crypto.votecapabilityshare;

import civitas.util.CivitasBigInteger;

public interface VoteCapabilityShareTestData extends VoteCapabilityTestData {

	VoteCapabilityShare VOTE_CAPABILITY_SHARE = new VoteCapabilityShare(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	VoteCapabilityShare[][] CAPABILITY_SHARE_MATRIX = new VoteCapabilityShare[][] {
			{
					new VoteCapabilityShare(CivitasBigInteger.valueOf(2)),
					new VoteCapabilityShare(CivitasBigInteger.valueOf(3)) },
			{
					new VoteCapabilityShare(CivitasBigInteger.valueOf(5)),
					new VoteCapabilityShare(CivitasBigInteger.valueOf(7)) } };

	VoteCapabilityShare[] VOTE_CAPABILITY_SHARES = VOTE_CAPABILITIES.stream()
			.map(x -> new VoteCapabilityShare(x.m)).toList()
			.toArray(new VoteCapabilityShare[0]);

}
