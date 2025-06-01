package civitas.crypto.keypairshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.crypto.privatekey.ElGamalPrivateKeyToXML;
import civitas.crypto.publickey.ElGamalPublicKeyToXML;
import civitas.util.Use;

public class ElGamalKeyPairShareToXML {
	@Use
	ElGamalPublicKeyToXML elGamalPublicKeyToXML;
	@Use
	ElGamalPrivateKeyToXML elGamalPrivateKeyToXML;

	public String apply(ElGamalKeyPairShare that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalKeyPairShare that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<elGamalKeyPairShare>");

		if (that.pubKey != null) {
			elGamalPublicKeyToXML.apply(that.pubKey, sb);
		}
		if (that.privKey != null) {
			elGamalPrivateKeyToXML.apply(that.privKey, sb);
		}

		sb.print("</elGamalKeyPairShare>");
	}

}
