package civitas.crypto.sharedkeyciphertext;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.sharedkeymsg.SharedKeyMsg;

public interface SharedKeyMsgTestData extends BasicValuesTestData {
	SharedKeyMsg SHARED_KEY_MSG = new SharedKeyMsg(SOMESTRING);

}
