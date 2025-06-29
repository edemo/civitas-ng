package civitas.crypto.algorithms;

import org.springframework.stereotype.Controller;

import civitas.util.CivitasBigInteger;

@Controller
public class ConvertHashToBigInt {

	public CivitasBigInteger apply(byte[] hash) {
		CivitasBigInteger x = new CivitasBigInteger(1, hash);
		return x;
	}

}
