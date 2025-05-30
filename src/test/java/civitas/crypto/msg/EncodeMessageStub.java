package civitas.crypto.msg;

import civitas.util.DI;

public class EncodeMessageStub {

	public static EncodeMessage stub() {
		return DI.get(EncodeMessage.class);
	}
}
