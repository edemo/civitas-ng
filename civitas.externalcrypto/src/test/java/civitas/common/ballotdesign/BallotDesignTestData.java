package civitas.common.ballotdesign;

import java.util.List;
import java.util.Map;

import civitas.common.CommonConstants;
import civitas.common.VoteChoice;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface BallotDesignTestData extends CommonConstants {
	String ADDITIONALENV = "Árvíztűrő Tükörfúrógép";
	byte[] HASH_OF_ADDITIONALENV = "hash_of_additionalenv".getBytes();
	CivitasBigInteger BIGINTEGER_HASH_OF_ADDITIONALENV = CivitasBigIntegerFactory.obtain(1, HASH_OF_ADDITIONALENV);

	String BARE_CONTEXT_0 = KIND + "0:1";
	String BARE_CONTEXT_1 = KIND + "0:2";
	String BARE_CONTEXT_2 = KIND + "1:2";

	String CONTEXT_BAD = ADDITIONALENV + KIND + "01";
	String CONTEXT_BAD_NOINT = ADDITIONALENV + KIND + "béla:1";

	String CONTEXT_0 = ADDITIONALENV + BARE_CONTEXT_0;
	String CONTEXT_1 = ADDITIONALENV + BARE_CONTEXT_1;
	String CONTEXT_2 = ADDITIONALENV + BARE_CONTEXT_2;

	List<Integer> VOTE_PIECES = List.of(0, 1, 2);
	List<String> CONTEXTS = List.of(CONTEXT_0, CONTEXT_1, CONTEXT_2);

	Map<Integer, String> CONTEXT_MAP = Map.of(0, CONTEXT_0, 1, CONTEXT_1, 2, CONTEXT_2);

	Map<Integer, VoteChoice> VOTE_CONTENTS_MAP =
			Map.of(0, VoteChoice.I_BEATS_J, 1, VoteChoice.J_BEATS_I, 2, VoteChoice.NEITHER_BEAT);

	byte[] ADDITIONALENV_BYTES = ADDITIONALENV.getBytes();

	String CANDIDATE = "Agent Orange";
	List<String> CANDIDATES = List.of("Sleepy Joe", CANDIDATE, NONE_OF_ABOVE);
	BallotDesign BALLOTDESIGN = new BallotDesign(CANDIDATES.toArray(new String[0]));
}
