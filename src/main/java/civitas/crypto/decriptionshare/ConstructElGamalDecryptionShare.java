package civitas.crypto.decriptionshare;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.keypairshare.ElGamalKeyPairShare;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.privatekey.ElGamalPrivateKeyC;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalDecryptionShare {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public ElGamalDecryptionShare apply(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare) {
		if (c instanceof ElGamalCiphertext
				&& keyShare.privKey instanceof ElGamalPrivateKeyC
				&& keyShare.privKey.getParams() instanceof ElGamalParametersC) {
			ElGamalCiphertext mc = (ElGamalCiphertext) c;
			ElGamalPrivateKeyC priv = (ElGamalPrivateKeyC) keyShare.privKey;
			ElGamalParametersC params = (ElGamalParametersC) priv.getParams();
			CivitasBigInteger ai = mc.a.modPow(priv.x, params.p);
			return new ElGamalDecryptionShare(ai,
					constructElGamalDiscLogEqualityProof.apply(params, mc.a, params.g,
							priv.x));
		}
		return null;
	}

}
