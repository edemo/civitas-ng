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

	public ElGamalKeyPair apply(ElGamalParameters p) {
		ElGamalParameters ps = p;
		CivitasBigInteger x = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);
		ElGamalPrivateKey k = new ElGamalPrivateKey(x, ps);
		ElGamalPublicKey K = new ElGamalPublicKey(y, ps);
		return new ElGamalKeyPair(K, k);
	}

}
