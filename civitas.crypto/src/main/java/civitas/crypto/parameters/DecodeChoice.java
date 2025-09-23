package civitas.crypto.parameters;

import java.util.Map;

import org.springframework.stereotype.Controller;

import civitas.common.VoteChoice;
import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

@Controller
public class DecodeChoice {
	public VoteChoice apply(final Map<CivitasBigInteger, VoteChoice> map, final CivitasBigInteger m)
			throws CryptoException {
		VoteChoice found = map.get(m);
		if (null == found) {
			throw new CryptoException("Brute force decoding failed");
		}
		return found;
	}
}
