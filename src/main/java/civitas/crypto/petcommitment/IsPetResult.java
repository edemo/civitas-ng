package civitas.crypto.petcommitment;

import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.msg.ElGamalMsg;

@Service
public class IsPetResult implements Constants {
	public boolean apply(ElGamalMsg petResult) {
		return ONE.equals(petResult.m);
	}

}
