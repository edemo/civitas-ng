package civitas.crypto.importing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.crypto.SharedKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.SharedKeyC;

public class SharedKeyFromWire {

	public SharedKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		BufferedReader br;
		if (r instanceof BufferedReader) {
			br = (BufferedReader) r;
		} else {
			br = new BufferedReader(r);
		}

		String name = br.readLine();
		String s = br.readLine();
		byte[] bs = Base64.getDecoder().decode(s);
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new SharedKeyC(factory.sharedKeyFromBytes(bs), name);
	}

}
