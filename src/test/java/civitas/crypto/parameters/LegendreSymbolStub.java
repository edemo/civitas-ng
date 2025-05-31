package civitas.crypto.parameters;

import civitas.util.DI;

public class LegendreSymbolStub {

	public static LegendreSymbol stub() {
		return DI.get(LegendreSymbol.class);
	}
}
