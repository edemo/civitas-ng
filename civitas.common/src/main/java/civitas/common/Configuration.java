package civitas.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configuration {

	@Value("${storeFile}")
	public String storeFile;

	@Value("${storePassword}")
	public String storePassword;

	@Value("${serverKeyEntry}")
	public String serverKeyEntry;

	@Value("${urlBase}")
	public String urlBase;
}
