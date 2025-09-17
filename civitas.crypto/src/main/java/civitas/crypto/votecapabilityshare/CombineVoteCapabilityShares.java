package civitas.crypto.votecapabilityshare;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigInteger;

public class CombineVoteCapabilityShares {

	public VoteCapability[] apply(VoteCapabilityShare[][] shares,
			ElGamalParameters params) {
		if (shares == null) {
			return null;
		}
		try {
			CivitasBigInteger[] accum = new CivitasBigInteger[shares[0].length];
			for (VoteCapabilityShare[] share : shares) {
				for (int j = 0; j < share.length; j++) {
					VoteCapabilityShare s = share[j];
					if (accum[j] == null) {
						accum[j] = s.m();
					} else {
						accum[j] = accum[j].modMultiply(s.m(), params.p);
					}
				}
			}
			VoteCapability[] ret = new VoteCapability[accum.length];
			for (int j = 0; j < accum.length; j++) {
				ret[j] = new VoteCapability(accum[j]);
			}
			return ret;

		} catch (NullPointerException e) {
			return null;
		}
	}

}
