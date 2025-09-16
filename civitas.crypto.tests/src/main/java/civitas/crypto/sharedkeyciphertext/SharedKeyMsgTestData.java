package civitas.crypto.sharedkeyciphertext;

import civitas.crypto.sharedkeymsg.SharedKeyMsg;
import civitas.util.BasicValuesTestData;

public interface SharedKeyMsgTestData extends BasicValuesTestData {
	SharedKeyMsg SHARED_KEY_MSG = new SharedKeyMsg(SOMESTRING);

}
