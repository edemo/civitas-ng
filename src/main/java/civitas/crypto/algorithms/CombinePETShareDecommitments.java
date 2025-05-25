package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.PETDecommitment;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.PETDecommitmentC;
import civitas.util.CivitasBigInteger;

public class CombinePETShareDecommitments implements Constants {

	public ElGamalCiphertext apply(PETDecommitment[] decs,
			ElGamalParameters params, CivitasBigInteger d) {
		CivitasBigInteger e = ONE;
		ElGamalParametersC ps = (ElGamalParametersC) params;

		for (int i = 0; i < (decs == null ? 0 : decs.length); i++) {
			PETDecommitmentC decom = (PETDecommitmentC) decs[i];
			d = d.modMultiply(decom.di, ps.p);
			e = e.modMultiply(decom.ei, ps.p);
		}
		return new ElGamalCiphertextC(d, e);
	}

}
