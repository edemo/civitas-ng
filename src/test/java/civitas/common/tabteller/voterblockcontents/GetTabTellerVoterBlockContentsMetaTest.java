package civitas.common.tabteller.voterblockcontents;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Tested;

class GetTabTellerVoterBlockContentsMetaTest extends TestBase
		implements TabTellerVoterBlockContentsTestData {
	@Tested
	GetTabTellerVoterBlockContentsMeta getTabTellerVoterBlockContentsMeta;

	@Test
	@DisplayName("the form of the meta is ttVoterBlockContents:teller<tellerIndex>:voterBlock<VoterBlock>")
	void test() {
		assertEquals(TAB_TELLER_VOTER_BLOCK_CONTENTS_META,
				getTabTellerVoterBlockContentsMeta.apply(7, 9));
	}

}
