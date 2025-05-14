package civitas.crypto.concrete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElGamalReencryptFactorCTest extends ConcreteTestBase {

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_XML,
				new ElGamalReencryptFactorC(BIGINT_A).toXML());
	}

	@Test
	@DisplayName("constructor parameter can be null")
	void test1() {
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_NULL_XML,
				new ElGamalReencryptFactorC(null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		ElGamalReencryptFactorC a = (ElGamalReencryptFactorC) ElGamalReencryptFactorC
				.fromXML(new StringReader(ELGAMAL_REENCRYPT_FACTOR_XML));
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_XML, a.toXML());
	}

}
