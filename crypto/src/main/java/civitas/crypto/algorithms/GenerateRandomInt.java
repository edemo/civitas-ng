package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;

@Controller
public class GenerateRandomInt {

	@Autowired
	CryptoBase cryptoBase;

	public int apply(int n) {
		if (n <= 0)
			return 0;
		return cryptoBase.getRandomGenerator().nextInt(n);
	}

}
