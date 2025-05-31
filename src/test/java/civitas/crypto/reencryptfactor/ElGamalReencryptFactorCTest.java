package civitas.crypto.reencryptfactor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

public class ElGamalReencryptFactorCTest extends TestBase
		implements ElGamalReencryptFactorCTestData {
	@Use
	ElGamalReencryptFactorFromXML elGamalReencryptFactorFromXML;
	@Use
	ElGamalReencryptFactorToXML elGamalReencryptFactorToXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_XML, elGamalReencryptFactorToXML
				.apply(new ElGamalReencryptFactor(BIGINT_A)));
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		ElGamalReencryptFactor a = elGamalReencryptFactorFromXML
				.apply(new StringReader(ELGAMAL_REENCRYPT_FACTOR_XML));
		assertEquals(ELGAMAL_REENCRYPT_FACTOR_XML,
				elGamalReencryptFactorToXML.apply(a));
	}

}
