package civitas.crypto.petcommitment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petshare.PETShare;
import civitas.util.CivitasBigInteger;

@Service
public class ConstructPETCommitment {

	@Autowired
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
