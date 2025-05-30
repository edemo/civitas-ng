package civitas.crypto.ciphertext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.io.PrintWriter;

import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ElGamalCiphertextToXMLStub implements ElGamalCiphertextCTestData {
	public static ElGamalCiphertextToXML stub() {
		ElGamalCiphertextToXML stub = mock(ElGamalCiphertextToXML.class);

		ArgumentCaptor<PrintWriter> argument = ArgumentCaptor
				.forClass(PrintWriter.class);
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) {
				PrintWriter printWriter = (PrintWriter) invocation.getArguments()[0];
				printWriter.append(EL_GAMAL_CIPHERTEXT_E_XML);
				return null;
			}
		}).when(stub).apply(any(), argument.capture());

		return stub;

	}

}
