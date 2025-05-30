package civitas.crypto.petdecommitment;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.petcommitment.PETCommitmentC;

public class PetCommitmentFromXML {

	public PETCommitmentC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String d = Util
				.unescapeString(Util.readSimpleTag(r, PETCommitment.OPENING_TAG));
		return new PETCommitmentC(CryptoFactoryC.stringToBigInt(d));
	}

}
