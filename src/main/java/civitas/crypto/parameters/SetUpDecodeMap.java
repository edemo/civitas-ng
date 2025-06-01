package civitas.crypto.parameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import civitas.util.CivitasBigInteger;

public class SetUpDecodeMap {

	public Map<CivitasBigInteger, Integer> apply(List<Integer> choices,
			ElGamalParameters params) {
		Map<CivitasBigInteger, Integer> map = new HashMap<>();
		choices.forEach(x -> map
				.put(CivitasBigInteger.valueOf(x).modMultiply(params.g, params.p), x));
		return map;
	}

}
