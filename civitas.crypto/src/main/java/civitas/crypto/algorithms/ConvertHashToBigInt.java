package civitas.crypto.algorithms;

import org.springframework.stereotype.Controller;

import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

@Controller
public class ConvertHashToBigInt {

	public CivitasBigInteger apply(final byte[] hash) {
		return CivitasBigIntegerFactory.obtain(1, hash);
	}
}
