package civitas.common;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.crypto.BasicValuesTestData;
import civitas.util.CivitasBigInteger;

public class RandomAnswer implements Answer<CivitasBigInteger> {
	public static int step = 0;

	@Override
	public CivitasBigInteger answer(InvocationOnMock invocation) {

		CivitasBigInteger random = BasicValuesTestData.RANDOMS.get(step);
		step++;
		return random;
	}

}