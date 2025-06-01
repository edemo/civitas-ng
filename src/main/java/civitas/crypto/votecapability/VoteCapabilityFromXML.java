package civitas.crypto.votecapability;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.algorithms.ConvertToBigInt;
import civitas.util.Use;

public class VoteCapabilityFromXML implements Constants {
	@Use
	ConvertToBigInt convertToBigInt;

	public VoteCapability apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapabilityOPENING_TAG));
		return new VoteCapability(convertToBigInt.apply(s));
	}

}
