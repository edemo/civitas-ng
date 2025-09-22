package civitas.crypto.keypair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Controller
public class GenerateElGamalKeyPair {

	@Autowired
	CryptoBase cryptoBase;

	public ElGamalKeyPair apply(ElGamalParameters parameters) {
		CivitasBigInteger x = cryptoBase.generateRandomElement(parameters.q);
		CivitasBigInteger y = parameters.g.modPow(x, parameters.p);
		ElGamalPrivateKey privateKey = new ElGamalPrivateKey(x, parameters);
		ElGamalPublicKey publicKey = new ElGamalPublicKey(y, parameters);
		return new ElGamalKeyPair(publicKey, privateKey);
	}
}
