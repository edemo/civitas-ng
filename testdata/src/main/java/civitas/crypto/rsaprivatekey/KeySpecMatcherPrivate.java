package civitas.crypto.rsaprivatekey;

import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import org.mockito.ArgumentMatcher;

public class KeySpecMatcherPrivate
		implements ArgumentMatcher<PKCS8EncodedKeySpec> {
	private PKCS8EncodedKeySpec spec;

	public KeySpecMatcherPrivate(PKCS8EncodedKeySpec keyspecPrivate) {
		this.spec = keyspecPrivate;
	}

	@Override
	public boolean matches(PKCS8EncodedKeySpec argument) {
		if (null == argument)
			if (null == spec)
				return true;
			else
				return false;
		boolean res = Arrays.equals(spec.getEncoded(), argument.getEncoded());
		return res;
	}

}