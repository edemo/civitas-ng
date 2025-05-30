package civitas.crypto.decriptionshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.publickey.ElGamalPublicKeyC;
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
