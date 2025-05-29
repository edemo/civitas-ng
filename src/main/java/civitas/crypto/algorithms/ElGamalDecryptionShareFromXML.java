package civitas.crypto.algorithms;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.crypto.importing.ElGamalProofDiscLogEqualityFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalDecryptionShareFromXML {
	@Use
	ConvertToBigInt convertToBigInt;
	@Use
	ElGamalProofDiscLogEqualityFromXML elGamalProofDiscLogEqualityFromXML;

	public ElGamalDecryptionShareC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalDecryptionShare.OPENING_TAG);

		String sa = Util.unescapeString(Util.readSimpleTag(r, "ai"));
		CivitasBigInteger ai = convertToBigInt.apply(sa);

		ElGamalProofDiscLogEqualityC proof = (ElGamalProofDiscLogEqualityC) elGamalProofDiscLogEqualityFromXML
				.apply(r);

		Util.swallowEndTag(r, ElGamalDecryptionShare.OPENING_TAG);
		return new ElGamalDecryptionShareC(ai, proof/* , params */);
	}

}
