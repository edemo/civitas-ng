package civitas.bboard.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(final ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode().isError();
	}

	@Override
	public void handleError(final URI url, final HttpMethod method, final ClientHttpResponse httpResponse)
			throws IOException {
		String content;

		try (InputStream body = httpResponse.getBody();
				InputStreamReader inputStreamReader = new InputStreamReader(body);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
			content = bufferedReader.lines().reduce(String::concat).get();
		}

		throw new Error(httpResponse.getStatusCode() + ":" + content);
	}
}
