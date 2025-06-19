package civitas.util;

import java.security.KeyFactory;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;

@Service
public class ObtainKeyFactory {

	private static KeyFactory instance = null;

	@SneakyThrows
	public KeyFactory apply() {
		if (instance == null)
			instance = KeyFactory.getInstance("RSA", "BC");
		return instance;
	}

}
