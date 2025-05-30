package civitas.crypto.keyshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogC;
import civitas.crypto.proofknowndisclog.ElGamalProofKnowDiscLogFromXML;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyFromXML;
import civitas.util.Use;

public class ElGamalKeyShareFromXML {

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;
	@Use
	ElGamalProofKnowDiscLogFromXML elGamalProofKnowDiscLogFromXML;

	public ElGamalKeyShare apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalKeyShare");
		Util.swallowTag(r, "pubKey");
		ElGamalPublicKey pubKey = elGamalPublicKeyFromXML.apply(r);
		Util.swallowEndTag(r, "pubKey");
		Util.swallowTag(r, "proof");
		ElGamalProofKnowDiscLogC proof = elGamalProofKnowDiscLogFromXML.apply(r);
		Util.swallowEndTag(r, "proof");
		Util.swallowEndTag(r, "elGamalKeyShare");
		return new ElGamalKeyShare(pubKey, proof);
	}

}
