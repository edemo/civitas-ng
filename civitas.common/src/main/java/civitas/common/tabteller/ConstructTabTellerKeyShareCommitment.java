package civitas.common.tabteller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.ConvertToXml;
import civitas.common.tabteller.keysharecommitment.TabTellerKeyShareCommitment;
import civitas.crypto.algorithms.ConvertToBase64;
import civitas.crypto.messagedigest.CryptoHash;
import jakarta.xml.bind.JAXBException;

@Controller
public class ConstructTabTellerKeyShareCommitment {
	@Autowired
	CryptoHash cryptoHash;

	@Autowired
	ConvertToBase64 convertToBase64;

	@Autowired
	ConvertToXml convertToXml;

	public TabTellerKeyShareCommitment apply(TabTellerKeyShare that)
			throws JAXBException, UnsupportedEncodingException {
		String xml = convertToXml.apply(that);
		byte[] hash = cryptoHash.apply(xml.getBytes());
		return new TabTellerKeyShareCommitment(that.tellerIndex(), convertToBase64.apply(hash));
	}
}
