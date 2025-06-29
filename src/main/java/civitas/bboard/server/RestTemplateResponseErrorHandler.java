package civitas.bboard.server;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
		return httpResponse.getStatusCode().isError();
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {
		InputStream body = httpResponse.getBody();
		throw new Error(httpResponse.getStatusCode() + ":"
				+ new String(body.readAllBytes(), StandardCharsets.UTF_8));
	}
}