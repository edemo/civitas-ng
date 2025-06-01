package civitas.crypto.signedciphertext;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;

import org.mockito.ArgumentCaptor;

import civitas.util.ReaderAnswer;

public class ElGamalSignedCiphertextFromXMLStub
		implements ElGamalSignedCiphertextTestData {

	public static ElGamalSignedCiphertextFromXML stub()
			throws IllegalArgumentException, IOException {

		ElGamalSignedCiphertextFromXML mock = mock(
				ElGamalSignedCiphertextFromXML.class);
		ArgumentCaptor<Reader> readerArgument = ArgumentCaptor
				.forClass(Reader.class);

		doAnswer(new ReaderAnswer<ElGamalSignedCiphertext>(
				EL_GAMAL_SIGNED_CIPHERTEXT_MOCKING)).when(mock)
				.apply(readerArgument.capture());
		return mock;
	}

}
