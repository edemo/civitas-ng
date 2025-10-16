package civitas.common;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.util.BasicValuesTestData;

public class RandomFillerAnswer implements Answer<Void> {

	@Override
	public Void answer(final InvocationOnMock invocation) {

		TestUtil.fakeRandomToArray(invocation, BasicValuesTestData.RANDOMS.get(RandomAnswer.step));
		RandomAnswer.step++;
		return null;
	}
}
