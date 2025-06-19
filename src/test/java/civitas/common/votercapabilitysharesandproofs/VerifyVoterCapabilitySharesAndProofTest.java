package civitas.common.votercapabilitysharesandproofs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.common.electionresults.TellerTestData;
import civitas.crypto.proofdvr.ElGamalProofDVRTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;

class VerifyVoterCapabilitySharesAndProofTest extends TestBase
		implements ElGamalProofDVRTestData, VoterCapabilitySharesAndProofTestData,
		TellerTestData {

	@InjectMocks
	VerifyVoterCapabilitySharesAndProof VerifyVoterCapabilitySharesAndProof;

	@Test
	@DisplayName("verifies the voter capabilities and proofs\n"
			+ "- check that p.e equals the posted capability\n"
			+ "- check that the posted capability verifies\n"
			+ "- check that p.e' equals enc(vc, ttKey r)"
			+ "- check the proof, i.e., that p.e is a reencryption of p.e'")
	void test() {
		assertTrue(
				VerifyVoterCapabilitySharesAndProof.apply(VOTER_CAPABILITIES_AND_PROOFS,
						POSTED_CAPABILITIES, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_E,
						VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if the proof does not verify, the check fails")
	void test1() {
		assertFalse(
				VerifyVoterCapabilitySharesAndProof.apply(VOTER_CAPABILITIES_AND_PROOFS,
						POSTED_CAPABILITIES, EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if p.e' != enc(vc, ttKey r), the check fails")
	void test2() {
		assertFalse(
				VerifyVoterCapabilitySharesAndProof.apply(VOTER_CAPABILITIES_AND_PROOFS,
						POSTED_CAPABILITIES, EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_EPRIME, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if the posted capability does not verify, the check fails")
	void test3() {
		assertFalse(VerifyVoterCapabilitySharesAndProof.apply(
				VOTER_CAPABILITIES_AND_PROOFS_CAP_NONVERIFY,
				POSTED_CAPABILITIES_NONVERIFY, EL_GAMAL_PUBLIC_KEY_E,
				EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if p.e != the posted capability, the check fails")
	void test4() {
		assertFalse(
				VerifyVoterCapabilitySharesAndProof.apply(VOTER_CAPABILITIES_AND_PROOFS,
						POSTED_CAPABILITIES_NONVERIFY, EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if tabTellerSharedPublicKey is null, NullPointerException is throwns")
	void test5() {
		assertThrows(NullPointerException.class,
				() -> VerifyVoterCapabilitySharesAndProof.apply(
						VOTER_CAPABILITIES_AND_PROOFS, POSTED_CAPABILITIES,
						EL_GAMAL_PUBLIC_KEY_E, null, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if posted capabilities is null, NullPointerException is throwns")
	void test6() {
		assertThrows(NullPointerException.class,
				() -> VerifyVoterCapabilitySharesAndProof.apply(
						VOTER_CAPABILITIES_AND_PROOFS, null, EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if the length of posted capabilities and the capabilities in the VoterCapabilitySharesAndProof are different, the check fails")
	void test7() {
		assertFalse(
				VerifyVoterCapabilitySharesAndProof.apply(VOTER_CAPABILITIES_AND_PROOFS,
						new ElGamalSignedCiphertext[1], EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if the length of capabilities and reencrypt factors in the VoterCapabilitySharesAndProof are different, the check fails")
	void test8() {
		assertFalse(VerifyVoterCapabilitySharesAndProof.apply(
				VOTER_CAPABILITIES_AND_PROOFS_BAD_FACTOR_COUNTS, POSTED_CAPABILITIES,
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME,
				TELLER_INDEX));
	}

	@Test
	@DisplayName("if the length of capabilities and reencrypt factors in the VoterCapabilitySharesAndProof are different, the check fails")
	void test9() {
		assertFalse(VerifyVoterCapabilitySharesAndProof.apply(
				VOTER_CAPABILITIES_AND_PROOFS_BAD_PROOF_COUNT, POSTED_CAPABILITIES,
				EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME,
				TELLER_INDEX));
	}

	@Test
	@DisplayName("if VoterCapabilitySharesAndProof is null, NullPointerException is throwns")
	void test10() {
		assertThrows(NullPointerException.class,
				() -> VerifyVoterCapabilitySharesAndProof.apply(null,
						POSTED_CAPABILITIES, EL_GAMAL_PUBLIC_KEY_E, EL_GAMAL_PUBLIC_KEY_E,
						VOTER_NAME, TELLER_INDEX));
	}

	@Test
	@DisplayName("if the voter public key is null, NullPointerException is throwns")
	void test11() {
		assertThrows(NullPointerException.class,
				() -> VerifyVoterCapabilitySharesAndProof.apply(
						VOTER_CAPABILITIES_AND_PROOFS, POSTED_CAPABILITIES, null,
						EL_GAMAL_PUBLIC_KEY_E, VOTER_NAME, TELLER_INDEX));
	}

}
