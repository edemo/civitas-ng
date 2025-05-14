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
import civitas.crypto.VoteCapability;

public class VoteCapabilityCTest extends ConcreteTestBase {

	@Test
	@DisplayName("BigInteger+parameter constructor and toXML works as expected")
	void test() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		VOTE_CAPABILITY.toXML(p);
		assertEquals(VOTE_CAPABILITY_XML, s.toString());
	}

	@Test
	@DisplayName("String constructor works as expected")
	void test1() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		new VoteCapabilityC(SOMESTRING, EL_GAMAL_PARAMETERS).toXML(p);
		assertEquals(VOTE_CAPABILITY_XML, s.toString());
	}

	@Test
	@DisplayName("BigInteger constructor and toXML works as expected")
	void test2() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		VOTE_CAPABILITY_JUST_BIGINT.toXML(p);
		assertEquals(VOTE_CAPABILITY_JUST_BIGINT_XML, s.toString());
	}

	@Test
	@DisplayName("BigInteger constructor accepts null")
	void test2_() throws CryptoException {
		StringWriter s = new StringWriter();
		PrintWriter p = new PrintWriter(s);
		new VoteCapabilityC(null).toXML(p);
		assertEquals(VOTE_CAPABILITY_NULL_XML, s.toString());
	}

	@Test
	@DisplayName("fromXML and equals works as expected")
	void test3() throws IllegalArgumentException, IOException {

		assertTrue(VoteCapabilityC.fromXML(new StringReader(VOTE_CAPABILITY_XML))
				.equals(VOTE_CAPABILITY));
	}

	@Test
	@DisplayName("does not equal anything with other class")
	void test3_1() throws IllegalArgumentException, IOException {

		assertFalse(VOTE_CAPABILITY.equals(mock(VoteCapability.class)));
	}

	@Test
	@DisplayName("intValue works as expected")
	void test4() throws IllegalArgumentException, IOException {

		assertEquals(VOTE_CAPABILITY_INTVALUE, VOTE_CAPABILITY.intValue());
	}

}
