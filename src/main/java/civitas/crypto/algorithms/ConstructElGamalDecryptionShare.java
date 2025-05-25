package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalDecryptionShare;
import civitas.crypto.ElGamalKeyPairShare;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalPrivateKeyC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructElGamalDecryptionShare {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public ElGamalDecryptionShare apply(ElGamalCiphertext c,
			ElGamalKeyPairShare keyShare) {
		if (c instanceof ElGamalCiphertextC
				&& keyShare.privKey instanceof ElGamalPrivateKeyC
				&& keyShare.privKey.getParams() instanceof ElGamalParametersC) {
			ElGamalCiphertextC mc = (ElGamalCiphertextC) c;
			ElGamalPrivateKeyC priv = (ElGamalPrivateKeyC) keyShare.privKey;
			ElGamalParametersC params = (ElGamalParametersC) priv.getParams();
			CivitasBigInteger ai = mc.a.modPow(priv.x, params.p);
			return new ElGamalDecryptionShareC(ai,
					constructElGamalDiscLogEqualityProof.apply(params, mc.a, params.g,
							priv.x));
		}
		return null;
	}

}
