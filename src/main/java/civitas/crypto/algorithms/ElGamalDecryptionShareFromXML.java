package civitas.crypto.algorithms;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;

public class ElGamalDecryptionShareFromXML {

	public ElGamalDecryptionShareC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, ElGamalDecryptionShare.OPENING_TAG);

		String sa = Util.unescapeString(Util.readSimpleTag(r, "ai"));
		CivitasBigInteger ai = CryptoFactoryC.stringToBigInt(sa);

		ElGamalProofDiscLogEqualityC proof = (ElGamalProofDiscLogEqualityC) CryptoFactoryC
				.singleton().elGamalProofDiscLogEqualityFromXML(r);

		Util.swallowEndTag(r, ElGamalDecryptionShare.OPENING_TAG);
		return new ElGamalDecryptionShareC(ai, proof/* , params */);
	}

}
