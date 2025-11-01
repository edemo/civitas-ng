package civitas.crypto.rsaprivatekey.tests;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import org.mockito.ArgumentMatcher;

public class KeySpecMatcherPrivate implements ArgumentMatcher<PKCS8EncodedKeySpec> {
	private final PKCS8EncodedKeySpec spec;

	public KeySpecMatcherPrivate(final PKCS8EncodedKeySpec keyspecPrivate) {
		this.spec = keyspecPrivate;
	}

	@Override
	public boolean matches(final PKCS8EncodedKeySpec argument) {
		if (null == argument) {
			return null == spec;
		}
		return Arrays.equals(spec.getEncoded(), argument.getEncoded());
	}
}
