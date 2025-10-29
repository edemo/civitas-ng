package civitas.crypto.votecapabilityshare.tests;

import civitas.crypto.votecapability.VoteCapability;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import civitas.util.CivitasBigIntegerFactory;

public interface VoteCapabilityShareTestData extends VoteCapabilityTestData {

	VoteCapability[] EMPTY_ARRAY = new VoteCapability[0];

	VoteCapabilityShare VOTE_CAPABILITY_SHARE = new VoteCapabilityShare(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	VoteCapabilityShare[][] CAPABILITY_SHARE_MATRIX = {
		{
			new VoteCapabilityShare(CivitasBigIntegerFactory.obtain(2)),
			new VoteCapabilityShare(CivitasBigIntegerFactory.obtain(3))
		},
		{
			new VoteCapabilityShare(CivitasBigIntegerFactory.obtain(5)),
			new VoteCapabilityShare(CivitasBigIntegerFactory.obtain(7))
		}
	};

	VoteCapabilityShare[] VOTE_CAPABILITY_SHARES = VOTE_CAPABILITIES.stream()
			.map(x -> new VoteCapabilityShare(x.m()))
			.toList()
			.toArray(new VoteCapabilityShare[0]);
}
