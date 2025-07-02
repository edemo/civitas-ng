package civitas.bboard.server.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import civitas.common.EnvironmentState;
import civitas.common.election.ElectionDetailsTestData;

class GetRestTemplateStub implements ElectionDetailsTestData {
	public static GetRestTemplate stub() {
		GetRestTemplate mock = mock(GetRestTemplate.class);
		RestTemplate restTemplateMock = mock(RestTemplate.class);
		mock.restTemplate = restTemplateMock;
		when(mock.apply()).thenReturn(restTemplateMock);
		when(restTemplateMock.postForObject(eq(ELECTION_ID.uriBase + "/post"),
				any(), any()))
				.then(new EnvDependentAnswer<>(Map.of(EnvironmentState.NORMAL,
						new AnswerOrThrowable<>(null, true),
						EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE,
						new AnswerOrThrowable<>(new IOException(), null))));
		return mock;
	}
}
