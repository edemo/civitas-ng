package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.petdecommitment.PetCommitmentFromXML;
import civitas.util.Use;

public class PETCommitmentCTest extends ConcreteTestBase
		implements PETCommitmentCTestData {
	@Use
	PetCommitmentFromXML petCommitmentFromXML;

	@Test
	@DisplayName("constructor and toXML works as expected")
	void test() {
		assertEquals(PET_C_XML, PET_COMMITMENT.toXML());
	}

	@Test
	@DisplayName("constructor accepts null parameter")
	void test1() {
		assertEquals(PET_C_NULL_XML, new PETCommitmentC(null).toXML());
	}

	@Test
	@DisplayName("fromXML works as expected")
	void test2() throws IllegalArgumentException, IOException {
		assertEquals(PET_C_XML,
				petCommitmentFromXML.apply(new StringReader(PET_C_XML)).toXML());
	}

}
