package civitas.util;

import java.security.KeyFactory;

import lombok.SneakyThrows;

public class ObtainKeyFactory {

	private static KeyFactory instance = null;

	@SneakyThrows
	public KeyFactory apply() {
		if (instance == null)
			instance = KeyFactory.getInstance("RSA", "BC");
		return instance;
	}

}
