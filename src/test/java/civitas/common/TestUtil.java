package civitas.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

public class TestUtil {

	public static String readerToString(StringReader r) throws IOException {
		char[] target = new char[40];
		int i = r.read(target);
		String actual = new String(Arrays.copyOfRange(target, 0, i));
		return actual;
	}

}
