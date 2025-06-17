package civitas.common.mix.capabilitymix;

import static org.mockito.Mockito.mock;

import java.util.Base64;
import java.util.function.Supplier;

import civitas.common.ballot.BallotTestData;
import civitas.common.election.ElectionTestData;
import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.proofvote.ProofVoteTestData;

public interface CapabilityMixTestData
		extends BallotTestData, ProofVoteTestData, ElectionTestData,
		MixCapabilityElementRevelationTestData {

	CapabilityMix CAPABILITY_MIX_MOCK = mock(CapabilityMix.class);

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
			new ElGamalCiphertextish[] { ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(0) });

	CapabilityMix CAPABILITY_MIX_REMIXED = new CapabilityMix(VOTER_BLOCK,
			new byte[0], new byte[][] {},
			new ElGamalCiphertextish[] {
					REENCRYPTED_VOTE_CAPABILITIES.get(1),
					REENCRYPTED_VOTE_CAPABILITIES.get(0) });

	CapabilityMix CAPABILITY_MIX_INITIAL = new CapabilityMix(4, new byte[0],
			new byte[][] {},
			new ElGamalCiphertextish[] { ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(1) });
	CapabilityMix CAPABILITY_MIX_LEFT = new CapabilityMix(2, new byte[0],
			new byte[][] {
					null,
					Base64.getDecoder()
							.decode("nbuVKzKGSCyUQ3uS29JzAG22s3ngrPqeLNr/jMcJRDU=") },
			new ElGamalCiphertextish[] {
					ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(0),
					REENCRYPTED_VOTE_CAPABILITIES.get(1) });

	CapabilityMix CAPABILITY_MIX_RIGHT = new CapabilityMix(3, new byte[0],
			new byte[][] {
					new byte[0],
					Base64.getDecoder()
							.decode("bRJm0nLPKMbXxhCoKbymzOUfYlK5T5ffjUNjhvm40C0=") },
			new ElGamalCiphertextish[] {
					null,
					REENCRYPTED_VOTE_CAPABILITIES.get(0) });

}
