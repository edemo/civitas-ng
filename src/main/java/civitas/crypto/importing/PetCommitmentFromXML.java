package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.PETCommitment;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.PETCommitmentC;

public class PetCommitmentFromXML {

	public PETCommitmentC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String d = Util
				.unescapeString(Util.readSimpleTag(r, PETCommitment.OPENING_TAG));
		return new PETCommitmentC(CryptoFactoryC.stringToBigInt(d));
	}

}
