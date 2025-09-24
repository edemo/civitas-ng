package civitas.crypto.proof1ofl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.CryptoException;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.EncodeMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructWellKnownCiphertexts implements Constants {

	@Autowired
	ElGamalEncrypt elGamalEncrypt;

	@Autowired
	EncodeMessage encodeMessage;

	public CiphertextList apply(final ElGamalPublicKey key, final int count) throws CryptoException {
		if (count < 1 || key == null) {
			throw new CryptoException("bad parameters for constructWellKnownCiphertexts");
		}
		CiphertextList cs = new CiphertextList();

		ElGamalReencryptFactor factor = new ElGamalReencryptFactor(ZERO);
		ElGamalParameters params = key.params;
		for (int i = 0; i < count; i++) {
			CivitasBigInteger encodedMessage = encodeMessage.apply(i + 1, params);
			ElGamalCiphertext encrypted = elGamalEncrypt.apply(key, new ElGamalMsg(encodedMessage), factor);
			cs.add(encrypted);
		}
		return cs;
	}
}
