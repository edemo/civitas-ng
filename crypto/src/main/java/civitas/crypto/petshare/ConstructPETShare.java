package civitas.crypto.petshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructPETShare {

	@Autowired
	CryptoBase cryptoBase;

	public PETShare apply(ElGamalParameters prms, ElGamalCiphertextish a,
			ElGamalCiphertextish b) {
		if (a == null || b == null || prms == null) {
			return null;
		}
		ElGamalParameters params = prms;
		CivitasBigInteger z = cryptoBase.generateRandomElement(params.q);
		return new PETShare(a, b, z);
	}

}
