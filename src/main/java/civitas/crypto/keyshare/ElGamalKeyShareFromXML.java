package civitas.crypto.keyshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogC;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogFromXML;
import civitas.crypto.publickey.ElGamalPublicKeyC;
import civitas.crypto.publickey.ElGamalPublicKeyFromXML;
import civitas.util.Use;

public class ElGamalKeyShareFromXML {

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	public ElGamalKeyShareC apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalKeyShare");
		Util.swallowTag(r, "pubKey");
		ElGamalPublicKeyC pubKey = elGamalPublicKeyFromXML.apply(r);
		Util.swallowEndTag(r, "pubKey");
		Util.swallowTag(r, "proof");
		ElGamalProofKnowDiscLogC proof = elGamalProofKnowDiscLogFromXML.apply(r);
		Util.swallowEndTag(r, "proof");
		Util.swallowEndTag(r, "elGamalKeyShare");
		return new ElGamalKeyShareC(pubKey, proof);
	}

}
