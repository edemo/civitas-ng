package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalProofDiscLogEquality;
import civitas.crypto.PETDecommitment;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.PETDecommitmentC;
import civitas.util.Use;

public class PetDecommitmentFromXML {
	@Use
	ElGamalProofDiscLogEqualityFromXML elGamalProofDiscLogEqualityFromXML;

	public PETDecommitmentC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, PETDecommitment.OPENING_TAG);
		String d = Util.unescapeString(Util.readSimpleTag(r, "d"));
		String e = Util.unescapeString(Util.readSimpleTag(r, "e"));
		Util.swallowTag(r, "prf");
		ElGamalProofDiscLogEquality proof = elGamalProofDiscLogEqualityFromXML
				.apply(r);
		Util.swallowEndTag(r, "prf");
		Util.swallowEndTag(r, PETDecommitment.OPENING_TAG);
		return new PETDecommitmentC(CryptoFactoryC.stringToBigInt(d),
				CryptoFactoryC.stringToBigInt(e), proof);
	}

}
