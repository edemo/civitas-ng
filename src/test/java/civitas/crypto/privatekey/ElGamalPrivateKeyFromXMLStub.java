package civitas.crypto.privatekey;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;

import org.mockito.ArgumentCaptor;

import civitas.util.ReaderAnswer;

public class ElGamalPrivateKeyFromXMLStub implements ElGamalPrivateKeyTestData {

	public static ElGamalPrivateKeyFromXML stub()
			throws IllegalArgumentException, IOException {
		ElGamalPrivateKeyFromXML mock = mock(ElGamalPrivateKeyFromXML.class);
		ArgumentCaptor<Reader> readerArgument = ArgumentCaptor
				.forClass(Reader.class);
		doAnswer(new ReaderAnswer<ElGamalPrivateKey>(EL_GAMAL_PRIVATE_KEY_MOCKING))
				.when(mock).apply(readerArgument.capture());
		return mock;
	}

}
