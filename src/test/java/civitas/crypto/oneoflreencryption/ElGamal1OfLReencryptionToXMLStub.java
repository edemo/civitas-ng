package civitas.crypto.oneoflreencryption;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;

import org.mockito.ArgumentCaptor;

import civitas.util.XMLAnswer;

class ElGamal1OfLReencryptionToXMLStub
		implements ElGamal1OfLReencryptionTestData {
	public static ElGamal1OfLReencryptionToXML stub() {
		ElGamal1OfLReencryptionToXML mock = mock(
				ElGamal1OfLReencryptionToXML.class);
		when(mock.apply(EL_GAMAL_1_OF_L_REENCRYPTION))
				.thenReturn(EL_GAMAL_1_OF_L_REENCRYPTION_XML);

		ArgumentCaptor<PrintWriter> argument = ArgumentCaptor
				.forClass(PrintWriter.class);
		doAnswer(new XMLAnswer(EL_GAMAL_1_OF_L_REENCRYPTION_XML)).when(mock)
				.apply(eq(EL_GAMAL_1_OF_L_REENCRYPTION), argument.capture());

		return mock;
	}
}
