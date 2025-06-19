package civitas.common.ballotdesign;

import civitas.DI;

public class ConvertChoiceToStringStub {

	public static ConvertChoiceToString stub() {
		return DI.get(ConvertChoiceToString.class);
	}
}
