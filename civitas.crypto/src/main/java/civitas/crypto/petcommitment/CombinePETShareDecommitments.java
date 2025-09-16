package civitas.crypto.petcommitment;

import civitas.crypto.Constants;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.petdecommitment.PETDecommitment;
import civitas.util.CivitasBigInteger;

public class CombinePETShareDecommitments implements Constants {

	public ElGamalCiphertext apply(PETDecommitment[] decs,
			ElGamalParameters params, CivitasBigInteger d) {
		CivitasBigInteger e = ONE;
		ElGamalParameters ps = params;

		for (int i = 0; i < (decs == null ? 0 : decs.length); i++) {
			PETDecommitment decom = decs[i];
			d = d.modMultiply(decom.di(), ps.p());
			e = e.modMultiply(decom.ei(), ps.p());
		}
		return new ElGamalCiphertext(d, e);
	}

	public ElGamalCiphertext apply(PETDecommitment[] decs,
			ElGamalParameters params) {
		return apply(decs, params, ONE);
	}

}
