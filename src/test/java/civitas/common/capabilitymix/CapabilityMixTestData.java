package civitas.common.capabilitymix;

import java.util.function.Supplier;

import civitas.common.ballot.BallotTestData;
import civitas.common.election.ElectionTestData;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.proofvote.ProofVoteTestData;

public interface CapabilityMixTestData
		extends BallotTestData, ProofVoteTestData, ElectionTestData {

	Supplier<CapabilityMix> CAPABILITY_MIX_EMPTY_SUPPLIER = new Supplier<CapabilityMix>() {

		@Override
		public CapabilityMix get() {
			return new CapabilityMix(VOTER_BLOCK, new byte[0], new byte[][] {},
					new ElGamalCiphertext[0]);
		}
	};

	CapabilityMix CAPABILITY_MIX_COMMITMENT_ADDED = new CapabilityMix(VOTER_BLOCK,
			new byte[0], new byte[][] { SOMESTRING.getBytes() },
			new ElGamalCiphertext[0]);

	CapabilityMix CAPABILITY_MIX_CAPABILITY_ADDED = new CapabilityMix(VOTER_BLOCK,
			new byte[0], new byte[][] {},
			new ElGamalCiphertext[] { ENCRYPTED_VOTE_CAPABILITIES.get(0) });

}
