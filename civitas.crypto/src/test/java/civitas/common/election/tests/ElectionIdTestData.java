package civitas.common.election.tests;

import civitas.common.election.ElectionID;

public interface ElectionIdTestData {
	String ELECTION_ID_STRING = "The Greatest Election ID";
	String ELECTION_URI_BASE = "https://voting.demokracia.rulez.org:4420/voting";
	ElectionID ELECTION_ID = new ElectionID(ELECTION_ID_STRING, ELECTION_URI_BASE);
}
