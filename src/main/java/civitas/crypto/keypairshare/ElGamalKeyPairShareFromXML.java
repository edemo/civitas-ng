package civitas.crypto.keypairshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.privatekey.ElGamalPrivateKeyFromXML;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyFromXML;
import civitas.util.Use;

public class ElGamalKeyPairShareFromXML {
	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;
	@Use
	ElGamalPrivateKeyFromXML elGamalPrivateKeyFromXML;

	public ElGamalKeyPairShare apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, "elGamalKeyPairShare");
		ElGamalPublicKey pubKey = elGamalPublicKeyFromXML.apply(r);
		ElGamalPrivateKey privKey = elGamalPrivateKeyFromXML.apply(r);
		Util.swallowEndTag(r, "elGamalKeyPairShare");
		ElGamalParameters params = pubKey.params;
		return new ElGamalKeyPairShare(params, pubKey, privKey);
	}

}
