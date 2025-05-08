package civitas.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.Util;
import civitas.common.UtilTestData;

public class CurrentVersionTest {
	@Test
	@DisplayName("The version is JCivitas-v0.1")
	void test() {
		assertEquals(UtilTestData.VERSIONSTRING, Util.currentVersion());
	}

}
