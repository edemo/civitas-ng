package civitas.common.tests;

import static org.mockito.Mockito.mock;

import civitas.common.Configuration;

public class ConfigurationStub {
	public static Configuration stub() {
		Configuration mock = mock(Configuration.class);
		mock.storeFile = "lib/server.jks";
		mock.storePassword = "test12345";
		mock.serverKeyEntry = "bbs";
		mock.urlBase = "https://localhost/";
		return mock;
	}
}
