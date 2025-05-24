package civitas.crypto.algorithms;

import civitas.crypto.ElGamalParameters;
import civitas.crypto.PETDecommitment;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.crypto.concrete.PETDecommitmentC;
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
