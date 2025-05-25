package civitas.crypto.algorithms;

import civitas.util.Use;

public class CreateFreshNonceBase64 {

	@Use
	CreateFreshNonce createFreshNonce;
	@Use
	ConvertToBase64 convertToBase64;

	public String apply(int bitlength) {
		return convertToBase64.apply(createFreshNonce.apply(bitlength));
	}

}
