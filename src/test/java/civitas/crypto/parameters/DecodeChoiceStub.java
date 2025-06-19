package civitas.crypto.parameters;

import civitas.DI;

class DecodeChoiceStub {
	public static DecodeChoice stub() {
		return DI.get(DecodeChoice.class);
	}
}
