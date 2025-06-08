package civitas.util;

class ObtainKeyFactoryStub {
	public static ObtainKeyFactory stub() {
		return DI.get(ObtainKeyFactory.class);
	}
}
