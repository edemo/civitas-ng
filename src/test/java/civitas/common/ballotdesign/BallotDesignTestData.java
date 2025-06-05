package civitas.common.ballotdesign;

import java.util.List;

import civitas.common.CommonConstants;

public interface BallotDesignTestData extends CommonConstants {
	List<String> CANDIDATES = List.of("Sleepy Joe", "Agent Orange",
			NONE_OF_ABOVE);
	BallotDesign BALLOTDESIGN = new BallotDesign(
			CANDIDATES.toArray(new String[0]));
	// @formatting:off
	String BALLOTDESIGN_XML = "<ballotDesign><size>3</size><candidates>"
			+ "<candidate>Sleepy Joe</candidate>"
			+ "<candidate>Agent Orange</candidate>"
			+ "<candidate>none of the above</candidate>"
			+ "</candidates></ballotDesign>";
	// @formatting:on

}
