package civitas.crypto.ciphertext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.PrintWriter;

import org.mockito.ArgumentCaptor;

import civitas.util.XMLAnswer;

public class ElGamalCiphertextToXMLStub implements ElGamalCiphertextTestData {
	public static ElGamalCiphertextToXML stub() {
		ElGamalCiphertextToXML stub = mock(ElGamalCiphertextToXML.class);

		ArgumentCaptor<PrintWriter> argument = ArgumentCaptor
				.forClass(PrintWriter.class);

		doAnswer(new XMLAnswer(EL_GAMAL_CIPHERTEXT_E_XML)).when(stub)
				.apply(eq(CIPHERTEXT_E), argument.capture());
		doAnswer(new XMLAnswer(CIPHERTEXT_ENCCAP_XML)).when(stub)
				.apply(eq(CIPHERTEXT_ENCCAP), argument.capture());

		return stub;
	}

}
