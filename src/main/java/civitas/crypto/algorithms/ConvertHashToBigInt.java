package civitas.crypto.algorithms;

import org.springframework.stereotype.Service;

import civitas.util.CivitasBigInteger;

@Service
public class ConvertHashToBigInt {

	public CivitasBigInteger apply(byte[] hash) {
		CivitasBigInteger x = new CivitasBigInteger(1, hash);
		return x;
	}

}
