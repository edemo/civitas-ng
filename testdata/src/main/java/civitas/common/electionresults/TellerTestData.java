package civitas.common.electionresults;

import civitas.crypto.BasicValuesTestData;

public interface TellerTestData extends BasicValuesTestData {
	int TELLER_INDEX = 8;
	String TELLER_COMPUTER = "Teller" + TELLER_INDEX;
	String TELLER_META = "electionResults:" + TELLER_COMPUTER;

}
