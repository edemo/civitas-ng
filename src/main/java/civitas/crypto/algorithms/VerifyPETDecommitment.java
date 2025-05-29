package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.PETCommitment;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalProofDiscLogEqualityC;
import civitas.crypto.concrete.PETCommitmentC;
import civitas.crypto.concrete.PETDecommitmentC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyPETDecommitment {

	@Use
	CryptoHash cryptoHash;
	@Use
	VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	public boolean apply(PETDecommitmentC self, PETCommitment c,
			ElGamalParameters params, ElGamalCiphertext ciphertext1,
			ElGamalCiphertext ciphertext2) {
		if (!(c instanceof PETCommitmentC)) {
			return false;
		}
		ElGamalProofDiscLogEqualityC prf = (ElGamalProofDiscLogEqualityC) self.proof;
		ElGamalParametersC ps = (ElGamalParametersC) params;
		PETCommitmentC com = (PETCommitmentC) c;
		ElGamalCiphertextC m1 = (ElGamalCiphertextC) ciphertext1;
		ElGamalCiphertextC m2 = (ElGamalCiphertextC) ciphertext2;

		CivitasBigInteger d = m1.a.modDivide(m2.a, ps.p);
		CivitasBigInteger e = m1.b.modDivide(m2.b, ps.p);

		// check that it's a proof of the correct thing.
		if (self.di == null || self.ei == null)
			return false;
		if (!d.equals(prf.g1) || !e.equals(prf.g2))
			return false;
		return com.hash.equals(cryptoHash.apply(self.di, self.ei))
				&& verifyElGamalProofDiscLogEquality.apply(prf, params);
	}

}
