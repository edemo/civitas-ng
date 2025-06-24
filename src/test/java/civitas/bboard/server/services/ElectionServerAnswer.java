package civitas.bboard.server.services;

import java.io.IOException;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.common.EnvironmentState;
import civitas.common.TestBase;

class ElectionServerAnswer<T> implements Answer<T> {

	private T answer;

	ElectionServerAnswer(T answer) {
		this.answer = answer;
	}

	@Override
	public T answer(InvocationOnMock invocation) throws Throwable {

		if (TestBase.states
				.contains(EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE))
			throw new IOException();
		return answer;

	}

}