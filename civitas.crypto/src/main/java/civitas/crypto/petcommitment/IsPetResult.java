package civitas.crypto.petcommitment;

import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;
import civitas.crypto.msg.ElGamalMsg;

@Controller
public class IsPetResult implements Constants {
	public boolean apply(ElGamalMsg petResult) {
		return ONE.equals(petResult.m);
	}

}
