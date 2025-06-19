package civitas.common.ballotdesign;

import civitas.DI;

class ConvertStringToChoiceStub {
	public static ConvertStringToChoice stub() {
		return DI.get(ConvertStringToChoice.class);
	}

}
