package civitas.crypto.decriptionshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityFromXML;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ElGamalDecryptionShareFromXML {
	@Use
	ConvertToBigInt convertToBigInt;
	@Use
	ElGamalProofDiscLogEqualityFromXML elGamalProofDiscLogEqualityFromXML;

	public ElGamalDecryptionShare apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalDecryptionShare.OPENING_TAG);

		String sa = Util.unescapeString(Util.readSimpleTag(r, "ai"));
		CivitasBigInteger ai = convertToBigInt.apply(sa);

		ElGamalProofDiscLogEqualityC proof = (ElGamalProofDiscLogEqualityC) elGamalProofDiscLogEqualityFromXML
				.apply(r);

		Util.swallowEndTag(r, ElGamalDecryptionShare.OPENING_TAG);
		return new ElGamalDecryptionShare(ai, proof/* , params */);
	}

}
