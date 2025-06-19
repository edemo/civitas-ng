package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.util.CivitasBigInteger;

@Service
public class GenerateRandomElement {

	@Autowired
	GetRandomGenerator getRandomGenerator;

	public CivitasBigInteger apply(CivitasBigInteger n) {
		CivitasBigInteger r = null;
		do {
			r = new CivitasBigInteger(n.bitLength(), getRandomGenerator.apply());
		} while (r.equals(Constants.ZERO) || r.compareTo(n) >= 0);
		return r;
	}

}
