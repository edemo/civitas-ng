package civitas.crypto.rsaprivatekey;

import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import org.mockito.ArgumentMatcher;

public class KeySpecMatcher implements ArgumentMatcher<X509EncodedKeySpec> {
	private final X509EncodedKeySpec spec;

	public KeySpecMatcher(final X509EncodedKeySpec spec) {
		this.spec = spec;
	}

	@Override
	public boolean matches(final X509EncodedKeySpec argument) {
		if (null == argument) {
			return null == spec;
		}
		return Arrays.equals(spec.getEncoded(), argument.getEncoded());
	}
}
