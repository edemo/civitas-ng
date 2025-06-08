package civitas.common.ballotdesign;

import civitas.common.CommonConstants;

public class ConvertStringToChoice implements CommonConstants {

	public byte apply(String choice) {
		Integer value = STRING_TO_CHOICE_MAP.get(choice);
		if (null == value)
			return -1;
		return value.byteValue();
	}

}
