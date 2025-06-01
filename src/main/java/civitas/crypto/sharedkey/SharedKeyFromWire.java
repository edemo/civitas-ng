package civitas.crypto.sharedkey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.util.Use;

public class SharedKeyFromWire {
	@Use
	CreateSharedKeyFromBytes createSharedKeyFromBytes;

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
		return new SharedKey(createSharedKeyFromBytes.apply(bs), name);
	}

}
