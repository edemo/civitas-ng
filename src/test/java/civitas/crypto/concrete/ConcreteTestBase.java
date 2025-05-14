package civitas.crypto.concrete;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeEach;

import civitas.crypto.CryptoException;

public class ConcreteTestBase extends ConcreteTestData {

	public MessageDigestC md;
	public java.security.MessageDigest baselineDigest;

	@BeforeEach
	void setUp() throws NoSuchAlgorithmException, IllegalArgumentException,
			IOException, CryptoException {

		java.security.MessageDigest messageDigest = java.security.MessageDigest
				.getInstance("SHA-256");
		baselineDigest = java.security.MessageDigest.getInstance("SHA-256");
		md = new MessageDigestC(messageDigest);

	}

}