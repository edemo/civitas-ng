package civitas.crypto.petdecommitment;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.util.Use;

public class PetCommitmentFromXML implements Constants {

	@Use
	ConvertToBigInt convertToBigInt;

	public PETCommitment apply(Reader r)
			throws IllegalArgumentException, IOException {
		String d = Util
				.unescapeString(Util.readSimpleTag(r, PETCommitmentOPENING_TAG));
		return new PETCommitment(convertToBigInt.apply(d));
	}

}
