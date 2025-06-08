package civitas.crypto.petcommitment;

import civitas.crypto.Constants;
import civitas.crypto.msg.ElGamalMsg;

public class IsPetResult implements Constants {
	public boolean apply(ElGamalMsg petResult) {
		if (petResult instanceof ElGamalMsg) {
			ElGamalMsg m = petResult;
			return ONE.equals(m.m);
		}
		return false;
	}

}
