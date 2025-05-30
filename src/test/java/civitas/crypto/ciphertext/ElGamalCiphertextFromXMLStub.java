package civitas.crypto.ciphertext;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ElGamalCiphertextFromXMLStub
		implements ElGamalCiphertextCTestData {
	public ElGamalCiphertextFromXML stub() throws IOException {
		ElGamalCiphertextFromXML stub = mock(ElGamalCiphertextFromXML.class);
		ArgumentCaptor<Reader> readerArgument = ArgumentCaptor
				.forClass(Reader.class);

		doAnswer(new Answer<ElGamalCiphertext>() {
			@Override
			public ElGamalCiphertext answer(InvocationOnMock invocation)
					throws IOException {
				Reader reader = (Reader) invocation.getArguments()[0];
				reader.skip(EL_GAMAL_CIPHERTEXT_E_XML.length());
				return CIPHERTEXT_E;
			}
		}).when(stub).apply(readerArgument.capture());
		return stub;
	}
}
