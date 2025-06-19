package civitas.common.tabteller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.common.ConvertToXml;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.crypto.algorithms.ConvertToBase64;
import civitas.crypto.messagedigest.CryptoHash;

@Service
public class ConstructTabTellerKeyShareCommitment {
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	ConvertToBase64 convertToBase64;
	@Autowired
	ConvertToXml convertToXml;

	public TabTellerKeyShareCommitment apply(TabTellerKeyShare that)
			throws JsonProcessingException {
		String xml = convertToXml.apply(that);
		byte[] hash = cryptoHash.apply(xml);
		return new TabTellerKeyShareCommitment(that.tellerIndex,
				convertToBase64.apply(hash));
	}

}
