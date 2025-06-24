package civitas.bboard.server.services;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient {
	RestTemplate restTemplate = new RestTemplate();

}