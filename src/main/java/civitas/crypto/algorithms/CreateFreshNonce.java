package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateFreshNonce {

	@Autowired
	GetRandomGenerator getRandomGenerator;

	public byte[] apply(int bitlength) {
		int bytelength = bitlength / 8;
		if (bitlength % 8 != 0)
			bytelength++;
		byte[] bs = new byte[bytelength];
		getRandomGenerator.apply().nextBytes(bs);
		return bs;
	}

}
