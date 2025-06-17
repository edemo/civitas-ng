package civitas.crypto.petcommitment;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petshare.PETShare;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETCommitment {

	@Use
	CryptoHash cryptoHash;

	public PETCommitment apply(PETShare that, ElGamalParameters params) {
		ElGamalParameters ps = params;

		CivitasBigInteger zi = that.exponent;
		CivitasBigInteger d = that.ciphertext1.getA()
				.modDivide(that.ciphertext2.getA(), ps.p);
		CivitasBigInteger e = that.ciphertext1.getB()
				.modDivide(that.ciphertext2.getB(), ps.p);

		CivitasBigInteger di = d.modPow(zi, ps.p);
		CivitasBigInteger ei = e.modPow(zi, ps.p);

		return new PETCommitment(cryptoHash.apply(di, ei));
	}

}
