package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPublicKeyC;
import civitas.util.Use;

public class VerifyElGamalDecryptionShare {

	@Use
	public VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	public boolean apply(ElGamalDecryptionShareC that, ElGamalCiphertext c,
			ElGamalPublicKey K) throws Error {
		if (that.proof != null) {
			try {
				ElGamalCiphertextC cipher = (ElGamalCiphertextC) c;
				ElGamalPublicKeyC KC = (ElGamalPublicKeyC) K;
				ElGamalParametersC params = (ElGamalParametersC) K.getParams();
				if (that.proof.g1.equals(cipher.a) && that.proof.g2.equals(params.g)
						&& that.proof.v.equals(that.ai) && that.proof.w.equals(KC.y)) {
					return verifyElGamalProofDiscLogEquality.apply(that.proof, params);
				}
			} catch (NullPointerException e) {
				throw new Error(e);
			} catch (ClassCastException e) {
				throw new Error(e);
			}
		}

		return false;
	}

}
