package civitas.bboard.server.controllers.tests;

import java.util.Map;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import civitas.common.tests.EnvironmentState;
import civitas.common.tests.RandomAwareTestBase;

public class EnvDependentAnswer<T> implements Answer<T> {

	private final Map<EnvironmentState, AnswerOrThrowable<T>> answer;

	public EnvDependentAnswer(final Map<EnvironmentState, AnswerOrThrowable<T>> answer) {
		this.answer = answer;
	}

	@Override
	public T answer(final InvocationOnMock invocation) throws Throwable {

		AnswerOrThrowable<T> theAnswer = answer.get(RandomAwareTestBase.state);
		if (null == theAnswer) {
			theAnswer = answer.get(EnvironmentState.NORMAL);
		}
		if (null != theAnswer.throwable()) {
			throw theAnswer.throwable();
		}
		return theAnswer.answer();
	}
}
