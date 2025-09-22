package civitas.crypto.algorithms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CreateFreshNonceBase64 {

	@Autowired
	CreateFreshNonce createFreshNonce;

	@Autowired
	ConvertToBase64 convertToBase64;

	public String apply(int bitlength) {
		return convertToBase64.apply(createFreshNonce.apply(bitlength));
	}
}
