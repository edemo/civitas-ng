package civitas.crypto.decriptionshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;
import civitas.crypto.publickey.ElGamalPublicKey;

@Controller
public class VerifyElGamalDecryptionShare {

	@Autowired
	public VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	public boolean apply(final ElGamalDecryptionShare that, final ElGamalCiphertext c, final ElGamalPublicKey key)
			throws Error {
		if (c == null) {
			throw new IllegalArgumentException("null ciphertext");
		}
		if (key == null) {
			throw new IllegalArgumentException("null key");
		}
		ElGamalParameters params = key.params;
		if (that.proof().g1().equals(c.a)
				&& that.proof().g2().equals(params.g)
				&& that.proof().v().equals(that.ai())
				&& that.proof().w().equals(key.y)) {
			return verifyElGamalProofDiscLogEquality.apply(that.proof(), params);
		}

		return false;
	}
}
