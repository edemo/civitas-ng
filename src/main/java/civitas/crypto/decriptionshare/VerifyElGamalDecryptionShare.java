package civitas.crypto.decriptionshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;
import civitas.crypto.publickey.ElGamalPublicKey;

@Service
public class VerifyElGamalDecryptionShare {

	@Autowired
	public VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	public boolean apply(ElGamalDecryptionShare that, ElGamalCiphertext c,
			ElGamalPublicKey K) throws Error {
		if (c == null)
			throw new IllegalArgumentException("null ciphertext");
		if (K == null)
			throw new IllegalArgumentException("null key");
		ElGamalParameters params = K.params;
		//@formatter:off
		if (that.proof.g1.equals(c.a)
				&& that.proof.g2.equals(params.g)
				&& that.proof.v.equals(that.ai)
				&& that.proof.w.equals(K.y)) {
			return verifyElGamalProofDiscLogEquality.apply(that.proof, params);
		}

		return false;
	}

}
