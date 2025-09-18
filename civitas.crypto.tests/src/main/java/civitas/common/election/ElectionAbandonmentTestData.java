package civitas.common.election;

import civitas.common.electionresults.TellerTestData;

public interface ElectionAbandonmentTestData extends TellerTestData {
	String ABANDONMENT_REASON = "just why not";
	ElectionAbandonment ELECTION_ABANDONMENT = new ElectionAbandonment(TELLER_INDEX, true, ABANDONMENT_REASON);
	ElectionAbandonment ELECTION_ABANDONMENT_NONTELLER =
			new ElectionAbandonment(TELLER_INDEX, false, ABANDONMENT_REASON);
	String ELECTION_ABANDONMENT_REPORTER = "tabulation teller " + TELLER_INDEX;
	String ELECTION_ABANDONMENT_REPORTER_UNKNOWN = "unknown entity";
}
