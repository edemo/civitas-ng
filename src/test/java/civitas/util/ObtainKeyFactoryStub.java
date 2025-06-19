package civitas.util;

import civitas.DI;

class ObtainKeyFactoryStub {
	public static ObtainKeyFactory stub() {
		return DI.get(ObtainKeyFactory.class);
	}
}
