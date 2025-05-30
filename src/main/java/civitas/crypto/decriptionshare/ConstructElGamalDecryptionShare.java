package civitas.crypto.decriptionshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalDecryptionShare {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public ElGamalDecryptionShare apply(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare) {
		if (c instanceof ElGamalCiphertext
				&& keyShare.privKey instanceof ElGamalPrivateKey
				&& keyShare.privKey.params instanceof ElGamalParametersC) {
			ElGamalCiphertext mc = c;
			ElGamalPrivateKey priv = keyShare.privKey;
			ElGamalParametersC params = (ElGamalParametersC) priv.params;
			CivitasBigInteger ai = mc.a.modPow(priv.x, params.p);
			return new ElGamalDecryptionShare(ai, constructElGamalDiscLogEqualityProof
					.apply(params, mc.a, params.g, priv.x));
		}
		return null;
	}

}
