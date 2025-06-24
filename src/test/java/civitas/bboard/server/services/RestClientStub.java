package civitas.bboard.server.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.web.client.RestTemplate;

import civitas.common.election.ElectionTestData;

class RestClientStub implements ElectionTestData {
	public static RestClient stub() {
		RestClient mock = mock(RestClient.class);
		RestTemplate restTemplateMock = mock(RestTemplate.class);
		mock.restTemplate = restTemplateMock;
		when(restTemplateMock.postForObject(eq(ELECTION_ID.uriBase + "/post"),
				any(), any())).then(new ElectionServerAnswer<Boolean>(true));
		return mock;
	}
}
