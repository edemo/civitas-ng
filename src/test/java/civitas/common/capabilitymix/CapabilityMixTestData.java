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

	CapabilityMix CAPABILITY_MIX_REMIXED = new CapabilityMix(VOTER_BLOCK,
			new byte[0], new byte[][] {},
			new ElGamalCiphertext[] {
					REENCRYPTED_VOTE_CAPABILITIES.get(1),
					REENCRYPTED_VOTE_CAPABILITIES.get(0) });

	byte[] NONCE = BYTES;
	int MAPPING = 1;
	MixCapabilityElementRevelation RELEVATION = new MixCapabilityElementRevelation(
			MAPPING, NONCE, ELGAMAL_REENCRYPT_FACTOR_EPRIME);

	String MIX_CONFIRM_ROLL_META = "mixConfirm:elecRoll:1:2";
	String MIX_CONFIRM_VOTE_META = "mixConfirm:vote:1:2";
	String MIX_HASH_REVELATION_VOTE = "mixHashRevelation:vote:" + BLOCKNAME_14
			+ ":2";
	String MIX_HASH_REVELATION_ROLL = "mixHashRevelation:elecRoll:" + BLOCKNAME_14
			+ ":2";

}
