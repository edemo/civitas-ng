package civitas.crypto.sharedkey;

import java.io.PrintWriter;
import java.util.Base64;

public class SharedKeyToWire {

	public void apply(SharedKey that, PrintWriter s) {
		s.print(that.name);
		s.print('\n');
		byte[] bs = that.k.getEncoded();
		String base64 = Base64.getEncoder().encodeToString(bs);
		s.print(base64);
		s.print('\n');
	}

}
