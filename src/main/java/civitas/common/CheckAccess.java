package civitas.common;

import org.springframework.stereotype.Controller;

@Controller
public class CheckAccess {

	public void apply(Operation operation, String string, String objectID)
			throws SecurityException {
		switch (operation) {
		case POST:
			return;
		default:
			throw new SecurityException(
					operation + " is not authorized for " + objectID + " by " + string);
		}
	}

}
