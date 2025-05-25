package civitas.crypto.algorithms;

import civitas.util.Use;

public class CreateFreshNonce {

	@Use
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
