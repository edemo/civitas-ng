package civitas.crypto.sharedkeyciphertext.tests;

import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.util.tests.BasicValuesTestData;

public interface SharedKeyMsgTestData extends BasicValuesTestData {
	SharedKeyMsg SHARED_KEY_MSG = new SharedKeyMsg(SOMESTRING);
}
