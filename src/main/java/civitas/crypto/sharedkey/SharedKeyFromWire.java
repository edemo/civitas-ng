package civitas.crypto.sharedkey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SharedKeyFromWire {
	@Autowired
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
