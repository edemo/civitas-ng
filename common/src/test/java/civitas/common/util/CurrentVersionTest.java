package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.CommonUtil;
import civitas.crypto.BasicValuesTestData;

public class CurrentVersionTest implements BasicValuesTestData {

	private static final Object VERSIONSTRING = "JCivitas-v0.1";

	@Test
	@DisplayName("The version is JCivitas-v0.1")
	void test() {
		assertEquals(VERSIONSTRING, CommonUtil.currentVersion());
	}

}
