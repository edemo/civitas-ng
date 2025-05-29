package civitas.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.crypto.CryptoFactory;
import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ConcreteTestData;

public class CiphertextListTest extends ConcreteTestBase
		implements ConcreteTestData {

	CryptoFactory mockFactory = mock(CryptoFactory.class);
	CiphertextList zerosized = new CiphertextList(null);
	CiphertextList nullInside = new CiphertextList(null, true);

	ElGamalCiphertext ciphertext = mock(ElGamalCiphertext.class);
	CiphertextList oneInside = new CiphertextList(
			new ElGamalCiphertext[] { ciphertext });

	@Override
	@BeforeEach
	public void setUp() throws IllegalArgumentException, IOException {
		ArgumentCaptor<PrintWriter> argument = ArgumentCaptor
				.forClass(PrintWriter.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				PrintWriter printWriter = (PrintWriter) invocation.getArguments()[0];
				printWriter.append(EL_GAMAL_CIPHERTEXT_E_XML);
				return null;
			}
		}).when(ciphertext).toXML(argument.capture());

		CryptoFactory factory = mock(CryptoFactory.class);
		CiphertextList.factory = factory;

		ArgumentCaptor<Reader> readerArgument = ArgumentCaptor
				.forClass(Reader.class);

		doAnswer(new Answer<ElGamalCiphertext>() {
			@Override
			public ElGamalCiphertext answer(InvocationOnMock invocation)
					throws IOException {
				Reader reader = (Reader) invocation.getArguments()[0];
				reader.skip(EL_GAMAL_CIPHERTEXT_E_XML.length());
				return ciphertext;
			}
		}).when(factory).elGamalCiphertextFromXML(readerArgument.capture());

	}

	@Test
	@DisplayName("in case of null argument the constructor creates a zero-length list")
	void test() {
		assertEquals(0, zerosized.size());
	}

	@Test
	@DisplayName("in case of null argument the two-argument constructor creates a zero-length list")
	void test2() {
		assertEquals(0, nullInside.size());
	}

	@Test
	@DisplayName("get(n) gets the nth cypertext")
	void test3() {
		assertEquals(ciphertext, oneInside.get(0));
	}

	@Test
	@DisplayName("get(n) throws IndexOutOfBoundsException for out of bounds index")
	void test4() {
		assertThrows(IndexOutOfBoundsException.class, () -> oneInside.get(1));
	}

	@Test
	@DisplayName("get(n) throws IndexOutOfBoundsException if the list is null inside")
	void test5() {
		assertThrows(IndexOutOfBoundsException.class, () -> nullInside.get(0));
	}

	@Test
	@DisplayName("toXml returns an xml representation of the list")
	void test6() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		oneInside.toXML(printWriter);
		assertEquals(CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml returns correct xml for an empty one")
	void test6_1() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		zerosized.toXML(printWriter);
		assertEquals(EMPTY_CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml returns correct xml for one with null inside"
			+ "FIXME: original code catched NullPointerException instead of using the computed length in the for cycle")
	void test6_2() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		nullInside.toXML(printWriter);
		assertEquals(EMPTY_CIPHERTEXT_LIST_AS_XML, stringWriter.toString());
	}

	@Test
	@DisplayName("toXml does nothing silently if printwriter is null")
	void test6_3() {
		assertDoesNotThrow(() -> zerosized.toXML(null));
	}

	@Test
	@DisplayName("fromXML converts the xml to a CiphertextList")
	void test7() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(CIPHERTEXT_LIST_AS_XML);
		CiphertextList cl = CiphertextList.fromXML(sr);
		assertEquals(ciphertext, cl.get(0));
		assertEquals(1, cl.size());
	}

	@Test
	@DisplayName("fromXML converts the xml of an empty CiphertextList correctly")
	void test8() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(EMPTY_CIPHERTEXT_LIST_AS_XML);
		CiphertextList cl = CiphertextList.fromXML(sr);
		assertEquals(0, cl.size());
	}

	@Test
	@DisplayName("fromXML converts the xml with a negative length to an empthy list")
	void test9() throws IllegalArgumentException, IOException {
		StringReader sr = new StringReader(
				CIPHERTEXT_LIST_XML_WITH_NEGATIVE_LENGTH);
		CiphertextList cl = CiphertextList.fromXML(sr);
		assertEquals(0, cl.size());
	}
}
