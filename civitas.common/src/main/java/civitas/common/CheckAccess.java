package civitas.common;

import org.springframework.stereotype.Controller;

@Controller
public class CheckAccess {

	public void apply(final Operation operation, final String string,
			final String objectID) {
		if (operation != Operation.POST) {
			throw new SecurityException(
					operation + " is not authorized for " + objectID + " by " + string);
		}
	}

}
