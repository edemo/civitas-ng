package civitas.crypto.petcommitment;

import civitas.crypto.Constants;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.msg.ElGamalMsg;

public class IsPetResult implements Constants {
	public boolean apply(ElGamalMsg petResult) {
		// Pet result is true if the message == 1
		if (petResult instanceof ElGamalMsg) {
			ElGamalMsg m = (ElGamalMsg) petResult;
			return ONE.equals(m.m);
		}
		return false;
	}

}
