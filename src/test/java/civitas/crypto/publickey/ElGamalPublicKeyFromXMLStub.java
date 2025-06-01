package civitas.crypto.publickey;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.Reader;

import org.mockito.ArgumentCaptor;

import civitas.util.ReaderAnswer;

public class ElGamalPublicKeyFromXMLStub implements ElGamalPublicKeyTestData {

	public static ElGamalPublicKeyFromXML stub()
			throws IllegalArgumentException, IOException {
		ElGamalPublicKeyFromXML mock = mock(ElGamalPublicKeyFromXML.class);
		ArgumentCaptor<Reader> readerArgument = ArgumentCaptor
				.forClass(Reader.class);

		doAnswer(new ReaderAnswer<ElGamalPublicKey>(EL_GAMAL_PUBLIC_KEY_MOCKING))
				.when(mock).apply(readerArgument.capture());
		return mock;
	}

}
