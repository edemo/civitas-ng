package civitas.crypto.petdecommitment;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.CryptoFactoryC;
import civitas.crypto.petcommitment.PETCommitment;

public class PetCommitmentFromXML implements Constants {

	public PETCommitment apply(Reader r)
			throws IllegalArgumentException, IOException {
		String d = Util
				.unescapeString(Util.readSimpleTag(r, PETCommitmentOPENING_TAG));
		return new PETCommitment(CryptoFactoryC.stringToBigInt(d));
	}

}
