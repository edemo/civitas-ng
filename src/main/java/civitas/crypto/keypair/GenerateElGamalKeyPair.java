package civitas.crypto.keypair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.CivitasBigInteger;

@Service
public class GenerateElGamalKeyPair {

	@Autowired
	GenerateRandomElement generateRandomElement;

	public ElGamalKeyPair apply(ElGamalParameters p) {
		ElGamalParameters ps = p;
		CivitasBigInteger x = generateRandomElement.apply(ps.q);
		CivitasBigInteger y = ps.g.modPow(x, ps.p);
		ElGamalPrivateKey k = new ElGamalPrivateKey(x, ps);
		ElGamalPublicKey K = new ElGamalPublicKey(y, ps);
		return new ElGamalKeyPair(K, k);
	}

}
