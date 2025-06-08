package civitas.crypto.petdecommitment;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETDecommitment {

	@Use
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	public PETDecommitment apply(ElGamalParameters p, CivitasBigInteger exponent,
			ElGamalCiphertext ciphertext1, ElGamalCiphertext ciphertext2) {
		ElGamalParameters params = p;

		CivitasBigInteger zi = exponent;
		CivitasBigInteger d = ciphertext1.a.modDivide(ciphertext2.a, params.p);
		CivitasBigInteger e = ciphertext1.b.modDivide(ciphertext2.b, params.p);

		CivitasBigInteger di = d.modPow(zi, params.p);
		CivitasBigInteger ei = e.modPow(zi, params.p);

		ElGamalProofDiscLogEquality proof = constructElGamalDiscLogEqualityProof
				.apply(params, d, e, zi);
		return new PETDecommitment(di, ei, proof);
	}

}
