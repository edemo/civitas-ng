package civitas.common.tabteller;

import civitas.common.electionresults.TellerTestData;

public interface TabTellerKeyShareTestData extends TellerTestData {
	TabTellerKeyShare TAB_TELLER_KEY_SHARE = new TabTellerKeyShare(TELLER_INDEX);
	String TAB_TELLER_KEY_SHARE_XML = "<TabTellerKeyShare><tellerIndex>7</tellerIndex></TabTellerKeyShare>";
	byte[] TAB_TELLER_KEY_SHARE_HASH = "ttkshare hash".getBytes();
}
