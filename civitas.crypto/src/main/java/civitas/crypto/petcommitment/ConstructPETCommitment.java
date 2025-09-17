package civitas.crypto.petcommitment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petshare.PETShare;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructPETCommitment {

	@Autowired
	CryptoHash cryptoHash;

	public PETCommitment apply(PETShare that, ElGamalParameters params) {
		CivitasBigInteger d = that.ciphertext1().getA()
				.modDivide(that.ciphertext2().getA(), params.p);
		CivitasBigInteger e = that.ciphertext1().getB()
				.modDivide(that.ciphertext2().getB(), params.p);

		CivitasBigInteger di = d.modPow(that.exponent(), params.p);
		CivitasBigInteger ei = e.modPow(that.exponent(), params.p);

		return new PETCommitment(cryptoHash.apply(di, ei, null, null));
	}

}
