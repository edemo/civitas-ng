package civitas.crypto.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import civitas.common.VoteChoice;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public class SetUpDecodeMap {

	public Map<CivitasBigInteger, VoteChoice> apply(List<VoteChoice> choices,
			ElGamalParameters params) {
		Map<CivitasBigInteger, VoteChoice> map = new HashMap<>();
		choices.forEach(x -> map.put(CivitasBigIntegerFactory.obtain(x.ordinal())
				.modMultiply(params.g(), params.p()), x));
		return map;
	}

}
