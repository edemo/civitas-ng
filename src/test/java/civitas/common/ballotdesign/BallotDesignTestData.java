package civitas.common.ballotdesign;

import java.util.List;
import java.util.Map;

import civitas.common.CommonConstants;
import civitas.crypto.BasicValuesTestData;
import civitas.util.CivitasBigInteger;

public interface BallotDesignTestData
		extends CommonConstants, BasicValuesTestData {
	String ADDITIONALENV = "Árvíztűrő Tükörfúrógép";
	byte[] HASH_OF_ADDITIONALENV = "hash_of_additionalenv".getBytes();
	CivitasBigInteger BIGINTEGER_HASH_OF_ADDITIONALENV = new CivitasBigInteger(1,
			HASH_OF_ADDITIONALENV);

	String BARE_CONTEXT_0 = CommonConstants.KIND + "0:1";
	String BARE_CONTEXT_1 = CommonConstants.KIND + "0:2";
	String BARE_CONTEXT_2 = CommonConstants.KIND + "1:2";

	String CONTEXT_BAD = ADDITIONALENV + CommonConstants.KIND + "01";
	String CONTEXT_BAD_NOINT = ADDITIONALENV + CommonConstants.KIND + "béla:1";

	String CONTEXT_0 = ADDITIONALENV + BARE_CONTEXT_0;
	String CONTEXT_1 = ADDITIONALENV + BARE_CONTEXT_1;
	String CONTEXT_2 = ADDITIONALENV + BARE_CONTEXT_2;

	List<Integer> VOTE_PIECES = List.of(0, 1, 2);
	List<String> CONTEXTS = List.of(CONTEXT_0, CONTEXT_1, CONTEXT_2);

	Map<Integer, String> CONTEXT_MAP = Map.of(0, CONTEXT_0, 1, CONTEXT_1, 2,
			CONTEXT_2);

	Map<Integer, Integer> VOTE_CONTENTS_MAP = Map.of(0,
			CommonConstants.VOTE_CHOICE_I_BEATS_J, 1,
			CommonConstants.VOTE_CHOICE_NEITHER_BEAT, 2,
			CommonConstants.VOTE_CHOICE_J_BEATS_I);

	byte[] ADDITIONALENV_BYTES = ADDITIONALENV.getBytes();

	String CANDIDATE = "Agent Orange";
	List<String> CANDIDATES = List.of("Sleepy Joe", CANDIDATE, NONE_OF_ABOVE);
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
