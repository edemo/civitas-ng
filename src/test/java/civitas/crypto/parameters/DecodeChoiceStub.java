package civitas.crypto.parameters;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import civitas.common.VoteChoice;
import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

class DecodeChoiceStub implements ElGamalParametersTestData {
	public static DecodeChoice stub() throws CryptoException {
		DecodeChoice mock = mock(DecodeChoice.class);
		when(mock.apply(DECODEMAP, BIGINT_A))
				.thenThrow(IllegalArgumentException.class);
		for (VoteChoice choice : CHOICES) {
			when(mock.apply(DECODEMAP, CivitasBigInteger.valueOf(choice.ordinal())
					.modMultiply(BIGINT_G, BIGINT_P))).thenReturn(choice);
		}
		return mock;
	}
}
