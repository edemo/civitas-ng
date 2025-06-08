package civitas.crypto.votecapability;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import civitas.util.Use;

public class VoteCapabilityTest extends TestBase
		implements VoteCapabilityShareTestData {
	@Use
	VoteCapabilityFromXML voteCapabilityFromXML;
	@Use
	EncodeMessage encodeMessage;
	@Use
	VoteCapabilityToXML voteCapabilityToXML;

	@Test
	@DisplayName("BigInteger+parameter constructor and toXML works as expected")
	void test() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		voteCapabilityToXML.apply(new VoteCapability(encodeMessage
				.apply(MESSAGE_VOTE_CAPABILITY_SHARE, EL_GAMAL_PARAMETERS)), p);
		assertEquals(VOTE_CAPABILITY_XML, s.toString());
	}

	@Test
	@DisplayName("String constructor works as expected")
	void test1() throws CryptoException {
		assertEquals(VOTE_CAPABILITY,
				new VoteCapability(encodeMessage.apply(VOTE, EL_GAMAL_PARAMETERS)));
	}

	@Test
	@DisplayName("BigInteger constructor and toXML works as expected")
	void test2() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		voteCapabilityToXML.apply(VOTE_CAPABILITY, p);
		assertEquals(VOTE_CAPABILITY_XML, s.toString());
	}

	@Test
	@DisplayName("fromXML and equals works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(voteCapabilityFromXML
				.apply(new StringReader(VOTE_CAPABILITY_XML)).equals(VOTE_CAPABILITY));
	}

	@Test
	@DisplayName("does not equal anything with other class")
	void test3_1() throws IllegalArgumentException, IOException {

		assertFalse(VOTE_CAPABILITY.equals(mock(VoteCapability.class)));
	}

	@Test
	@DisplayName("intValue works as expected")
	void test4() throws IllegalArgumentException, IOException {

		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.intValue(),
				VOTE_CAPABILITY.m.intValue());
	}

}
