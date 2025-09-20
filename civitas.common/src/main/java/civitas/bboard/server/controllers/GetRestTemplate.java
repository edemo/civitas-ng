package civitas.bboard.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import civitas.bboard.server.RestTemplateResponseErrorHandler;

@Component
public class GetRestTemplate {
	@Autowired
	RestTemplateBuilder restTemplateBuilder;

	RestTemplate restTemplate;

	public RestTemplate apply() {
		if (null == restTemplate) {
			restTemplate = restTemplateBuilder
					.errorHandler(new RestTemplateResponseErrorHandler())
					.build();
		}
		return restTemplate;
	}
}
