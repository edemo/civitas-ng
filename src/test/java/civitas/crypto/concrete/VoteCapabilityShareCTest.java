package civitas.crypto.concrete;

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

import civitas.crypto.CryptoException;
import civitas.crypto.VoteCapabilityShare;
import civitas.crypto.importing.VoteCapabilityShareFromXML;
import civitas.util.Use;

public class VoteCapabilityShareCTest extends ConcreteTestBase
		implements ConcreteTestData {

	@Test
	@DisplayName("BigInteger+parameter constructor and toXML works as expected")
	void test() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		VOTE_CAPABILITY_SHARE.toXML(p);
		assertEquals(VOTE_CAPABILITY_SHARE_XML, s.toString());
	}

	@Test
	@DisplayName("String constructor works as expected")
	void test1() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		new VoteCapabilityShareC(SOMESTRING, EL_GAMAL_PARAMETERS).toXML(p);
		assertEquals(VOTE_CAPABILITY_SHARE_XML, s.toString());
	}

	@Test
	@DisplayName("BigInteger constructor and toXML works as expected")
	void test2() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		VOTE_CAPABILITY_SHARE_JUST_BIGINT.toXML(p);
		assertEquals(VOTE_CAPABILITY_SHARE_JUST_BIGINT_XML, s.toString());
	}

	@Test
	@DisplayName("BigInteger constructor accepts null")
	void test2_() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		new VoteCapabilityShareC(null).toXML(p);
		assertEquals(VOTE_CAPABILITY_SHARE_NULL_XML, s.toString());
	}

	@Use
	VoteCapabilityShareFromXML voteCapabilityShareFromXML;

	@Test
	@DisplayName("fromXML and equals works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(voteCapabilityShareFromXML
				.apply(new StringReader(VOTE_CAPABILITY_SHARE_XML))
				.equals(VOTE_CAPABILITY_SHARE));
	}

	@Test
	@DisplayName("does not equal anything with other class")
	void test3_1() throws IllegalArgumentException, IOException {

		assertFalse(VOTE_CAPABILITY_SHARE.equals(mock(VoteCapabilityShare.class)));
	}

	@Test
	@DisplayName("intValue works as expected")
	void test4() throws IllegalArgumentException, IOException {

		assertEquals(VOTE_CAPABILITY_SHARE_INTVALUE,
				VOTE_CAPABILITY_SHARE.intValue());
	}

}
