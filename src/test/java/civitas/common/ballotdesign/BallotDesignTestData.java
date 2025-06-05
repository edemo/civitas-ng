package civitas.common.ballotdesign;

public interface BallotDesignTestData {
	BallotDesign BALLOTDESIGN = new BallotDesign(
			new String[] { "Sleepy Joe", "Agent Orange", "none of the above" });
	// @formatting:off
	String BALLOTDESIGN_XML = "<ballotDesign><size>3</size><candidates>"
			+ "<candidate>Sleepy Joe</candidate>"
			+ "<candidate>Agent Orange</candidate>"
			+ "<candidate>none of the above</candidate>"
			+ "</candidates></ballotDesign>";
	// @formatting:on

}
