package civitas.crypto.algorithms;

import civitas.crypto.ElGamalParameters;
import civitas.crypto.VoteCapability;
import civitas.crypto.VoteCapabilityShare;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.VoteCapabilityC;
import civitas.crypto.concrete.VoteCapabilityShareC;
import civitas.util.CivitasBigInteger;

public class CombineVoteCapabilityShares {

	public VoteCapability[] apply(VoteCapabilityShare[][] shares,
			ElGamalParameters p) {
		if (shares == null)
			return null;
		try {
			ElGamalParametersC params = (ElGamalParametersC) p;
			// multiply all the shares together
			CivitasBigInteger[] accum = new CivitasBigInteger[shares[0].length];
			for (int i = 0; i < shares.length; i++) {
				for (int j = 0; j < shares[i].length; j++) {
					VoteCapabilityShareC s = (VoteCapabilityShareC) shares[i][j];
					if (accum[j] == null) {
						accum[j] = s.m;
					} else {
						accum[j] = accum[j].modMultiply(s.m, params.p);
					}
				}
			}
			VoteCapability[] ret = new VoteCapability[accum.length];
			for (int j = 0; j < accum.length; j++) {
				ret[j] = new VoteCapabilityC(accum[j]);
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		} catch (ClassCastException e) {
			return null;
		}
	}

}
