package civitas.crypto.votecapabilityshare;

import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigInteger;

public class CombineVoteCapabilityShares {

	public VoteCapability[] apply(VoteCapabilityShare[][] shares,
			ElGamalParameters params) {
		if (shares == null || shares[0] == null) {
			return new VoteCapability[0];
		}

		CivitasBigInteger[] product = new CivitasBigInteger[shares[0].length];
		for (VoteCapabilityShare[] share : shares) {
			for (int index = 0; index < share.length; index++) {
				VoteCapabilityShare s = share[index];
				if (product[index] == null) {
					product[index] = s.m();
				} else {
					product[index] = product[index].modMultiply(s.m(), params.p);
				}
			}
		}
		VoteCapability[] capabilities = new VoteCapability[product.length];
		for (int index = 0; index < product.length; index++) {
			capabilities[index] = new VoteCapability(product[index]);
		}
		return capabilities;
	}

}
