package civitas.crypto.msg;

import civitas.DI;

public class EncodeMessageStub {

	public static EncodeMessage stub() {
		return DI.get(EncodeMessage.class);
	}
}
