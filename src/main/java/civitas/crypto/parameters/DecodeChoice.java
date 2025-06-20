package civitas.crypto.parameters;

import java.util.Map;

import org.springframework.stereotype.Service;

import civitas.common.VoteChoice;
import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

@Service
public class DecodeChoice {
	public VoteChoice apply(Map<CivitasBigInteger, VoteChoice> map,
			CivitasBigInteger m) throws CryptoException {
		VoteChoice found = map.get(m);
		if (null == found)
			throw new CryptoException("Brute force decoding failed");
		return found;
	}

}
