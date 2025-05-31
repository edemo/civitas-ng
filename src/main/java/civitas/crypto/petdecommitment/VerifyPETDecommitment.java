package civitas.crypto.petdecommitment;

import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petcommitment.PETCommitment;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.crypto.proofdisclog.VerifyElGamalProofDiscLogEquality;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyPETDecommitment {

	@Use
	CryptoHash cryptoHash;
	@Use
	VerifyElGamalProofDiscLogEquality verifyElGamalProofDiscLogEquality;

	public boolean apply(PETDecommitment self, PETCommitment c,
			ElGamalParameters params, ElGamalCiphertext ciphertext1,
			ElGamalCiphertext ciphertext2) {
		if (!(c instanceof PETCommitment)) {
			return false;
		}
		ElGamalProofDiscLogEquality prf = self.proof;
		ElGamalParameters ps = params;
		PETCommitment com = c;
		ElGamalCiphertext m1 = ciphertext1;
		ElGamalCiphertext m2 = ciphertext2;

		CivitasBigInteger d = m1.a.modDivide(m2.a, ps.p);
		CivitasBigInteger e = m1.b.modDivide(m2.b, ps.p);

		// check that it's a proof of the correct thing.
		if (self.di == null || self.ei == null || !d.equals(prf.g1)
				|| !e.equals(prf.g2))
			return false;
		return com.hash.equals(cryptoHash.apply(self.di, self.ei))
				&& verifyElGamalProofDiscLogEquality.apply(prf, params);
	}

}
