package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;

@Controller
public class CreateFreshNonce {

	@Autowired
	CryptoBase cryptoBase;

	public byte[] apply(int bitlength) {
		int bytelength = bitlength / 8;
		if (bitlength % 8 != 0) {
			bytelength++;
		}
		byte[] bs = new byte[bytelength];
		cryptoBase.nextBytes(bs);
		return bs;
	}

}
