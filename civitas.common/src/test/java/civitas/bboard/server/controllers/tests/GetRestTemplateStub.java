package civitas.bboard.server.controllers.tests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import civitas.bboard.server.controllers.GetRestTemplate;
import civitas.common.election.tests.ElectionDetailsTestData;
import civitas.common.tests.EnvironmentState;

public class GetRestTemplateStub implements ElectionDetailsTestData {
	public static GetRestTemplate stub() throws NoSuchFieldException, IllegalAccessException {
		GetRestTemplate mock = mock(GetRestTemplate.class);
		RestTemplate restTemplateMock = mock(RestTemplate.class);
		Field restTemplate = GetRestTemplate.class.getDeclaredField("restTemplate");
		restTemplate.setAccessible(true);
		restTemplate.set(mock, restTemplateMock);
		when(mock.apply()).thenReturn(restTemplateMock);
		when(restTemplateMock.postForObject(eq(ELECTION_ID.uriBase() + "/post"), any(), any()))
				.then(new EnvDependentAnswer<>(Map.of(
						EnvironmentState.NORMAL,
						new AnswerOrThrowable<>(null, true),
						EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE,
						new AnswerOrThrowable<>(new IOException(), null))));
		return mock;
	}
}
