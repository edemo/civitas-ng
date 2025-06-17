package civitas.common.tabteller;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.common.ConvertToXml;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.crypto.algorithms.ConvertToBase64;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.util.Use;

public class ConstructTabTellerKeyShareCommitment {
	@Use
	CryptoHash cryptoHash;
	@Use
	ConvertToBase64 convertToBase64;
	@Use
	ConvertToXml convertToXml;

	public TabTellerKeyShareCommitment apply(TabTellerKeyShare that)
			throws JsonProcessingException {
		String xml = convertToXml.apply(that);
		byte[] hash = cryptoHash.apply(xml);
		return new TabTellerKeyShareCommitment(that.tellerIndex,
				convertToBase64.apply(hash));
	}

}
