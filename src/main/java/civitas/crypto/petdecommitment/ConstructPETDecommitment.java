package civitas.crypto.petdecommitment;

import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETDecommitment {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public PETDecommitment apply(ElGamalParameters p, CivitasBigInteger exponent,
			ElGamalCiphertextC ciphertext1, ElGamalCiphertextC ciphertext2) {
		try {
			ElGamalParametersC params = (ElGamalParametersC) p;

			CivitasBigInteger zi = exponent;
			CivitasBigInteger d = ciphertext1.a.modDivide(ciphertext2.a, params.p);
			CivitasBigInteger e = ciphertext1.b.modDivide(ciphertext2.b, params.p);

			CivitasBigInteger di = d.modPow(zi, params.p);
			CivitasBigInteger ei = e.modPow(zi, params.p);

			ElGamalProofDiscLogEqualityC proof = constructElGamalDiscLogEqualityProof
					.apply(params, d, e, zi);
			return new PETDecommitmentC(di, ei, proof);
		} catch (ClassCastException e) {
			return null;
		}
	}

}
