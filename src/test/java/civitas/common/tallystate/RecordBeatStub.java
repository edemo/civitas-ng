package civitas.common.tallystate;

import civitas.util.DI;

class RecordBeatStub {
	public static RecordBeat stub() {
		return DI.get(RecordBeat.class);
	}
}
