package civitas.common.tallystate;

import civitas.DI;

class RecordBeatStub {
	public static RecordBeat stub() {
		return DI.get(RecordBeat.class);
	}
}
